package com.campssg.config.jwt;

import com.campssg.config.auth.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// 인가
public class JwtTokenProviderFilter extends BasicAuthenticationFilter {

    private final PrincipalDetailsService principalDetailsService;
    // private final UserRepository userRepository;

    public JwtTokenProviderFilter(AuthenticationManager authenticationManager,
        PrincipalDetailsService principalDetailsService) {
        super(authenticationManager);
        this.principalDetailsService = principalDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("header : " + header);
        String token = request.getHeader(JwtProperties.HEADER_STRING)
            .replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
        String username = Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token).getBody()
            .getSubject();

        if (username != null) {
            UserDetails user = principalDetailsService.loadUserByUsername(username);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            // PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                    user, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    user.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public String createToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);

        return Jwts.builder()
            .setSubject(userEmail)
            .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET)
            .compact();
    }
}
