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
import java.util.Arrays;

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
    public String addQuestion(@RequestParam int quizId, ModelMap model, HttpSession session) throws IllegalAccessException {
        Quiz quiz = quizDao.getQuiz(quizId);
        if (quiz.getCreatedBy().getId() != getUser(session).getId()) {
            throw new IllegalAccessException();
        }

        Question question = addQuestion(quiz);
        quizDao.save(question);

        return getHtml(question);
    }

    @RequestMapping(path = "/addOption", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam int questionId) throws IllegalAccessException {
        Question question = quizDao.getQuestion(questionId);
        Answer answer = addAnswer(question);
        quizDao.save(answer);

        return getHtml(answer);
    }

    private String getHtml(Question question) {
        String questionHtml =
                "<div class=\"question\">\n" +
                        "   <div id=\"" + question.getId() + "\" class=\"questionLabel\" style=\"display: inline\">" + question.getLabel() + "</div>\n" +
                        "   <br/>\n" +
                        "   <div id=\"options-" + question.getId() + "\">\n";

        for (Answer answer : question.getAnswerOptions()) {
            questionHtml += getHtml(answer);
        }

        questionHtml +=
                "   </div>\n" +
                        "   <a class=\"addOption\" onclick=\"addOption(" + question.getId() + ");\">Add Option</a>\n" +
                        "</div>";

        return questionHtml;
    }

    private String getHtml(Answer answer) {
        return "<div class=\"option\">\n" +
                "   <input type=\"radio\"/>\n" +
                "   <span id=\"" + answer.getId() + "\" class=\"optionLabel\" style=\"display: inline\">" + answer.getLabel() + "</span>\n" +
                "</div>";

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
