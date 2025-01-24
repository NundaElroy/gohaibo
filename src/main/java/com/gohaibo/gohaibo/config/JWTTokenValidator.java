package com.gohaibo.gohaibo.config;

import com.gohaibo.gohaibo.utility.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;

@Component
public class JWTTokenValidator  extends OncePerRequestFilter {

     private  final JwtTokenProvider jwtTokenProvider;

    public JWTTokenValidator(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    private final String SECRET_KEY;
//
//    public JWTTokenValidator(@Value("${jwt.secret.key}") String secretKey) {
//        this.SECRET_KEY = secretKey;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Skip JWT validation for /auth/** endpoint
        String path = request.getRequestURI();
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);//Bearer length is 7

        if (token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try{


            if(!jwtTokenProvider.validateToken(token)){
                throw new BadCredentialsException("invalid token....");
            }
//            // This is the secret key used to sign the JWT
//            SecretKey secretkey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//            Claims claims = Jwts.parser()
//                    .verifyWith(secretkey)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();

            String email = jwtTokenProvider.getEmail(authorizationHeader);
            String authorities = "ROLE_USER";

            List<GrantedAuthority>  auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            throw new BadCredentialsException("invalid token....");
        }

        filterChain.doFilter(request, response);





    }
}
