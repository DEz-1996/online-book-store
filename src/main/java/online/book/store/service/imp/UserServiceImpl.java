package online.book.store.service.imp;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.UserRegistrationRequestDto;
import online.book.store.dto.UserResponseDto;
import online.book.store.exception.exceptions.RegistrationException;
import online.book.store.mapper.UserMapper;
import online.book.store.model.User;
import online.book.store.repository.UserRepository;
import online.book.store.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String REGISTRATION_ERROR_MSG = "Can't register user";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto) {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException(REGISTRATION_ERROR_MSG);
        }
        User user = userMapper.toUser(registrationRequestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
