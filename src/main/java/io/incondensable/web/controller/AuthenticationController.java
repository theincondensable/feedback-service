package io.incondensable.web.controller;

import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.global.security.service.AuthenticationService;
import io.incondensable.mapper.CustomerMapper;
import io.incondensable.web.dto.auth.request.LoginWithCredentialsRequestDto;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import io.incondensable.web.dto.auth.response.LoggedInCustomerResponseDto;
import io.incondensable.web.dto.customer.response.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final CustomerMapper customerMapper;

    @ControllerLog
    @PostMapping("/login/credentials")
    public ResponseEntity<LoggedInCustomerResponseDto> loginWithCredentials(@Valid @RequestBody LoginWithCredentialsRequestDto req) {
        return ResponseEntity.ok(authService.loginWithCredentials(req));
    }

    @ControllerLog
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponseDto> signup(@Valid @RequestBody SignupRequestDto req) {
        return ResponseEntity.ok(
                customerMapper.entityToDto(authService.signup(req))
        );
    }

//    @PostMapping("/login/otp")
//    public ResponseEntity<LoggedInUserResponseDto> loginWithOtp(@Valid @RequestBody LoginWithOtpRequestDto req) {
//        return ResponseEntity.ok(authService.loginWithOtp(req));
//    }

}
