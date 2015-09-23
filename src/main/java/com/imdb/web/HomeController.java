package com.imdb.web;

import com.imdb.model.Director;
import com.imdb.model.Movie;
import com.imdb.service.DirectorService;
import com.imdb.service.MovieService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

	private MovieService movieService;
	private DirectorService directorService;

	@Autowired
	public HomeController(MovieService movieService, DirectorService directorService) {
		this.movieService = movieService;
		this.directorService = directorService;
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

	@RequestMapping(value = "/refresh",method = RequestMethod.GET)
	public String doRefresh() {
		Director director = directorService.findById(1);
//		int size = director.getMovies().size();
//	 	List<Movie> movies = director.getMovies();
//
////		Movie movie1 = movies.get(0);
//		Movie movie2 = movieService.findById(6);
//		Collection<Movie> movies = movieService.findAll();
//		movieService.refreshAverageRating(movies);
//		Movie movie = movieService.findById(6);
//		movie.setAverageRating(3.0);
//		movieService.update(movie);
//		Director director = directorService.findById(1);
//		director.setName("Steven Spielbergg");
//		directorService.update(director);
//		Movie movie = movieService.findById(6);
//		movieService.remove(movie);
//		movieService.save(movie);
		return "redirect:/";
	}
}