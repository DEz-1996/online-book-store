package online.book.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import online.book.store.dto.UserRegistrationRequestDto;

public class FieldMatchValidator implements
        ConstraintValidator<PasswordsFieldsMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(
            UserRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return Objects.equals(requestDto.getPassword(), requestDto.getRepeatPassword());
    }
}
