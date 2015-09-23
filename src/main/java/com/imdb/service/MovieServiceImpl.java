package com.imdb.service;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import com.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Collection<Movie> findAll() throws DataAccessException {
        return movieRepository.findAll();
    }

    @Override
    public Collection<Movie> findAll(String s) throws DataAccessException {
        return movieRepository.findAll(s);
    }

    @Override
    public Collection<Movie> findAll(UserDetails userDetails) throws DataAccessException {
        if (userDetails == null)
            return movieRepository.findAll();
        else
            return movieRepository.findAll(userDetails);
    }

    @Override
    public Collection<Movie> findAll(UserDetails userDetails, String s) throws DataAccessException {
        if (userDetails == null)
            return movieRepository.findAll(s);
        else
            return movieRepository.findAll(userDetails, s);
    }

    @Override
    public Movie findById(Integer id) throws DataAccessException {
        return movieRepository.findById(id);
    }

    @Override
    public void save(Movie movie) throws DataAccessException {
        movieRepository.save(movie);
    }

    @Override
    public void update(Movie movie) throws DataAccessException {
        movieRepository.update(movie);
    }

    @Override
    public void remove(Movie movie) throws DataAccessException {
        movieRepository.remove(movie);
    }

    @Override
    public void refreshAverageRating(Movie movie) throws DataAccessException {
        movieRepository.refreshAverageRating(movie);
    }

    @Override
    public void refreshAverageRating(Collection<Movie> movies) throws DataAccessException {
        for (Movie movie : movies){
            refreshAverageRating(movie);
        }
    }

    @Override
    public void commitRating(Rating rating) throws DataAccessException {
        Movie movie = rating.getPrimaryKey().getMovie();
        Integer ratingsCount = movie.getRatingsCount();
        movie.setRatingsCount(ratingsCount == null ? 1 : ratingsCount + 1);
        Double averageRating = movie.getAverageRating();
        double average = (movie.getAverageRating() == null ? 0 : averageRating + rating.getRatingValue()) / (double)movie.getRatingsCount();
        movie.setAverageRating(average);
        movieRepository.update(movie);
    }
}
