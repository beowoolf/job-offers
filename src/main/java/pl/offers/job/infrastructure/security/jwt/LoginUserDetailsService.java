package pl.offers.job.infrastructure.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.offers.job.domain.loginandregister.LoginAndRegisterFacade;
import pl.offers.job.domain.loginandregister.dto.UserDto;

import java.util.Collections;

@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserDto userDto = loginAndRegisterFacade.findByUsername(username);
        return getUser(userDto);
    }

    private org.springframework.security.core.userdetails.User getUser(UserDto user) {
        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                Collections.emptyList());
    }

}
