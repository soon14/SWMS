package com.swms.user.rest.controller;

import com.swms.user.api.utils.JwtUtils;
import com.swms.user.rest.common.vo.AuthModel;
import com.swms.user.rest.common.vo.UserModel;
import com.swms.user.rest.param.login.LoginRequest;
import com.swms.user.service.UserService;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    @ResponseBody
    public AuthModel authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        UserModel userModel = UserModel.builder().username(userDetails.getUsername()).build();
        return AuthModel.builder().api_token(jwtCookie.getValue()).user(userModel).build();
    }

    @PostMapping("/signout")
    public Response<Object> logoutUser() {
        jwtUtils.getCleanJwtCookie();
        return Response.success();

    }
}
