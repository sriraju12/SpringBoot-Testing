package com.hcl.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.model.Movie;
import com.hcl.service.MovieService;

@WebMvcTest
public class RefactorMovieControllerTestCode {

	
	@MockBean
	private MovieService movieService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
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
	void shouldCreateMovie() throws Exception {

		when(movieService.save(any(Movie.class))).thenReturn(movie1);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/movies").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(movie1)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.movieName").value(movie1.getMovieName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.genere").value(movie1.getGenere()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value(movie1.getReleaseDate().toString()));
	}

	@Test
	void shouldGetAllMovies() throws Exception {

		List<Movie> list = new ArrayList<>();
		list.add(movie1);
		list.add(movie2);

		when(movieService.getAllMovies()).thenReturn(list);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
		        .andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(list.size()));

	}
	
	
	@Test
	void shouldGetOneMovie() throws Exception {
		
		when(movieService.getMovieById(anyLong())).thenReturn(movie1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/{id}",1L))
		    .andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath("$.movieName").value(movie1.getMovieName()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.genere").value(movie1.getGenere()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value(movie1.getReleaseDate().toString()));
	}
	
	
	@Test
	void shouldDeleteMovie() throws Exception {
		
		doNothing().when(movieService).deleteMovie(anyLong());
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/movies/{id}",1L))
		            .andExpect(status().isNoContent());
	}
	
	
	@Test
	void shouldUpdateMovie() throws Exception {
		
		when(movieService.updateMovie(any(Movie.class), anyLong())).thenReturn(movie1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/movies/{id}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movie1)))
		         .andExpect(status().isOk())
		         .andExpect(MockMvcResultMatchers.jsonPath("$.movieName").value(movie1.getMovieName()))
				 .andExpect(MockMvcResultMatchers.jsonPath("$.genere").value(movie1.getGenere()))
				 .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value(movie1.getReleaseDate().toString()));
		         
	}
	
	
}
