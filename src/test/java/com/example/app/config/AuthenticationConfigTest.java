package com.example.app.config;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({InstancioExtension.class, MockitoExtension.class})
class AuthenticationConfigTest {

    @Test
    void user_details_service_should_return_in_memory_user_with_expected_username_and_password(
        @Given String username,
        @Given String password,
        @Mock SecurityProperties securityProperties) {

        when(securityProperties.username()).thenReturn(username);
        when(securityProperties.password()).thenReturn(password);

        AuthenticationConfig config = new AuthenticationConfig(securityProperties);

        UserDetailsService service = config.userDetailsService();
        assertThat(service).isInstanceOf(InMemoryUserDetailsManager.class);
        UserDetails user = service.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(config.passwordEncoder().matches(password, user.getPassword())).isTrue();
    }

    @Test
    void password_encoder_should_encode_password(@Given String rawPassword, @Mock SecurityProperties securityProperties) {
        AuthenticationConfig config = new AuthenticationConfig(securityProperties);
        PasswordEncoder encoder = config.passwordEncoder();
        String encoded = encoder.encode(rawPassword);
        assertThat(encoder.matches(rawPassword, encoded)).isTrue();
    }

    @Test
    void filter_chain_should_configure_http_security(@Mock SecurityProperties securityProperties) throws Exception {
        AuthenticationConfig config = new AuthenticationConfig(securityProperties);

        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        assertThat(config.filterChain(http)).isNotNull();
    }
}
