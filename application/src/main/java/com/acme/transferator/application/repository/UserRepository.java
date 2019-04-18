package com.acme.transferator.application.repository;

import com.acme.transferator.application.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String userId);

    void save(User user);
}
