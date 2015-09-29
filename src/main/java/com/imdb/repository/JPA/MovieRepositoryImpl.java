package com.imdb.repository.JPA;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import com.imdb.model.User;
import com.imdb.repository.MovieRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Movie> findAll() throws DataAccessException {
        return this.entityManager.createQuery("select movie from Movie movie").getResultList();
    }

    @Override
    public Movie findById(Integer id) throws DataAccessException {
        Query query = this.entityManager.createQuery("select movie from Movie movie where movie.id = :id");
        query.setParameter("id", id);
        return (Movie) query.getSingleResult();
    }

    @Override
    public Collection<Movie> findAll(String s) throws DataAccessException {
        Query query = this.entityManager.createQuery("select movie from Movie movie where lower(movie.title) like :s");
        query.setParameter("s", "%" + s + "%");
        return query.getResultList();
    }

    @Override
    public Collection<Movie> findAll(User user) throws DataAccessException {

        TypedQuery<Object[]> query = this.entityManager.createQuery
                ("select movie, rating.ratingValue from Movie movie " +
                        "left join movie.ratings rating on(rating.primaryKey.user.id = :id)", Object[].class);

        query.setParameter("id", user.getId());

        return setUserMovieRatings(query.getResultList());

    }

    @Override
    public Collection<Movie> findAll(User user, String s) throws DataAccessException {

        TypedQuery<Object[]> query = this.entityManager.createQuery
                ("select movie, rating.ratingValue from Movie movie " +
                        "left join movie.ratings rating on(rating.primaryKey.user.id = :id) " +
                        "where lower(movie.title) like :s", Object[].class);

        query.setParameter("id", user.getId());
        query.setParameter("s", "%" + s + "%");

        return setUserMovieRatings(query.getResultList());
    }

    @Override
    public void save(Movie movie) throws DataAccessException {
        this.entityManager.persist(movie);
    }

    @Override
    public void update(Movie movie) throws DataAccessException {
        this.entityManager.merge(movie);
    }

    @Override
    public void remove(Movie movie) throws DataAccessException {
        this.entityManager.remove(movie);
    }

//    @Override
//    public void refreshAverageRating(Movie movie) throws DataAccessException {
//        int sum = 0;
//        double average = 0;
////        Query query = this.entityManager.createQuery("select rating.ratingValue from Rating rating where rating.primaryKey.movie.id = :id");
////        query.setParameter("id", movie.getId());
////        List<Integer> values = query.getResultList();
//        List<Integer> values = new ArrayList<>();
//        List<Rating> ratings = movie.getRatings();
//        for (Rating r : ratings){
//            values.add(r.getRatingValue());
//        }
//        if (!values.isEmpty()) {
//            for (Integer value : values) {
//                sum += value;
//            }
//            average = (double)sum / values.size();
//            movie.setAverageRating(average);
//            movie.setRatingsCount(values.size());
//        } else {
//            movie.setAverageRating(null);
//            movie.setRatingsCount(null);
//        }
//        update(movie);
//    }

    private Collection<Movie> setUserMovieRatings(List<Object[]> objects) {
        List<Movie> movies = new ArrayList<>();

        for (Object[] o : objects) {
            Movie m = (Movie) o[0];
            if (o[1] != null) {
                Integer currentUserRating = (Integer) o[1];
                m.setCurrentUserRating(currentUserRating);
            }
            movies.add(m);
        }
        return movies;
    }
}
