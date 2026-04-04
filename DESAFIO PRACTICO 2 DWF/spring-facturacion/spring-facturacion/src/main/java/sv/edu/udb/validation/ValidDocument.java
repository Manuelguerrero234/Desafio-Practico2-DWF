package sv.edu.udb.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentValidator.class)
@Documented

public @interface ValidDocument {
    String message() default "Invalid document format. Must be DUI (XXXXXXXX-X) or NIT (XXXX-XXXXXX-XXX-X)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}