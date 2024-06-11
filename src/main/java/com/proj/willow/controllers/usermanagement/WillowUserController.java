package com.proj.willow.controllers.usermanagement;


import com.proj.willow.model.request.SignUpRequest;
import com.proj.willow.model.request.SigninRequest;
import com.proj.willow.model.response.JwtAuthenticationResponse;
import com.proj.willow.service.WillowUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Willow Fix", description = "Generate token management")
@RequestMapping("/api/v1/auth")
public class WillowUserController {
    @Autowired
    WillowUserService userService;


    @Operation(
            summary = "Create a user account",
            description = "The response is the token generated."
            //tags = { "CreateUser", "POST" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = WillowUserController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        log.info("custom :: Controller signup request");
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        log.info("custom :: Controller signin request");
        return ResponseEntity.ok(userService.signin(request));
    }

}
