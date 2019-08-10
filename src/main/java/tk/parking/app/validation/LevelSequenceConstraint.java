package tk.parking.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LevelSequenceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LevelSequenceConstraint {
    String message() default "Invalid sequence of the level numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
