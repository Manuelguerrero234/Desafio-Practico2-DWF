package sv.edu.udb.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DocumentValidator implements ConstraintValidator<ValidDocument, String> {

    private static final Pattern DUI_PATTERN = Pattern.compile("^[0-9]{8}-[0-9]{1}$");
    private static final Pattern NIT_PATTERN = Pattern.compile("^[0-9]{4}-[0-9]{6}-[0-9]{3}-[0-9]{1}$");

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {
        if (document == null || document.isBlank()) return false;
        return DUI_PATTERN.matcher(document).matches() || NIT_PATTERN.matcher(document).matches();
    }
}