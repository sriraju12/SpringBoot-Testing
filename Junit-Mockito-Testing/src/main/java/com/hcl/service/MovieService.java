package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Movie;
import com.hcl.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public Movie getMovieById(Long id) {
		return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
	}

	public Movie updateMovie(Movie movie, Long id) {
		Movie existingMovie = movieRepository.findById(id).get();
		existingMovie.setMovieName(movie.getMovieName());
		existingMovie.setGenere(movie.getGenere());
		existingMovie.setReleaseDate(movie.getReleaseDate());
		return movieRepository.save(existingMovie);

	}

	public void deleteMovie(Long id) {
		Movie existingMovie = movieRepository.findById(id).get();
		movieRepository.delete(existingMovie);
	}

}
