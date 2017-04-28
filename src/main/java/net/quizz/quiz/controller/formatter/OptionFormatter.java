package net.quizz.quiz.controller.formatter;

import net.quizz.common.utils.Utils;
import net.quizz.quiz.domain.quiz.Option;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author lutfun
 * @since 5/1/17
 */

@Component
public class OptionFormatter implements Formatter<Option> {

    @Override
    public Option parse(String text, Locale locale) throws ParseException {
        if (Utils.isEmpty(text)) {
            return null;
        }

        Option option = new Option();
        option.setId(Integer.parseInt(text));

        return option;
    }

    @Override
    public String print(Option option, Locale locale) {
        return String.valueOf(option.getId());
    }


}
