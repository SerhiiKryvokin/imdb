package com.imdb.model;

import com.imdb.model.composite.RatingId;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

    @EmbeddedId
    private RatingId primaryKey = new RatingId();

    @Column(name = "rating_value")
    private Integer ratingValue;

    public RatingId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(RatingId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }
}
