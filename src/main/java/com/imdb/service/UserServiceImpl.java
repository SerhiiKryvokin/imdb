package com.imdb.service;

import com.imdb.model.User;
import com.imdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() throws DataAccessException {
        return userRepository.findAll();
    }

    @Override
    public Collection<User> findByName(String s) throws DataAccessException {
        return userRepository.findByLogin(s);
    }

    @Override
    public User findById(Integer id) throws DataAccessException {
        return userRepository.findById(id);
    }

    @Override
    public User findOneByLogin(String login) throws DataAccessException {
        return userRepository.findOneByLogin(login);
    }

    @Override
    public Boolean isLogPassValid(String login, String password) throws DataAccessException {
        User user;
        try {
            user = userRepository.findOneByLogin(login);
        }catch (NoResultException e){
            return false;
        }
        return new BCryptPasswordEncoder().matches(password, user.getPassHash());
    }

    @Override
    public void save(User user) throws DataAccessException {
        String hashedPass = new BCryptPasswordEncoder().encode(user.getPassHash());
        user.setPassHash(hashedPass);
        userRepository.save(user);
    }

    @Override
    public void update(User user) throws DataAccessException {
        userRepository.update(user);
    }
}
