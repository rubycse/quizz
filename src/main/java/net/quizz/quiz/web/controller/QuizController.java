package net.quizz.quiz.web.controller;

import net.quizz.auth.domain.User;
import net.quizz.common.service.AuthService;
import net.quizz.common.utils.DateUtils;
import net.quizz.quiz.domain.quiz.Option;
import net.quizz.quiz.domain.quiz.Question;
import net.quizz.quiz.domain.quiz.Quiz;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.repository.QuizDao;
import net.quizz.quiz.service.QuizAccessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 4/22/17
 */

@Controller
@RequestMapping(path = "/quiz")
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizAccessManager quizAccessManager;

    @RequestMapping(path = "/myQuizzes", method = RequestMethod.GET)
    public String myQuizzes(ModelMap model) {
        User user = authService.getUser();
        model.put("quizzes", quizDao.getQuizzes(user));
        return "quiz/myQuizzes";
    }

    @RequestMapping(path = "/run", method = RequestMethod.GET)
    public String run(@RequestParam int publicationId, ModelMap model) {
        Publication publication = quizDao.getPublication(publicationId);
        User user = authService.getUser();
        quizAccessManager.canAnswer(publication);
        Quiz quiz = quizDao.getQuiz(publication, user);

        if (quiz == null) {
            quiz = new Quiz(publication);
            quiz.setStartTime(new Date());
            quiz.setAnsweredBy(user);
            quizDao.save(quiz);
        }

        Question question = quiz.getQuestion(0);
        question.setStartTime(new Date());
        quizDao.save(question);

        setupReferenceData(model, quiz, question);

        return "quiz/run";
    }

    @RequestMapping(path = "/run", method = RequestMethod.POST)
    public String start(@RequestParam int quizId,
                        @RequestParam int questionId,
                        @RequestParam List<Option> answer,
                        RedirectAttributes redirectAttributes,
                        ModelMap model) {

        Quiz quiz = quizDao.getQuiz(quizId);
        if (questionId != quiz.getCurrentQuestion().getId()) {
            throw new IllegalAccessError("This question is not accessible, questionId=" + questionId
                    + ", currentQuestionId:" + quiz.getCurrentQuestion().getId());
        }

        Question question = quizDao.getQuestion(questionId);
        question.setEndTime(new Date());
        question.updateAnswer(answer);

        quizDao.save(question);


        int index = quiz.getIndex(question);
        if (quiz.getQuestions().size() > (index + 1)) {
            Question nextQuestion =  quiz.getQuestion(index + 1);
            nextQuestion.setStartTime(new Date());
            quizDao.save(nextQuestion);
            setupReferenceData(model, quiz, nextQuestion);
            return "quiz/run";
        }

        quiz.setEndTime(new Date());
        quizDao.save(quiz);
        redirectAttributes.addAttribute("quizId", quiz.getId());

        return "redirect:result";
    }

    @RequestMapping(path = "/result", method = RequestMethod.GET)
    public String result(@RequestParam int quizId, ModelMap model) {
        model.put("quiz", quizDao.getQuiz(quizId));
        model.put("datePattern", DateUtils.DATE_TIME_FORMAT_READABLE);

        return "quiz/result";
    }

    private void setupReferenceData(ModelMap model, Quiz quiz, Question question) {
        model.put("quiz", quiz);
        model.put("question", question);
        model.put("index", quiz.getIndex(question));
    }
}
