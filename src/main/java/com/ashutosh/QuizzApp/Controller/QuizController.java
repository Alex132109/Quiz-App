package com.ashutosh.QuizzApp.Controller;

import com.ashutosh.QuizzApp.Service.QuizService;
import com.ashutosh.QuizzApp.model.Question;
import com.ashutosh.QuizzApp.model.QuestionWrapper;
import com.ashutosh.QuizzApp.model.Response;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;
    Response response1;

    @PostMapping("/create")
    public ResponseEntity<Integer> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        int quizId = quizService.createQuiz(category, numQ, title);
        return ResponseEntity.ok(quizId);
    }
    @GetMapping("/get")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@RequestParam("id") int id){
        return quizService.getQuizQuestions(id);
    }
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateresult(id, responses);
    }
}
