package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.model.Movie;
import com.hcl.service.MovieService;

@RestController
@RequestMapping("/moives")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Movie create(@RequestBody Movie movie) {
		return movieService.save(movie);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Movie> getAll() {
		return movieService.getAllMovies();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Movie getOneMovieById(@PathVariable Long id) {
		return movieService.getMovieById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Movie upodateMovie(@RequestBody Movie movie, @PathVariable Long id) {
		return movieService.updateMovie(movie, id);
	}
}
