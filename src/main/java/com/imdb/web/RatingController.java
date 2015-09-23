package com.imdb.web;

import com.imdb.utils.validation.RatingForm;
import com.imdb.service.MovieService;
import com.imdb.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final MovieService movieService;

    @Autowired
    public RatingController(RatingService ratingService, MovieService movieService) {
        this.ratingService = ratingService;
        this.movieService = movieService;
    }

    @RequestMapping(value = "/rate/{movieId}", method = RequestMethod.POST)
    public String doRate(@AuthenticationPrincipal UserDetails userdeltails,
                         @Valid RatingForm ratingForm,
                         ModelMap model,
                         @PathVariable Integer movieId){
        ratingService.rateMovie(userdeltails, movieService.findById(movieId), ratingForm.getValue());
        return "redirect:/";
    }
}
