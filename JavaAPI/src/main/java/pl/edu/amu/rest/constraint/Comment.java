package pl.edu.amu.rest.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by Altenfrost on 2016-01-05.
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Comment.Validator.class)
public @interface Comment {
    String message() default "{pl.edu.amu.rest.constraint.Comment.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Comment, pl.edu.amu.rest.model.Comment> {
        @Context
        private Request request;

        @Override
        public void initialize(Comment comment) {

        }

        @Override
        public boolean isValid(pl.edu.amu.rest.model.Comment comment, ConstraintValidatorContext constraintValidatorContext) {
            if (request.getMethod().equals("PUT")) {

                if (comment.getCommentText() == null || !Pattern.matches("^(?=\\s*\\S).*$", comment.getCommentText()) || comment.isPositive() == null) {
                    return false;
                } else {
                    return true;
                }

            } else if (request.getMethod().equals("POST")) {
                try {
                    if (Long.valueOf(comment.getGiverId()).equals(Long.valueOf(comment.getRecieverId()))) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            return true;
        }

    }
}
