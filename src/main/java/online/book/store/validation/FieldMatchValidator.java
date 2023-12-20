package online.book.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.book.store.dto.UserRegistrationRequestDto;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {

    @Override
    public boolean isValid(
            UserRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return requestDto.getPassword().equals(requestDto.getRepeatPassword());
    }
}
