package com.acme.transferator.infra.repository;

import com.acme.transferator.application.repository.UserRepository;
import com.acme.transferator.application.domain.User;

import java.util.HashMap;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private HashMap<String, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
    }

    public InMemoryUserRepository(HashMap<String, User> users) {
        this.users = users;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void save(User user) {
        users.put(user.getIdentifier(), user);
    }
}
