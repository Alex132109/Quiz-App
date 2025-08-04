package com.ashutosh.QuizzApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "question") // ✅ This maps the entity to the 'question' table
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String rightAnswer;

    private String difficultyLevel;

    @Column(name = "category")
    private String category;
}
