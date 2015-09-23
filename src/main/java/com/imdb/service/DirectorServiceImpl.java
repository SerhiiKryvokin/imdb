package com.imdb.service;

import com.imdb.model.Director;
import com.imdb.model.Movie;
import com.imdb.repository.DirectorRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class DirectorServiceImpl implements DirectorService{

    private DirectorRepository directorRepository;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Collection<Director> findAll() throws DataAccessException {
        return directorRepository.findAll();
    }

    @Override
    public Collection<Director> findByName(String s) throws DataAccessException {
        return directorRepository.findByName(s);
    }

    @Override
    public Director findById(Integer id) throws DataAccessException {
        return directorRepository.findById(id);
    }

    @Override
    public void save(Director director) throws DataAccessException {
        directorRepository.save(director);
    }

    @Override
    public void update(Director director) throws DataAccessException {
        directorRepository.update(director);
    }
}
