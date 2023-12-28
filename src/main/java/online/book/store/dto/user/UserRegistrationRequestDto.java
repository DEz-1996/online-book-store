package online.book.store.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import online.book.store.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch
public class UserRegistrationRequestDto {
    private static final int MIN_PASS_SIZE = 8;
    private static final int MAX_PASS_SIZE = 20;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = MIN_PASS_SIZE, max = MAX_PASS_SIZE)
    private String password;
    @NotBlank
    @Length(min = MIN_PASS_SIZE, max = MAX_PASS_SIZE)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String shippingAddress;
}
