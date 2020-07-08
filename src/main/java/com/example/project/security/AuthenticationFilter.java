package com.example.project.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project.dto.LoginModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * System autentykacji użytkownika
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;

	public AuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	/**
	 * Metoda próbująca dokonać autentykacji użytkowniak.
	 * Za pomocą requesta pobierana jest nazwa użytkowniak oraz hasło.
	 * Następnie na podstawie przesłanych danych tworzony jest token do autentykacji
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response)
			throws AuthenticationException {
		LoginModel loginModel = null;
		try {
			loginModel = new ObjectMapper()
					.readValue(request.getInputStream(), LoginModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(
						loginModel.getUsername(),
						loginModel.getPassword(),
						new ArrayList<>()
				);

		return authManager.authenticate(authToken);
	}

	/**
	 * Jeżeli użytkownik został zalogowany poprawnie, do hedearsów będzie wysyłany JWT token
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
		String token = JWT
				.create()
				.withSubject(userPrincipal.getUsername())
				.withClaim("roles", userPrincipal.getAuthorities().toString())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecProperties.TIME))
				.sign(Algorithm.HMAC512(SecProperties.PASSWORD.getBytes()));
		response.addHeader(SecProperties.HEADER, SecProperties.PREFIX + token);
	}
}
