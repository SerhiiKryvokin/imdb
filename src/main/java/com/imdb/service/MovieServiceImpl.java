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

/**
 * Implements API for all required logic related to movies.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private UserService userService;

    /**
     * Autowiring beans.
     * @param movieRepository repository bean that is responsible for any db operations related to movies.
     * @param userService implements API for all required logic related to user.
     */
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.userService = userService;
    }

    /**
     * See repository docs.
     * @return list of all movies available in db.
     * @throws DataAccessException
     */
    @Override
    public Collection<Movie> findAll() throws DataAccessException {
        return movieRepository.findAll();
    }

    /**
     * See repository docs.
     * @param s the string that will be specified for search pattern
     * @return list of movies matched the pattern.
     * @throws DataAccessException
     */
    @Override
    public Collection<Movie> findAll(String s) throws DataAccessException {
        return movieRepository.findAll(s);
    }

    /**
     *
     * @param userDetails information about logged user
     * @return list of movies rated by logged user.
     * @throws DataAccessException
     */
    @Override
    public Collection<Movie> findAll(UserDetails userDetails) throws DataAccessException {
        if (userDetails == null)
            return movieRepository.findAll();
        else
            return movieRepository.findAll(userService.findOneByLogin(userDetails.getUsername()));
    }

    /**
     *
     * @param userDetails information about logged user
     * @param s the string that will be specified for search pattern
     * @return list of movies rated by logged user and matched the pattern
     * @throws DataAccessException
     */
    @Override
    public Collection<Movie> findAll(UserDetails userDetails, String s) throws DataAccessException {
        if (userDetails == null)
            return movieRepository.findAll(s);
        else
            return movieRepository.findAll(userService.findOneByLogin(userDetails.getUsername()), s);
    }

    /**
     *
     * @param id movie id
     * @return movie with specified id
     * @throws DataAccessException
     */
    @Override
    public Movie findById(Integer id) throws DataAccessException {
        return movieRepository.findById(id);
    }

    /**
     *
     * @param movie the movie to save
     * @throws DataAccessException
     */
    @Override
    public void save(Movie movie) throws DataAccessException {
        movieRepository.save(movie);
    }

    /**
     *
     * @param movie the movie that already exist in db and need to be updated
     * @throws DataAccessException
     */
    @Override
    public void update(Movie movie) throws DataAccessException {
        movieRepository.update(movie);
    }

    /**
     *
     * @param movie movie to be removed
     * @throws DataAccessException
     */
    @Override
    public void remove(Movie movie) throws DataAccessException {
        movieRepository.remove(movie);
    }

    /**
     *
     * @param id id of the movie to be removed
     * @throws DataAccessException
     */
    @Override
    public void remove(Integer id) throws DataAccessException {
        movieRepository.remove(movieRepository.findById(id));
    }

    /**
     * Recalculates average rating of all movies
     * @throws DataAccessException
     */
    @Override
    public void refreshAverageRating() throws DataAccessException {
        refreshAverageRating(movieRepository.findAll());
    }

    /**
     * Recalculates average rating of the movie that was recently rated
     * @param rating new Rating instance, created after performing rate operation by some user
     * @throws DataAccessException
     */
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

    /**
     * Recalculates average rating of all movies contained in the specified Collection.
     * @param movies the collection of movies for average rating recalculation
     * @throws DataAccessException
     */
    private void refreshAverageRating(Collection<Movie> movies) throws DataAccessException {
        for (Movie movie : movies){
            refreshAverageRating(movie);
        }
    }

    /**
     * Recalculates average rating of the specified movie
     * @param movie the movie whose average rating should be recalculated
     * @throws DataAccessException
     */
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
