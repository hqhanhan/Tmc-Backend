package com.qk.seed.service.impl;

import com.qk.seed.model.dto.CustomUserDetails;
import com.qk.seed.model.po.User;
import com.qk.seed.dao.repository.UserRepository;
import com.qk.seed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.selectUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user '" + username + "'");
        }

        // Not involve authorities, so pass null to authorities
        return new CustomUserDetails(user, true, true, true, true, null);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.selectUserById(id));
    }

    @Override
    public boolean saveUser(User user) {
        return userRepository.insertUser(user) > 0;
    }

    @Override
    public boolean modifyUserOnPasswordById(User user) {
        return userRepository.updateUserOnPasswordById(user) > 0;
    }

    @Override
    public boolean deleteUserById(Long id) {
        return userRepository.deleteUserById(id) > 0;
    }

}
