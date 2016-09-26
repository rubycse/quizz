package net.quizz.quiz.controller;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.Answer;
import net.quizz.quiz.domain.Question;
import net.quizz.quiz.domain.Quiz;
import net.quizz.quiz.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @author lutfun
 * @since 3/28/16
 */

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute Quiz quiz) {
        return "quiz/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String save(@ModelAttribute Quiz quiz, ModelMap model, HttpSession session) {
        User user = getUser(session);
        quiz.setCreatedBy(user);
        quizDao.save(quiz);
        model.put("id", quiz.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(@RequestParam int id, ModelMap model) {
        model.put("quiz", quizDao.getQuiz(id));
        return "quiz/show";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelMap model, HttpSession session) {
        User user = getUser(session);
        model.put("quizzes", quizDao.getUserQuizzes(user));
        return "quiz/list";
    }

    private User getUser(HttpSession session) {
        return (User) session.getAttribute("USER");
    }

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public Question addQuestion(@RequestParam int quizId, HttpSession session) throws IllegalAccessException {
        Quiz quiz = quizDao.getQuiz(quizId);
        if (quiz.getCreatedBy().getId() != getUser(session).getId()) {
            throw new IllegalAccessException();
        }

        Question question = addQuestion(quiz);
        quizDao.save(question);

        return question;
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    @ResponseBody
    public Answer addQuestion(@RequestParam int questionId) throws IllegalAccessException {
        Question question = quizDao.getQuestion(questionId);
        Answer answer = addAnswer(question);
        quizDao.save(answer);

        return answer;
    }

    @RequestMapping(path = "/updateQuizName", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateQuizName(@RequestParam int id, @RequestParam String value, HttpServletResponse response) {
        Quiz quiz = quizDao.getQuiz(id);
        quiz.setName(value);
        quizDao.save(quiz);
        return quiz.getName();
    }

    @RequestMapping(path = "/updateQuestionLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateQuestionLabel(@RequestParam int id, @RequestParam String value) {
        Question question = quizDao.getQuestion(id);
        question.setLabel(value);
        quizDao.save(question);
        return question.getLabel();
    }

    @RequestMapping(path = "/toggleRequired", method = RequestMethod.POST)
    @ResponseBody
    public String toggleRequired(@RequestParam int questionId) {
        Question question = quizDao.getQuestion(questionId);
        question.setRequired(!question.isRequired());
        quizDao.save(question);
        return "SUCCESS";
    }

    @RequestMapping(path = "/updateOptionLabel", method = RequestMethod.POST)
    @ResponseBody
    public String updateOptionLabel(@RequestParam int id, @RequestParam String value) {
        Answer answer = quizDao.getAnswer(id);
        answer.setLabel(value);
        quizDao.save(answer);
        return answer.getLabel();
    }

    @RequestMapping(path = "/deleteQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String deleteQuestion(@RequestParam int id) {
        quizDao.deleteQuestion(id);
        return "SUCCESS";
    }

    @RequestMapping(path = "/deleteOption", method = RequestMethod.POST)
    @ResponseBody
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
