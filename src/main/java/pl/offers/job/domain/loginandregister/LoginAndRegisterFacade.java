package pl.offers.job.domain.loginandregister;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pl.offers.job.domain.loginandregister.dto.RegisterUserDto;
import pl.offers.job.domain.loginandregister.dto.RegistrationResultDto;
import pl.offers.job.domain.loginandregister.dto.UserDto;

@Component
@RequiredArgsConstructor
public class LoginAndRegisterFacade {

    private static final String USER_NOT_FOUND = "User not found";

    private final LoginRepository repository;

    public UserDto findByUsername(String username) {
        return repository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.password(), user.username()))
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));
    }

    public RegistrationResultDto register(RegisterUserDto registerUserDto) {
        final User user = User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
        User savedUser = repository.save(user);
        return new RegistrationResultDto(savedUser.id(), true, savedUser.username());
    }

}
