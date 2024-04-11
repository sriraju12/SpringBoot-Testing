package com.hcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
