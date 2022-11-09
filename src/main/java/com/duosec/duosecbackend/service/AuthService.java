package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.security.jwt.JwtGenerator;
import com.duosec.duosecbackend.security.model.JwtUser;
import com.duosec.duosecbackend.utils.RandomOTP;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.duosec.duosecbackend.utils.Constants.EXPIRE_TOKEN_AFTER_MINUTES;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:16 AM
 */
@Service
public class AuthService {

    @Autowired
    private AuthModel authModel;

    @Autowired
    private JwtGenerator jwtGenerator;

    public CompanyCreds getCompanyDetails(String uniqueId) {
        return authModel.findByCompanyUniqueId(uniqueId).get();
    }


    public ObjectId saveRegisterCompanyDetails(CompanyRegister companyRegister) {
        CompanyCreds companyCreds = new CompanyCreds();
        companyCreds.setCreateDate(LocalDateTime.now());
        companyCreds.setExpireDate(LocalDateTime.now().plusMinutes(1));
        companyCreds.setCompanyName(companyRegister.getCompanyName());
        companyCreds.setCompanyEmailId(companyRegister.getCompanyEmailId());
        authModel.save(companyCreds);
        companyCreds.setCompanyUniqueId(companyCreds.getId().toString());
        authModel.save(companyCreds);
        return companyCreds.getId();
    }

    public boolean storeDetails(CompanyRegisterComplete companyRegisterComplete) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyRegisterComplete.getCompanyUniqueId()).get();
        if (companyCreds.getCompanyUniqueId().equals(companyRegisterComplete.getCompanyUniqueId()) && !companyCreds.isCompanyMailVerified()) {
            companyCreds.setAlgorithm(companyRegisterComplete.getAlgorithm());
            companyCreds.setOtpRefreshDuration(companyRegisterComplete.getOtpRefreshDuration());
            companyCreds.setPassword(companyRegisterComplete.getPassword());
            companyCreds.setCompanyMailVerified(true);
            authModel.save(companyCreds);
            return true;
        }
        return false;
    }

    public String loginSentMail(CompanyLogin companyLogin) {
        try {
            CompanyCreds companyCreds = authModel.findByCompanyEmailId(companyLogin.getCompanyEmailId()).get();
            String otp = new RandomOTP().getRandomNumberString();
            companyCreds.setOtp(otp);
            //        TODO: sent otp in mail
            authModel.save(companyCreds);
            return companyCreds.getCompanyUniqueId();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public AuthResponse sendJwt(CompanyCreds companyCreds) {
        String token = jwtGenerator.generate(new JwtUser(companyCreds.getCompanyName(), "COMPANY_ADMIN"));
        return new AuthResponse(companyCreds.getCompanyName(), token);
    }

    public AuthResponse verifyOtp(CompanyLoginVerify companyLoginVerify) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyLoginVerify.getCompanyUniqueId()).get();
        if (companyCreds.getOtp().equals(companyLoginVerify.getOtp())) {
            companyCreds.setOtp("");
            authModel.save(companyCreds);
            return sendJwt(companyCreds);
        }
        //        TODO: sent otp in mail
        return new AuthResponse();
    }

    public String forgotPassword(String email) {
        Optional<CompanyCreds> userOptional = authModel.findByCompanyEmailId(email);
        if (userOptional.isEmpty()) {
            return "Invalid email id.";
        }
        CompanyCreds companyCreds = userOptional.get();
        companyCreds.setToken(generateToken());
        companyCreds.setTokenCreationDate(String.valueOf(LocalDateTime.now()));
        companyCreds = authModel.save(companyCreds);
//        mailService.sendEmail(email, userOptional.get().getName(), Constants.FORGET_PASSWORD_SUBJECT, Constants.HELLO + Constants.FORGET_PASSWORD_BODY + " " + Constants.FRONTEND_URL + user.getToken());
        return companyCreds.getToken();
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID()) +
                UUID.randomUUID();
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

    public String resetPassword(String token, String password) {
        Optional<CompanyCreds> userOptional = Optional.ofNullable(authModel.findByToken(token));
        if (userOptional.isEmpty()) {
            return "Invalid token.";
        }
        LocalDateTime tokenCreationDate = LocalDateTime.parse(userOptional.get().getTokenCreationDate());
        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        CompanyCreds companyCreds = userOptional.get();
        companyCreds.setPassword(password);
        companyCreds.setToken(null);
        companyCreds.setTokenCreationDate(null);
        authModel.save(companyCreds);
        return "Your password successfully updated.";
    }
}
