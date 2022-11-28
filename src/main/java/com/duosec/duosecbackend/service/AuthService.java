package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.exception.TokenExpiredException;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.security.jwt.JwtGenerator;
import com.duosec.duosecbackend.security.model.JwtUser;
import com.duosec.duosecbackend.utils.ExtensionFunction;
import com.duosec.duosecbackend.utils.RandomOTP;
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

    public CompanyRegister getCompanyDetails(String uniqueId) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(uniqueId).get();
        return new CompanyRegister(companyCreds.getCompanyName(), companyCreds.getCompanyEmailId());
    }


    public void saveRegisterCompanyDetails(CompanyRegister companyRegister) throws EmptyDataException, NullDataException {
        if (companyRegister.getCompanyEmailId() == null || companyRegister.getCompanyName() == null)
            throw new EmptyDataException("CompanyEmailId or CompanyEmailId can't be null");
        if (companyRegister.getCompanyEmailId().equals("") || companyRegister.getCompanyName().equals(""))
            throw new NullDataException("CompanyEmailId or CompanyEmailId can't empty");

        CompanyCreds companyCreds = new CompanyCreds();
        companyCreds.setCreateDate(LocalDateTime.now());
        companyCreds.setExpireDate(LocalDateTime.now().plusMinutes(1));
        companyCreds.setCompanyName(companyRegister.getCompanyName());
        companyCreds.setCompanyEmailId(companyRegister.getCompanyEmailId());
        authModel.save(companyCreds);
        companyCreds.setCompanyUniqueId(companyCreds.getId().toString());
        authModel.save(companyCreds);
    }

    public boolean storeDetails(CompanyRegisterComplete companyRegisterComplete) throws EmptyDataException, NullDataException {
        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (companyRegisterComplete.getCompanyUniqueId().isEmpty() ||
                companyRegisterComplete.getPassword().isEmpty() ||
                companyRegisterComplete.getAlgorithm().isEmpty())
            throw new EmptyDataException("CompanyUniqueId, Password, Algorithm can't be empty");

        if (extensionFunction.isNull(companyRegisterComplete.getCompanyUniqueId()) ||
                extensionFunction.isNull(companyRegisterComplete.getPassword()) ||
                extensionFunction.isNull(companyRegisterComplete.getAlgorithm()))
            throw new NullDataException("CompanyUniqueId, Password, Algorithm can't be null");

        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyRegisterComplete.getCompanyUniqueId()).get();
        if (companyCreds.getCompanyUniqueId().equals(companyRegisterComplete.getCompanyUniqueId())
                && !companyCreds.isCompanyMailVerified()) {
            companyCreds.setAlgorithm(companyRegisterComplete.getAlgorithm());
            companyCreds.setOtpRefreshDuration(companyRegisterComplete.getOtpRefreshDuration());
            companyCreds.setPassword(companyRegisterComplete.getPassword());
            companyCreds.setCompanyMailVerified(true);
            companyCreds.setApiKey(new RandomOTP().generateApiKey());
            authModel.save(companyCreds);
            return true;
        }
        return false;
    }

    public String loginSentMail(CompanyLogin companyLogin) {
        try {
            CompanyCreds companyCreds = authModel.findByCompanyEmailId(companyLogin.getCompanyEmailId()).get();
            if (companyCreds.getPassword().equals(companyLogin.getPassword())) {
                String otp = new RandomOTP().getRandomNumberString();
                companyCreds.setOtp(otp);
                //        TODO: sent otp in mail
                authModel.save(companyCreds);
                return companyCreds.getCompanyUniqueId();
            }
            return null;
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
            companyCreds.setOtp(null);
            authModel.save(companyCreds);
            return sendJwt(companyCreds);
        }
        //        TODO: sent otp in mail
        return new AuthResponse();
    }

    public String forgotPassword(String email) throws NullDataException {
        Optional<CompanyCreds> userOptional = authModel.findByCompanyEmailId(email);
        if (userOptional.isEmpty()) {
            throw new NullDataException("Email Id field is Empty");
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

    public String resetPassword(String token, String password) throws EmptyDataException, NullDataException, TokenExpiredException {
        if (token.isEmpty() || password.isEmpty()) {
            throw new EmptyDataException("Empty token or password field");
        }

        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(token) || extensionFunction.isNull(password)) {
            throw new EmptyDataException("Null token or password field");
        }

        Optional<CompanyCreds> userOptional = Optional.ofNullable(authModel.findByToken(token));
        LocalDateTime tokenCreationDate = LocalDateTime.parse(userOptional.get().getTokenCreationDate());
        if (isTokenExpired(tokenCreationDate)) {
            throw new TokenExpiredException("Token Expired");
        }
        CompanyCreds companyCreds = userOptional.get();
        companyCreds.setPassword(password);
        companyCreds.setToken(null);
        companyCreds.setTokenCreationDate(null);
        authModel.save(companyCreds);
        return "Your password successfully updated.";
    }
}
