package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.dto.request.AuthenticationRequest;
import com.poly.quanlybanhang.dto.response.AuthenticationResponse;
import com.poly.quanlybanhang.exception.AppException;
import com.poly.quanlybanhang.exception.ErrorCode;
import com.poly.quanlybanhang.mapper.UserMapper;
import com.poly.quanlybanhang.repository.UserRepository;
import com.poly.quanlybanhang.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    UserMapper userMapper;
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTS));

    boolean authenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());

    if(!authenticate)
        throw new AppException(ErrorCode.UNAUTHENTICATED);

    return AuthenticationResponse.builder()
            .authentication(true)
            .build();

    }
}
