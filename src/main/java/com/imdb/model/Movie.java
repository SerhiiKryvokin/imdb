package com.imdb.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private String picture;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primaryKey.movie")
    private List<Rating> ratings;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "ratings_count")
    private Integer ratingsCount;

    @Transient
    private Integer currentUserRating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRatin) {
        this.averageRating = averageRatin;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Integer getCurrentUserRating() {
        return currentUserRating;
    }

    public void setCurrentUserRating(Integer currentUserRating) {
        this.currentUserRating = currentUserRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
