package com.imdb.service;

import com.imdb.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface UserService {
    Collection<User> findAll() throws DataAccessException;
    Collection<User> findByName(String s) throws DataAccessException;
    User findById(Integer id) throws DataAccessException;
    User findOneByLogin(String login) throws DataAccessException;
    Boolean isLogPassValid(String login, String password) throws DataAccessException;
    void save(User user) throws DataAccessException;
    void update(User user) throws DataAccessException;
}
