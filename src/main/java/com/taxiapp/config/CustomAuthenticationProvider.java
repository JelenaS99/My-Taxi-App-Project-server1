package com.taxiapp.config;

import com.taxiapp.model.dto.LoginDto;
import com.taxiapp.model.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;

    public CustomAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginDto request = new LoginDto();
        request.setUsername(username);
        request.setPassword(password);

        try {
            ResponseEntity<LoginResponseDto> response =
                    restTemplate.postForEntity(
                            "http://localhost:8095/api/users/login",
                            request,
                            LoginResponseDto.class
                    );

            if (response.getStatusCode().is2xxSuccessful()) {

                List<GrantedAuthority> authorities =
                        response.getBody().getRoles().stream()
                                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                                .toList();

                return new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        authorities
                );
            }

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
