package com.hcl.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hcl.model.Movie;
import com.hcl.repository.MovieRepository;

@DataJpaTest
public class RefactorMovieRepoTestCode {

	@Autowired
	private MovieRepository movieRepository;

	private Movie movie;
	private Movie movie2;

	@BeforeEach
	void init() {

		Movie movie = new Movie();
		movie.setMovieName("Salaar");
		movie.setGenere("Action");
		movie.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie movie2 = new Movie();
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));
	}

	@Test
	@DisplayName("it should save movie")
	void save() {

		Movie addedMovie = movieRepository.save(movie);
		assertNotNull(addedMovie);
	}

	@Test
	@DisplayName("it should return all movies")
	void display() {

		movieRepository.save(movie);
		movieRepository.save(movie2);

		// ACT
		List<Movie> allMovies = movieRepository.findAll();

		// assert
		assertNotNull(allMovies);
		assertEquals(2, allMovies.size());
	}

	@Test
	@DisplayName("it should return movie based on id")
	void getByIdMovie() {
		// Arrange
		movieRepository.save(movie);

		// ACT
		Movie existingMovie = movieRepository.findById(movie.getId()).get();

		// assert
		assertNotNull(existingMovie);
		assertEquals("Action", existingMovie.getGenere());
	}

	@Test
	@DisplayName("it should update movie")
	void update() {
		// Arrange
		movieRepository.save(movie);
		Movie existingMovie = movieRepository.findById(movie.getId()).get();

		// ACT
		existingMovie.setGenere("Mass");
		Movie updatedMovie = movieRepository.save(existingMovie);

		// assert
		assertNotNull(updatedMovie);
		assertEquals("Mass", existingMovie.getGenere());
		assertEquals("Salaar", existingMovie.getMovieName());

	}

	@Test
	@DisplayName("it should delete movie")
	void delete() {
		// Arrange
		movieRepository.save(movie);
		movieRepository.save(movie2);

		// ACT
		movieRepository.delete(movie);
		List<Movie> list = movieRepository.findAll();

		// assert
		assertEquals(1, list.size());
	}

}
