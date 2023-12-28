package online.book.store.service;

import online.book.store.dto.user.UserRegistrationRequestDto;
import online.book.store.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto);
}
