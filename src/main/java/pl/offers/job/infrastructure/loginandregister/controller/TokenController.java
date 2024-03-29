package pl.offers.job.infrastructure.loginandregister.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.offers.job.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.offers.job.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import pl.offers.job.infrastructure.security.jwt.JwtAuthenticatorFacade;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }

}
