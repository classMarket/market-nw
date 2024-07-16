package market_nw.market_nw.security;

import market_nw.market_nw.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
//        System.out.println("여기test1");
//        System.out.println("token이란:" + token);

        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰이 있으며,유효성이 있을시
//            System.out.println("유효성있음");
            String email = jwtTokenProvider.getEmail(token);
//            System.out.println("email이란:" + email);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UserDetails userDetails = userService.loadUserByUsername(email);
            System.out.println("userDetails이란:" + userDetails);
            if (userDetails != null) {
//                System.out.println("유효성 있으면서 정보가 있음");
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }

}
