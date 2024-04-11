package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import com.hcl.model.Movie;
import com.hcl.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
public class RefactorMovieServiceTestCode {

	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private MovieService movieService;

	private Movie movie1;
	private Movie movie2;

	@BeforeEach
	void init() {

		movie1 = new Movie();
		movie1.setId(1L);
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		movie2 = new Movie();
		movie2.setId(2L);
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));
	}

	@Test
	@DisplayName("it should save the object into database")
	void save() {

		when(movieRepository.save(any(Movie.class))).thenReturn(movie1);

		Movie newMovie = movieService.save(movie1);

		assertNotNull(newMovie);
		assertEquals("Salaar", newMovie.getMovieName());
	}

	@Test
	@DisplayName("it should return all movies")
	void displayAllMovies() {

		List<Movie> list = new ArrayList<Movie>();
		list.add(movie1);
		list.add(movie2);

		when(movieRepository.findAll()).thenReturn(list);

		List<Movie> allMovies = movieService.getAllMovies();

		assertEquals(2, allMovies.size());
		assertNotNull(allMovies);
	}

	@Test
	@DisplayName("it should get movie by id")
	void getMovieById() {

		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie1));

		Movie existingMovie = movieService.getMovieById(1L);

		assertNotNull(existingMovie);
		assertEquals("Salaar", existingMovie.getMovieName());
	}

	@Test
	@DisplayName("it should throw exception for get movie by id")
	void getMovieByIdException() {

		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

		assertThrows(RuntimeException.class, () -> {
			movieService.getMovieById(2L);
		});

	}

	@Test
	@DisplayName("it should update the object")
	void updateMovie() {

		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie1));
		when(movieRepository.save(any(Movie.class))).thenReturn(movie1);
		movie1.setMovieName("Kalki");

		movieService.updateMovie(movie1, 1L);

		assertNotNull(movie1);
		assertEquals("Kalki", movie1.getMovieName());
	}

	@Test
	@DisplayName("it should delete the movie")
	void deleteMovie() {

		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie1));
		doNothing().when(movieRepository).delete(any(Movie.class));

		movieService.deleteMovie(1L);

		verify(movieRepository, times(1)).delete(movie1); // here it will verify that the movieRepository is called 1
															// time or not.

	}

}
