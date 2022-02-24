package au.com.asx.demo.controllers;

import java.util.Locale;

import au.com.asx.demo.dto.UserDetailDTO;
import au.com.asx.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import au.com.asx.demo.exceptions.UserException;

@RestController
public class UserInfoController {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/api/userdetails/{id}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String id) {
        LOGGER.info("Entering getUserDetails id: " + id);
        if (!isUserIdValid(id)) {
            LOGGER.error("Exiting getUserDetails with error");
            return buildErrorMessage(messageSource.getMessage("error.user.invalid.userId", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
        }

        UserDetailDTO userDetailDTO = userService.getUserDetails(id);
        if (userDetailDTO != null) {
            LOGGER.info("Exiting getUserDetails after success");
            return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
        }
        LOGGER.error("Exiting getUserDetails with error");
		return buildErrorMessage(messageSource.getMessage("error.user.userId.notfound", new Object[]{id}, Locale.ENGLISH), HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/api/userdetails/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUserDetails(@PathVariable String id, @RequestBody UserDetailDTO userDetails) {
        LOGGER.info("Entering updateUserDetails id: " + id);
        if (userDetails == null || id == null) {
            LOGGER.info("Exiting updateUserDetails with error");
            return buildErrorMessage(messageSource.getMessage("error.user.details.missing", null, Locale.ENGLISH), HttpStatus.NOT_FOUND);
        }
        if (!isUserIdValid(id)) {
            LOGGER.info("Exiting updateUserDetails with error");
            return buildErrorMessage(messageSource.getMessage("error.user.invalid.userId", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
        }
        userDetails.setUserId(id);
        UserDetailDTO updatedUser = userService.updateUserDetails(userDetails);
        if (updatedUser != null) {
            LOGGER.info("Exiting updateUserDetails after success");
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        LOGGER.info("Exiting updateUserDetails with error");
        return buildErrorMessage(messageSource.getMessage("error.user.userId.notfound", new Object[]{userDetails.getUserId()}, Locale.ENGLISH), HttpStatus.NOT_FOUND);
    }

    private boolean isUserIdValid(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private ResponseEntity<Object> buildErrorMessage(String message, HttpStatus status) {
        UserException exception = new UserException(message, status);
        return new ResponseEntity<>(exception, exception.getStatus());
    }


}
