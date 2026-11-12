package halima.idouaksim.demoescqrsaxon.events;

import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus accountStatus;
    private String currency;
}
