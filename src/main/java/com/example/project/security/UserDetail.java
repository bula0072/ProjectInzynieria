package com.example.project.security;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Klasa implementuje <code>UserDetailService</code>.
 * Jest wykorzystywana przez DaoAuthenticationProvider
 * do pobierania danych związanych z użytkownikiem
 */
@Service
public class UserDetail implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByLogin(s);
        return new UserPrincipal(user);
    }
}
