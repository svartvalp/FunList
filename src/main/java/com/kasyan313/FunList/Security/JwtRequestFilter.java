package com.kasyan313.FunList.Security;

import com.kasyan313.FunList.Models.User;
import com.kasyan313.FunList.Services.UserService;
import com.kasyan313.FunList.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.split(" ")[1];
        }
        User foundUser = null;
        try {
            foundUser = userService.findUserById(jwtUtils.extractId(token));
        } catch (Exception e) {
        }
        if(SecurityContextHolder.getContext().getAuthentication() == null && token != null && foundUser != null) {
           UserDetailsImpl userDetails = new UserDetailsImpl(foundUser);
           if(jwtUtils.validateToken(token, foundUser)) {
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                       UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
