package com.imdb.web;

import com.imdb.model.Movie;
import com.imdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@Controller
public class HomeController {

	private MovieService movieService;

	@Autowired
	public HomeController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String doHome(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Map<String, String> reqParams, ModelMap model) {
		Collection<Movie> movies;
		if (reqParams.get("search") != null){
			movies = movieService.findAll(userDetails, reqParams.get("search"));
		} else {
			movies = movieService.findAll(userDetails);
		}
		model.addAttribute("movies", movies);
		return "home";
	}

}