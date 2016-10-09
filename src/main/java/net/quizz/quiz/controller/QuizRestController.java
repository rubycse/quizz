package net.quizz.quiz.controller;

import net.quizz.common.service.AuthService;
import net.quizz.quiz.domain.Answer;
import net.quizz.quiz.domain.Question;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private QuizAccessManager quizAccessManager;

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    public Question addQuestion(@RequestParam int quizId) {
        Quiz quiz = quizDao.getQuiz(quizId);
        quizAccessManager.canEdit(quiz);
        Question question = addQuestion(quiz);
        quizDao.save(question);

        return question;
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    public Answer addOption(@RequestParam int questionId) {
        Question question = quizDao.getQuestion(questionId);
        quizAccessManager.canEdit(question.getQuiz());

        Answer answer = addAnswer(question);
        quizDao.save(answer);

        return answer;
    }

    @RequestMapping(path = "/updateQuizName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuizName(@RequestParam int id, @RequestParam String value) {
        Quiz quiz = quizDao.getQuiz(id);
        quizAccessManager.canEdit(quiz);

        quiz.setName(value);
        quizDao.save(quiz);
        return quiz.getName();
    }

    @RequestMapping(path = "/updateQuizDuration", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuizDuration(@RequestParam int id, @RequestParam String value) {
        Quiz quiz = quizDao.getQuiz(id);
        quizAccessManager.canEdit(quiz);

        quiz.setMaxDurationInMin(Integer.parseInt(value));
        quizDao.save(quiz);
        return quiz.getMaxDurationInMin().toString();
    }

    @RequestMapping(path = "/updateQuestionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateQuestionLabel(@RequestParam int id, @RequestParam String value) {
        Question question = quizDao.getQuestion(id);
        quizAccessManager.canEdit(question.getQuiz());
        question.setLabel(value);
        quizDao.save(question);
        return question.getLabel();
    }

    @RequestMapping(path = "/updateOptionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String updateOptionLabel(@RequestParam int id, @RequestParam String value) {
        Answer answer = quizDao.getAnswer(id);
        quizAccessManager.canEdit(answer.getQuestion().getQuiz());
        answer.setLabel(value);
        quizDao.save(answer);
        return answer.getLabel();
    }

    @RequestMapping(path = "/toggleRequired", method = RequestMethod.POST)
    public String toggleRequired(@RequestParam int questionId) {
        Question question = quizDao.getQuestion(questionId);
        quizAccessManager.canEdit(question.getQuiz());
        question.setRequired(!question.isRequired());
        quizDao.save(question);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteQuestion", method = RequestMethod.POST)
    public String deleteQuestion(@RequestParam int id) {
        Question question = quizDao.getQuestion(id);
        quizAccessManager.canEdit(question.getQuiz());
        quizDao.deleteQuestion(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteOption", method = RequestMethod.POST)
    public String deleteOption(@RequestParam int id) {
        quizAccessManager.canEdit(quizDao.getAnswer(id).getQuestion().getQuiz());
        quizDao.deleteAnswer(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/updateAnswer", method = RequestMethod.POST)
    public String updateAnswer(@RequestParam int id) {
        Answer answer = quizDao.getAnswer(id);
        quizAccessManager.canEdit(answer.getQuestion().getQuiz());
        Question question = answer.getQuestion();
        question.clearAnswer();
        answer.setRightAnswer(true);
        quizDao.save(question);
        return "SUCCESS";
    }

    private Question addQuestion(Quiz quiz) {
        int questionSize = quiz.getQuestions().size();
        Question question = new Question("Question " + (questionSize + 1));
        question.setAnswerOptions(new ArrayList<Answer>());
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
