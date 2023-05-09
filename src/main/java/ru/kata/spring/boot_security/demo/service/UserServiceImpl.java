package ru.kata.spring.boot_security.demo.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;
import java.util.Optional;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword().equals(user.getPassword())) {
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

    }

//    @Override
//    @Transactional
//    public void updateUser(int id, User updateUser) {
//        User userToUpdate = userRepository.findById(id).get();
//        if (userToUpdate.getPassword().equals(updateUser.getPassword())) {
//            userRepository.save(updateUser);
//        } else {
//            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
//            userRepository.save(updateUser);
//        }
//
//
//    }


    @Override
    public User getUser(int id) {
        Optional<User> getUser = userRepository.findById(id);
        return getUser.orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
