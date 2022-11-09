package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.ChangePasswordModel;
import com.duosec.duosecbackend.service.SettingService;
import com.duosec.duosecbackend.utils.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 10:58 PM
 */
@RestController
@RequestMapping(Endpoints.SETTINGS)
public class SettingController {

    @Autowired
    private SettingService settingService;

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        try {
            boolean status = settingService.changePassword(changePasswordModel);
            if (status) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
