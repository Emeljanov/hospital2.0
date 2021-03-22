package by.emel.anton.security;

import by.emel.anton.security.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            Optional.of(servletRequest)
                    .map(HttpServletRequest.class::cast)
                    .map(jwtTokenProvider::resolveToken)
                    .filter(jwtTokenProvider::validateToken)
                    .map(jwtTokenProvider::getAuthentication)
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

         /*   String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
*/
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
