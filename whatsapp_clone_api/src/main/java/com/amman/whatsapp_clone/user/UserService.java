package com.amman.whatsapp_clone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    public List<UserResponse> getAllUsersExceptSelf(Authentication authentication){
        return  repo.findAllUsersExceptSelf(authentication.getName())
                .stream()
                .map(u->
                        new UserResponse(u.getId()
                                ,u.getFirstName(),
                                u.getLastName(),
                                u.getEmail(),
                                u.getLastSeen(),
                                u.isUserOnline()))
                .toList();

    }

}
