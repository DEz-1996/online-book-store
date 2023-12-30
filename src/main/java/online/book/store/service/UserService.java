package online.book.store.service;

import online.book.store.dto.UserRegistrationRequestDto;
import online.book.store.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto);
}
