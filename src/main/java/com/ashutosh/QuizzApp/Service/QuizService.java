package com.ashutosh.QuizzApp.Service;

import com.ashutosh.QuizzApp.repository.QuestionRepo;
import com.ashutosh.QuizzApp.repository.QuizRepo;
import com.ashutosh.QuizzApp.model.Question;
import com.ashutosh.QuizzApp.model.QuestionWrapper;
import com.ashutosh.QuizzApp.model.Quiz;
import com.ashutosh.QuizzApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

    public int createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepo.findRandomQuestionByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        Quiz savedQuiz = quizRepo.save(quiz);

        return savedQuiz.getId();  // just return the quizId
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getRightAnswer());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateresult(int id, List<Response> responses) {

        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int i = 0;
        int right = 0;
        for (Response response: responses){
            if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;
                i++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);

    }
}
