package com.hcl.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.hcl.model.Movie;
import com.hcl.repository.MovieRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieIntegrationTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private MovieRepository movieRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void beforeSetup() {
		baseUrl = baseUrl + ":" + port + "/movies";
	}

	@AfterEach
	public void afterSetup() {
		movieRepository.deleteAll();
	}

	@Test
	void shouldCreateNewMovie() {

		Movie movie1 = new Movie();
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie newMovie = restTemplate.postForObject(baseUrl, movie1, Movie.class);

		assertNotNull(newMovie);
	}

	@Test
	void shouldGetAllMovies() {

		Movie movie1 = new Movie();
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie movie2 = new Movie();
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));

		restTemplate.postForObject(baseUrl, movie1, Movie.class);
		restTemplate.postForObject(baseUrl, movie2, Movie.class);

		List<Movie> list = restTemplate.getForObject(baseUrl, List.class);

		int count = movieRepository.findAll().size();
		assertEquals(2, count);
	}

	@Test
	void shouldGetOneMovie() {

		Movie movie1 = new Movie();
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie movie2 = new Movie();
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));

		movie1 = restTemplate.postForObject(baseUrl, movie1, Movie.class);
		movie2 = restTemplate.postForObject(baseUrl, movie2, Movie.class);

		Movie existingMovie = restTemplate.getForObject(baseUrl + "/" + movie1.getId(), Movie.class);

		assertNotNull(existingMovie);
		assertEquals("Salaar", existingMovie.getMovieName());

	}

	@Test
	void shouldDeleteMovie() {

		Movie movie1 = new Movie();
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie movie2 = new Movie();
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));

		movie1 = restTemplate.postForObject(baseUrl, movie1, Movie.class);
		movie2 = restTemplate.postForObject(baseUrl, movie2, Movie.class);

		restTemplate.delete(baseUrl + "/" + movie1.getId());

		int count = movieRepository.findAll().size();

		assertEquals(1, count);

	}

	@Test
	void shouldUpdateMovie() {

		Movie movie1 = new Movie();
		movie1.setMovieName("Salaar");
		movie1.setGenere("Action");
		movie1.setReleaseDate(LocalDate.of(2023, Month.DECEMBER, 22));

		Movie movie2 = new Movie();
		movie2.setMovieName("Kalki");
		movie2.setGenere("Periodic");
		movie2.setReleaseDate(LocalDate.of(2024, Month.MAY, 29));

		movie1 = restTemplate.postForObject(baseUrl, movie1, Movie.class);
		movie2 = restTemplate.postForObject(baseUrl, movie2, Movie.class);

		movie1.setMovieName("Saaho");

		restTemplate.put(baseUrl + "/{id}", movie1, movie1.getId());

		Movie existingMovie = restTemplate.getForObject(baseUrl + "/" + movie1.getId(), Movie.class);

		assertNotNull(existingMovie);
		assertEquals("Saaho", existingMovie.getMovieName());

	}

}
