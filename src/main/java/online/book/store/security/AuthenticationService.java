package online.book.store.security;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.user.UserLoginRequestDto;
import online.book.store.dto.user.UserLoginResponceDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponceDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password()));
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponceDto(token);
    }
}
