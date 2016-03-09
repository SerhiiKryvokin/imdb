package com.imdb.web;

import com.imdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    public String doRefresh() {
        movieService.refreshAverageRating();
        return "redirect:/";
    }

}
