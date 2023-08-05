package com.groupe_8.tp_api.Config;

import com.groupe_8.tp_api.Service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;



import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {


        @Autowired
        UtilisateurService userDetailsService;

        @Autowired
        private AccessDeniedHandler accessDeniedHandler;



        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeRequests(auth->auth
                            .requestMatchers("/utilisateur/read").permitAll()
                            .requestMatchers("/utilisateur/create").permitAll()
                            .anyRequest().authenticated())
                    ;
            return http.build();
        }

        private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        @Bean
        public AuthenticationProvider getProvider() {

            AppAuthProvider provider = new AppAuthProvider();
            provider.setUserDetailsService(userDetailsService);
            return provider;

        }
}
