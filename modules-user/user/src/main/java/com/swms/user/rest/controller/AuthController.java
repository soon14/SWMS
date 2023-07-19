package com.swms.user.rest.controller;

import com.swms.user.api.utils.JwtUtils;
import com.swms.user.rest.common.vo.AuthModel;
import com.swms.user.rest.common.vo.UserModel;
import com.swms.user.rest.param.login.LoginRequest;
import com.swms.user.service.UserService;
import com.swms.user.service.model.PermissionGrantedAuthority;
import com.swms.user.service.model.UserDetailsModel;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String jwtCookie = jwtUtils.generateJwtCookie(authorities, userDetails.getUsername());
        UserModel userModel = UserModel.builder().username(userDetails.getUsername()).icon(userDetails.getUser().getAvatar()).build();

        //压缩token
        return AuthModel.builder().token(jwtCookie).user(userModel).build();
    }

    @GetMapping("/signout")
    public Response<Object> logoutUser() {
        return Response.success();
    }
}
