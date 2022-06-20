package com.company.eaiesb.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.eaiesb.models.UserSample;
import com.company.eaiesb.repository.SampleUserRepository;
import com.company.eaiesb.util.JwtUtil;


@Service
public class JwtRequestFilter extends OncePerRequestFilter{

	
	@Autowired
	private SampleUserRepository sampleUserRepo;

    @Autowired
    private JwtUtil jwtUtil;
    
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		
		 final String authorizationHeader = request.getHeader("Authorization");

	        String mail = null;
	        String jwt = null;

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            mail = jwtUtil.extractEmail(jwt);
	        }


	        if (mail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	        	UserSample userSample = this.sampleUserRepo.findByEmail(mail);

	            if (jwtUtil.validateToken(jwt, userSample)) {

	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                		userSample, null, null);
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
	        chain.doFilter(request, response);
		
	}

}
