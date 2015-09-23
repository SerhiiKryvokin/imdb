package com.imdb.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "imdb_user")
public class User {

    @Id
    @Column(name = "imdb_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "passhash")
    private String passHash;

    @Column(name = "user_role")
    private String role;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Rating> ratings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primaryKey.user")
    private List<Rating> ratings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
