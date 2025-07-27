package malco.com.br.pixoptimizer.repositories;

import malco.com.br.pixoptimizer.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountRepository extends JpaRepository<Account, Long> {
    UserDetails findByEmail(String email);
}
