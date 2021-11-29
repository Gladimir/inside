package com.test.insidetest.controller;

import com.test.insidetest.model.*;
import com.test.insidetest.repository.Message;
import com.test.insidetest.repository.UserEntity;
import com.test.insidetest.security.JwtTokenUtil;
import com.test.insidetest.service.MessageService;
import com.test.insidetest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final MessageService messageService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreateResponse> createUser(@Valid @RequestBody UserCreateRequest userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity user = mapper.map(userDetails, UserEntity.class);
        UserEntity createdUser = userService.createUser(user);

        UserCreateResponse returnValue = mapper.map(createdUser, UserCreateResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));

            UserEntity user = (UserEntity) authenticate.getPrincipal();

            String token = jwtTokenUtil.generateAccessToken(user);

            LoginResponse returnValue = new ModelMapper().map(user, LoginResponse.class);

            returnValue.setToken(token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(returnValue);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/message/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createMessage (@RequestBody @Valid MessageCreateRequest request) {
        if(request.getMessage().equals("history 10")) {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.get10LastMessages());
        } else {
            messageService.createMessage(request);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
}
