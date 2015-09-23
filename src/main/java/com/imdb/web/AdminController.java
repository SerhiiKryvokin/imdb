package com.imdb.web;

import com.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private MovieRepository movieRepository;

    @Autowired
    public AdminController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }



}
