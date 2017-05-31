
package net.quizz.quiz.web.formatter;

import net.quizz.common.utils.Utils;
import net.quizz.quiz.domain.quiz.Option;
import net.quizz.quiz.domain.template.StudentGroup;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author lutfun
 * @since 5/1/17
 */

@Component
public class StudentGroupFormatter implements Formatter<StudentGroup> {

    @Override
    public StudentGroup parse(String text, Locale locale) throws ParseException {
        if (Utils.isEmpty(text)) {
            return null;
        }

        StudentGroup group = new StudentGroup();
        group.setId(Integer.parseInt(text));

        return group;
    }

    @Override
    public String print(StudentGroup studentGroup, Locale locale) {
        return String.valueOf(studentGroup.getId());
    }
}
