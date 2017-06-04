package net.quizz.common.web;

import net.quizz.common.exception.InsufficientPrivilegeException;
import net.quizz.common.service.MailService;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.NotSerializableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lutfun
 * @since 6/4/17.
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Autowired
    private MailService mailService;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (isAjaxRequest(request)) {
            //TODO: Handle exception from ajax request
        }

        if (ex instanceof ObjectOptimisticLockingFailureException
                || ex.getCause() instanceof ObjectOptimisticLockingFailureException
                || ex instanceof OptimisticLockException
                || ex.getCause() instanceof OptimisticLockException
                || ex instanceof StaleObjectStateException
                || ex instanceof OptimisticLockingFailureException
                || ex.getCause() instanceof OptimisticLockingFailureException) {
            return returnWithError("errors.recordAlreadyModified");

        } else if (ex instanceof ObjectNotFoundException
                || ex.getCause() instanceof ObjectNotFoundException
                || ex instanceof ObjectRetrievalFailureException
                || ex.getCause() instanceof ObjectRetrievalFailureException
                || ex instanceof NoResultException
                || ex.getCause() instanceof NoResultException) {
            return returnWithError("errors.recordNotFound");

        } else if (ex instanceof HttpSessionRequiredException
                || ex.getCause() instanceof HttpSessionRequiredException) {
            return returnWithError("errors.loggedOutFromTab");

        } else if (!(ex instanceof NotSerializableException)
                && !(ex.getCause() instanceof NotSerializableException)
                && (ex instanceof IOException || ex.getCause() instanceof IOException)) {
            return returnWithError("errors.connectionClosed");

        } else if (ex instanceof DataIntegrityViolationException) {
            return returnWithError("errors.dataIntegrityViolation");
        } else if (ex instanceof InsufficientPrivilegeException) {
            return returnWithError(((InsufficientPrivilegeException) ex).getMessageKey());
        }

        if (ex instanceof ServletRequestBindingException || ex.getCause() instanceof ServletRequestBindingException) {
            ex = new ServletRequestBindingException(ex.getClass().getName());
        }

        log.error("An error occurred!, URL={}, error={}", getCurrentUrl(request), ex);

        return returnWithError("error.unknownError");
    }

    private String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

    private static ModelAndView returnWithError(String messageKey) {
        Map<String, Object> model = new HashMap<>();
        model.put("messageKey", messageKey);
        return new ModelAndView("/error", model);
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
