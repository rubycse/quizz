package net.quizz.quiz.controller;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Answer;
import net.quizz.quiz.domain.Question;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @author lutfun
 * @since 10/3/16
 */

@RestController
public class QuizRestController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    public Question addQuestion(@RequestParam int quizId, HttpSession session) throws IllegalAccessException {
        Quiz quiz = quizDao.getQuiz(quizId);
        if (quiz.getCreatedBy().getId() != authService.getUser(session).getId()) {
            throw new IllegalAccessException();
        }

        Question question = addQuestion(quiz);
        quizDao.save(question);

        return question;
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    public Answer addQuestion(@RequestParam int questionId) throws IllegalAccessException {
        Question question = quizDao.getQuestion(questionId);
        Answer answer = addAnswer(question);
        quizDao.save(answer);

        return answer;
    }

    @RequestMapping(path = "/updateQuizName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuizName(@RequestParam int id, @RequestParam String value, HttpServletResponse response) {
        Quiz quiz = quizDao.getQuiz(id);
        quiz.setName(value);
        quizDao.save(quiz);
        return quiz.getName();
    }

    @RequestMapping(path = "/updateQuestionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuestionLabel(@RequestParam int id, @RequestParam String value) {
        Question question = quizDao.getQuestion(id);
        question.setLabel(value);
        quizDao.save(question);
        return question.getLabel();
    }

    @RequestMapping(path = "/updateOptionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateOptionLabel(@RequestParam int id, @RequestParam String value) {
        Answer answer = quizDao.getAnswer(id);
        answer.setLabel(value);
        quizDao.save(answer);
        return answer.getLabel();
    }

    @RequestMapping(path = "/toggleRequired", method = RequestMethod.POST)
    public String toggleRequired(@RequestParam int questionId) {
        Question question = quizDao.getQuestion(questionId);
        question.setRequired(!question.isRequired());
        quizDao.save(question);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteQuestion", method = RequestMethod.POST)
    public String deleteQuestion(@RequestParam int id) {
        quizDao.deleteQuestion(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestParam int id) {
        quizDao.deleteAnswer(id);
        return "SUCCESS";
    }

    private Question addQuestion(Quiz quiz) {
        int questionSize = quiz.getQuestions().size();
        Question question = new Question("Question " + (questionSize + 1));
        question.setAnswerOptions(new ArrayList<>());
        addAnswer(question);
        addAnswer(question);
        question.setQuiz(quiz);
        quiz.getQuestions().add(question);
        return question;
    }

    private Answer addAnswer(Question question) {
        int answerSize = question.getAnswerOptions().size();
        Answer answer = new Answer("Answer " + (answerSize + 1));
        answer.setQuestion(question);
        question.getAnswerOptions().add(answer);
        return answer;
    }
}
