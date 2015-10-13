package com.imdb.service;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import com.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private UserService userService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.userService = userService;
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
            return movieRepository.findAll(userService.findOneByLogin(userDetails.getUsername()));
    }

    @Override
    public Collection<Movie> findAll(UserDetails userDetails, String s) throws DataAccessException {
        if (userDetails == null)
            return movieRepository.findAll(s);
        else
            return movieRepository.findAll(userService.findOneByLogin(userDetails.getUsername()), s);
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
    public void remove(Integer id) throws DataAccessException {
        movieRepository.remove(movieRepository.findById(id));
    }

    @Override
    public void refreshAverageRating() throws DataAccessException {
        refreshAverageRating(movieRepository.findAll());
    }

    @Override
    public void commitRating(Rating rating) throws DataAccessException {
        Movie movie = rating.getPrimaryKey().getMovie();
        Integer ratingsCount = movie.getRatingsCount();
        Double averageRating = movie.getAverageRating();
        if (ratingsCount == null) {
            movie.setAverageRating(rating.getRatingValue().doubleValue());
            movie.setRatingsCount(1);
        } else {
            movie.setRatingsCount(ratingsCount + 1);
            double average = (averageRating*ratingsCount + rating.getRatingValue()) / (double)movie.getRatingsCount();
            movie.setAverageRating(average);
        }

        movieRepository.update(movie);
    }

    private void refreshAverageRating(Collection<Movie> movies) throws DataAccessException {
        for (Movie movie : movies){
            refreshAverageRating(movie);
        }
    }

    private void refreshAverageRating(Movie movie) throws DataAccessException {
        int sum = 0;
        double average = 0;
//        Query query = this.entityManager.createQuery("select rating.ratingValue from Rating rating where rating.primaryKey.movie.id = :id");
//        query.setParameter("id", movie.getId());
//        List<Integer> values = query.getResultList();
        List<Integer> values = new ArrayList<>();
        List<Rating> ratings = movie.getRatings();
        for (Rating r : ratings){
            values.add(r.getRatingValue());
        }
        if (!values.isEmpty()) {
            for (Integer value : values) {
                sum += value;
            }
            average = (double)sum / values.size();
            movie.setAverageRating(average);
            movie.setRatingsCount(values.size());
        } else {
            movie.setAverageRating(null);
            movie.setRatingsCount(null);
        }
        movieRepository.update(movie);
    }
}
