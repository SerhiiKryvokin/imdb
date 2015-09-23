package com.imdb.service;

import com.imdb.model.Director;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface DirectorService {
    Collection<Director> findAll() throws DataAccessException;
    Collection<Director> findByName(String s) throws DataAccessException;
    Director findById(Integer id) throws DataAccessException;
    void save(Director director) throws DataAccessException;
    void update(Director director) throws DataAccessException;
}
