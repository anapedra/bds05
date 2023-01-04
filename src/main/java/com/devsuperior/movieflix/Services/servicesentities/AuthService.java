package com.devsuperior.movieflix.Services.servicesentities;

import com.devsuperior.movieflix.Services.exceptionservice.ForbiddenException;
import com.devsuperior.movieflix.Services.exceptionservice.UnauthorizedException;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User authenticated(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        }
        catch (Exception e){
            throw new UnauthorizedException("Invalid User!");
        }
    }
    public void validateSelfOrMember(Long id){
        User user=authenticated();
        if (!user.getId().equals(id) && !user.hasHole( "ROLE_MEMBER")){
            throw new ForbiddenException("Access denied");
        }



    }

}
