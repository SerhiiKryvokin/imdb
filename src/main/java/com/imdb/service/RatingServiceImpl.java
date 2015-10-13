package com.imdb.service;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import com.imdb.model.composite.RatingId;
import com.imdb.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;
    private UserService userService;
    private MovieService movieService;

    @Autowired
    RatingServiceImpl(RatingRepository ratingRepository, UserService userService, MovieService movieService){
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Override
    public Collection<Rating> findAll() throws DataAccessException {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(RatingId id) throws DataAccessException {
        return ratingRepository.findById(id);
    }

    @Override
    public void save(Rating rating) throws DataAccessException {
        ratingRepository.save(rating);
    }

    @Override
    public void update(Rating rating) throws DataAccessException {
        ratingRepository.update(rating);
    }

    @Override
    public void rateMovie(UserDetails userDetails, Movie movie, Integer value) throws DataAccessException {
        Rating rating = new Rating();
        RatingId ratingId = new RatingId();
        ratingId.setUser(userService.findOneByLogin(userDetails.getUsername()));
        ratingId.setMovie(movie);
        rating.setPrimaryKey(ratingId);
        rating.setRatingValue(value);
        ratingRepository.save(rating);
        movieService.commitRating(rating);
    }
}
