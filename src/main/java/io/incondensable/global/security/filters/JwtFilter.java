package io.incondensable.global.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import io.incondensable.global.security.service.TokenService;
import io.incondensable.global.security.util.JwtUtil;
import io.incondensable.global.security.vo.FeedbackUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author abbas
 */
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtUtil.extractJwtToken(request).ifPresentOrElse(
                jwt -> {
                    String username = jwtUtil.getUserNameFromJwtToken(jwt);
                    try {
                        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) createAuthentication(username);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                    } catch (BusinessException e) {
                        jwtInvalidError(username, response);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> {
                    try {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "failed to authorize.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private Authentication createAuthentication(String username) throws BusinessException {
        FeedbackUserDetails userDetails = (FeedbackUserDetails) userDetailsService.loadUserByUsername(username);
        tokenService.validateTokenExpiration(userDetails.getId());
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private void jwtInvalidError(String username, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            PrintWriter writer = response.getWriter();
            objectMapper.writeValue(writer, new ErrorDetails("Jwt Token is not valid.", HttpStatus.NOT_ACCEPTABLE, username));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        if (servletPath.contains("/swagger") || servletPath.contains("/v3/api-docs") || servletPath.contains("/docs"))
            return true;

        if (servletPath.contains("/auth")) {
            if (servletPath.contains("/logout")) {
                return false;
            }
            return true;
        }

        return false;
    }

}
