package online.book.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.book.store.dto.UserRegistrationRequestDto;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationRequestDto requestDto = (UserRegistrationRequestDto) object;
        return requestDto.getPassword().equals(requestDto.getRepeatPassword());
    }
}
