package com.example.project.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * System autoryzacji użytkownika
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
	private final UserRepository userRepository;

	public AuthorizationFilter(AuthenticationManager authenticationManager,
							   UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	/**
	 * Sprawdzanie JWT tokena przesyłanego w requescie
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecProperties.HEADER);

		if (header == null || !header.startsWith(SecProperties.PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		Authentication authentication = getUsernamePasswordAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	/**
	 * Sprawdzanie czy JWT token wysłany w requeście jest poprawny
	 */
	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecProperties.HEADER)
				.replace(SecProperties.PREFIX, "");

		if (token != null) {
			String username = JWT.require(Algorithm.HMAC512(SecProperties.PASSWORD.getBytes()))
					.build()
					.verify(token)
					.getSubject();

			if (username != null) {
				User user = userRepository.findByLogin(username);
				UserPrincipal userPrincipal = new UserPrincipal(user);
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(username,
								null,
								userPrincipal.getAuthorities());
				return authenticationToken;
			}
			return null;
		}
		return null;
	}
}
