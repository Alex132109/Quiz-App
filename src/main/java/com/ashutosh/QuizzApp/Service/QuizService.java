package com.ashutosh.QuizzApp.Service;

import com.ashutosh.QuizzApp.QuizzAppApplication;
import com.ashutosh.QuizzApp.dao.QuestionDao;
import com.ashutosh.QuizzApp.dao.QuizDao;
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
    QuestionDao questionDao;
    @Autowired
    QuizDao quizDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> questions = questionDao.findRandomQuestionByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);

            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Logs to console
            return new ResponseEntity<>("Failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateresult(int id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
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
