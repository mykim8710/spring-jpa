package com.example.springjpa.config.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("account > " +account);
        log.info("password > " +password);

//        User user = (User) customUserDetailsService.loadUserByUsername(account);
//
//        // 비밀번호가 미일치 throw Exception
//        if(!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException(account);
//        }
//
//        ResponseUserSignInDto dto = user.toResponseUserSignInDto();
        return new UsernamePasswordAuthenticationToken(null, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;    // false -> true
    }


}
