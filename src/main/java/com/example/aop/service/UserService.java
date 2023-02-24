package com.example.aop.service;

import com.example.aop.model.Users;
import com.example.aop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public Users getCurrentUser() {
        return usersRepository.findById(1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id 1 not found"));
    }
}
