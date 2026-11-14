package halima.idouaksim.demoescqrsaxon.events;

import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;
}
