package io.incondensable.web.controller;

import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.global.security.service.AuthenticationService;
import io.incondensable.mapper.CustomerMapper;
import io.incondensable.web.dto.BaseResponseDto;
import io.incondensable.web.dto.auth.request.LoginWithCredentialsRequestDto;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import io.incondensable.web.dto.auth.response.LoggedInCustomerResponseDto;
import io.incondensable.web.dto.customer.response.CustomerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @PostMapping("/login")
    @Operation(
            summary = "To login with Credentials of the Customer.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {@ExampleObject(
                                    name = "LoginWithCredentialsRequestDto ADMIN",
                                    summary = "Admin or let's say System Credentials.",
                                    value = """
                                            {
                                              "email": "admin@gmail.com",
                                              "password": "P@ssw0rd"
                                            }"""
                            ), @ExampleObject(
                                    name = "LoginWithCredentialsRequestDto MANAGER",
                                    summary = "Manager Credentials.",
                                    value = """
                                            {
                                              "email": "manager@gmail.com",
                                              "password": "P@ssw0rd"
                                            }"""
                            ), @ExampleObject(
                                    name = "LoginWithCredentialsRequestDto CUSTOMER",
                                    summary = "Customer Credentials.",
                                    value = """
                                            {
                                              "email": "customer@gmail.com",
                                              "password": "P@ssw0rd"
                                            }"""
                            ), @ExampleObject(
                                    name = "LoginWithCredentialsRequestDto CUSTOMER2",
                                    summary = "Customer2 Credentials.",
                                    value = """
                                            {
                                              "email": "customer2@gmail.com",
                                              "password": "P@ssw0rd"
                                            }"""
                            )}
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The Customer is logged-in."),
                    @ApiResponse(responseCode = "409", description = "Customer's E-Mail Address Not Found.")
            }
    )
    public ResponseEntity<LoggedInCustomerResponseDto> loginWithCredentials(@Valid @RequestBody LoginWithCredentialsRequestDto req) {
        return ResponseEntity.ok(authService.loginWithCredentials(req));
    }

    @ControllerLog
    @PostMapping("/signup")
    @Operation(summary = "To Create or Signup a Customer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "SignupRequestDTO MANAGER",
                                    summary = "Simple Customer With CUSTOMER Role DTO Sample",
                                    value = """
                                            {
                                              "customer": {
                                                "firstname": "Abbas",
                                                "lastname": "Ashrafi",
                                                "password": "P@ssw0rd",
                                                "email": "abbasashrafi95@gmail.com",
                                                "phoneNumber": "09019632702",
                                                "address": {
                                                  "country": "Iran",
                                                  "city": "Tehran",
                                                  "street": "Shariati",
                                                  "zipcode": "1916773944"
                                                },
                                                "roles": [
                                                  "CUSTOMER"
                                                ]
                                              }
                                            }"""
                            )
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The Customer has successfully signed up."),
                    @ApiResponse(responseCode = "409", description = "A Customer with The given E-Mail Address already exists.")
            }
    )
    public ResponseEntity<CustomerResponseDto> signup(
            @Valid @RequestBody SignupRequestDto req) {
        return ResponseEntity.ok(
                customerMapper.entityToDto(authService.signup(req))
        );
    }

    @ControllerLog
    @PostMapping("/logout")
    public ResponseEntity<BaseResponseDto<String>> logout() {
        String loggedOutCustomerEmailAddress = authService.logout();
        return ResponseEntity.ok(new BaseResponseDto<>(
                "Logout OK.",
                ("The Customer with Email " + loggedOutCustomerEmailAddress + " has been successfully logged out.")
        ));
    }

}
