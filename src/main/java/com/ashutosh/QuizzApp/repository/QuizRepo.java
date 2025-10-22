package com.ashutosh.QuizzApp.repository;

import com.ashutosh.QuizzApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizRepo extends JpaRepository<Quiz, Integer> {

}
