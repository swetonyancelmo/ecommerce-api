package com.swetonyancelmo.ecommerce.security;

import com.swetonyancelmo.ecommerce.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.ecommerce.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(@NonNull String email) {
        UserDetails user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }
        return user;
    }
}
