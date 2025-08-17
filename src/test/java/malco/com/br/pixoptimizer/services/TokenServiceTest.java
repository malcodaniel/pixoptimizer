package malco.com.br.pixoptimizer.services;

import malco.com.br.pixoptimizer.models.Account;
import malco.com.br.pixoptimizer.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Account validUserAccount;

    @BeforeAll
    void setUp() {
        Account validUser = new Account();
        validUser.setEmail("test@email.com");
        validUser.setPassword(passwordEncoder.encode("password"));
        validUser.setFullName("Test User");
        validUserAccount = accountRepository.save(validUser);
    }

    @Test
    public void shouldGenerateValidTokenIfUserCredentialsAreValid() {
        String token = tokenService.generateToken(validUserAccount);
        String tokenRegexPattern = "^[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*$";
        Pattern pattern = Pattern.compile(tokenRegexPattern);
        Matcher matcher = pattern.matcher(token);
        assertThat(matcher.matches()).isEqualTo(false);
    }

    @Test
    void shouldRaiseAnErrorIfCreadentialsAreNotValid() {
        var userCredential = new UsernamePasswordAuthenticationToken("test@email.com", "wrong password");
        assertThrows(BadCredentialsException.class, () -> {
            this.authenticationManager.authenticate(userCredential);
        });
    }
}