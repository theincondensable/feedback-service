package io.incondensable.web.controller;

import io.incondensable.business.service.UserService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.UserMapper;
import io.incondensable.web.dto.user.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @ControllerLog
    @GetMapping("/{email}")
    @Operation(summary = "To Get a Persisted User.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns The matching E-Mail Address User"),
                    @ApiResponse(responseCode = "404", description = "User with given E-Mail Address Not Found.")
            }
    )
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(
                userMapper.entityToDto(
                        userService.getUserByEmail(email)
                )
        );
    }

}
