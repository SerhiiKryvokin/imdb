package com.imdb.web;

import com.imdb.repository.MovieRepository;
import com.imdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private MovieService movieService;

    @Autowired
    public AdminController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/movie/delete/{movieId}", method = RequestMethod.POST)
    public String deleteMovie(@PathVariable Integer movieId){
        movieService.remove(movieId);
        return "redirect:/";
    }

}
