package malco.com.br.pixoptimizer.controllers;

import malco.com.br.pixoptimizer.models.Account;
import malco.com.br.pixoptimizer.models.dtos.AuthenticationDTO;
import malco.com.br.pixoptimizer.models.dtos.LoginSuccessResponseDTO;
import malco.com.br.pixoptimizer.repositories.AccountRepository;
import malco.com.br.pixoptimizer.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO credentials) {

        var userCredential = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var auth = this.authenticationManager.authenticate(userCredential);
        var token = tokenService.generateToken((Account) auth.getPrincipal());

        return ResponseEntity.ok(new LoginSuccessResponseDTO(token));
    }
}