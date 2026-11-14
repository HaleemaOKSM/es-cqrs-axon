package halima.idouaksim.demoescqrsaxon.query.repository;

import halima.idouaksim.demoescqrsaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
