package halima.idouaksim.demoescqrsaxon.commande.aggregate;

import halima.idouaksim.demoescqrsaxon.commande.commands.AddAccountCommand;
import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import halima.idouaksim.demoescqrsaxon.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    private String accountId;
    private double balance;
    private AccountStatus status;
    //Pour axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        //fonction de decision
        if (command.getInitialBalance()<=0) throw new IllegalArgumentException("Initial balance must be greater than zero");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }
}
