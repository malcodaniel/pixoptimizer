package malco.com.br.pixoptimizer.controllers;

import malco.com.br.pixoptimizer.models.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Profile("local")
public class TestController {
    @GetMapping("principal")
    public String test(@AuthenticationPrincipal Account user) {
        return user.toString();
    }
}
