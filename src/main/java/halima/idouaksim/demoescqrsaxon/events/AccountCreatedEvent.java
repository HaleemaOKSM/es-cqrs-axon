package halima.idouaksim.demoescqrsaxon.events;

import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus accountStatus;
    private String currency;
}
