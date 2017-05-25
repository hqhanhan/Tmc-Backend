package com.qk.seed.dao.repository;

import com.qk.seed.model.po.User;

import java.util.List;


public interface UserRepository {

    User selectUserById(Long id);

    User selectUserByUsername(String username);

    List<User> selectAllUsers();

    Integer insertUser(User user);

    Integer updateUserOnPasswordById(User user);

    Integer deleteUserById(Long id);

}
