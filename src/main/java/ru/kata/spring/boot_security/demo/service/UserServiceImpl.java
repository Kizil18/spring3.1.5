package ru.kata.spring.boot_security.demo.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }


//    @Override
//    @Transactional
//    public void updateUser(User user) {
//        userRepository.save(user);
//    }

    @Override
    @Transactional
    public void updateUser(int id, User updateUser) {
        User userToUpdate = userRepository.findById(id).get();
        if (userToUpdate.getPassword().equals(updateUser.getPassword())) {
            userRepository.save(updateUser);
        } else {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userRepository.save(updateUser);
        }


    }


    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
