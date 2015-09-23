package com.imdb.repository.JPA;

import com.imdb.model.Movie;
import com.imdb.model.User;
import com.imdb.repository.MovieRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


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
    public Collection<Movie> findAll(UserDetails userDetails) throws DataAccessException {
//        if (userDetails == null)
//            return findAll(); service

        TypedQuery<Object[]> query = this.entityManager.createQuery
                ("select movie, rating.ratingValue from Movie movie " +
                        "left join movie.ratings rating " +
                        "left join rating.primaryKey.user user " +
                        "on(user.login = :login)", Object[].class);

        query.setParameter("login", userDetails.getUsername());
        return setUserMovieRatings(query.getResultList());

    }

    @Override
    public Collection<Movie> findAll(UserDetails userDetails, String s) throws DataAccessException {
        TypedQuery<Object[]> query = this.entityManager.createQuery
                ("select movie, rating.ratingValue from Movie movie " +
                        "left join movie.ratings rating on(rating.primaryKey.user.id = :id) " +
//                        "join rating.primaryKey.user user " +
//                        "on(user.login = :login)" +
                        "where lower(movie.title) like :s", Object[].class);

//        query.setParameter("login", userDetails.getUsername());
        query.setParameter("id", 1); //move logic to service!!!!!!!!!!!
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

    @Override
    public void refreshAverageRating(Movie movie) throws DataAccessException {
        int sum = 0;
        double average = 0;
        Query query = this.entityManager.createQuery("select rating.ratingValue from Rating rating where rating.primaryKey.movie.id = :id");
        query.setParameter("id", movie.getId());
        List<Integer> values = query.getResultList();
        if (!values.isEmpty()){
            for (Integer value : values){
                sum += value;
            }
            average = sum/values.size();
            movie.setAverageRating(average);
            movie.setRatingsCount(values.size());

            update(movie);
        }

    }

    private Collection<Movie> setUserMovieRatings(List<Object[]> objects){
        List<Movie> movies = new ArrayList<>();

        for (Object[] o : objects){
            Movie m = (Movie)o[0];
            if (o[1] != null){
                Integer currentUserRating = (Integer)o[1];
                m.setCurrentUserRating(currentUserRating);
            }
            movies.add(m);
        }
        return movies;
    }
}
