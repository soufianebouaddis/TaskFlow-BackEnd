package os.org.taskflow.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import os.org.taskflow.auth.service.impl.UserDetailService;
import os.org.taskflow.constant.Constant;
import os.org.taskflow.security.config.AuthenticationEntry;
import os.org.taskflow.security.config.CsrfCookieFilter;
import os.org.taskflow.security.config.SpaCsrfTokenRequestHandler;
import os.org.taskflow.security.service.JwtService;
import os.org.taskflow.security.service.impl.JwtServiceImpl;

import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class ApiSecurity {
    private final JwtService jwtService;
    private final JwtFilter jwtFilter;
    private final UserDetailService userDetailsService;
    private final AuthenticationEntry authenticationEntry;
    @Value("${jwt-keys.public_key}")
    private String public_key;
    private final String[] matchers = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refreshToken",
            "/api/v1/auth/logout",
    };

    @Autowired
    public ApiSecurity(JwtService jwtService, JwtFilter jwtFilter, UserDetailService userDetailsService, AuthenticationEntry authenticationEntry) {
        this.jwtService = jwtService;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
        this.authenticationEntry = authenticationEntry;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(csrf -> {
                    csrf
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                            .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                            .ignoringRequestMatchers(matchers);
                }).addFilterBefore(new CsrfCookieFilter(), CsrfFilter.class)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntry))
                .oauth2ResourceServer(authorize -> authorize.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/auth/register").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/auth/refreshToken").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/auth/login").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/auth/public-key").permitAll())
                .authorizeHttpRequests(
                authorize -> authorize.requestMatchers("/swagger-ui/**", "/swagger-ui.html",
                        "/v3/api-docs/**").permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .permitAll()
                        .logoutSuccessHandler((req, res, auth) -> {
                            String origin = req.getHeader("Origin");
                            if ("http://localhost:5173".equals(origin)) {
                                res.setHeader("Access-Control-Allow-Origin", origin);
                            }
                            res.setHeader("Access-Control-Allow-Credentials", "true");
                            res.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies(
                                "XSRF-TOKEN",
                                Constant.ACCESS_TOKEN)
                        .clearAuthentication(true)
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        final RSAPublicKey rsaPublicKey = (RSAPublicKey) this.jwtService.loadPublicKey(public_key);
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
        return decoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                16,
                32,
                1,
                1 << 12,
                3
        );
    }

}
