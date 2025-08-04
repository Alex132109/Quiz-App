package com.ashutosh.QuizzApp.dao;

import com.ashutosh.QuizzApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
