package halima.idouaksim.demoescqrsaxon.commande.aggregate;

import halima.idouaksim.demoescqrsaxon.commande.commands.AddAccountCommand;
import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import halima.idouaksim.demoescqrsaxon.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    private String accountId;

    private double balance;
    private AccountStatus status;

    //Pour axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        //fonction de decision
        log.info("############3 AddAccountCommand received");
        if (command.getInitialBalance()<=0){
            throw new IllegalArgumentException(
                    "Initial balance must be greater than zero"
            );
        }
        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("############3 AccountCreatedEvent occured");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getAccountStatus();
    }
}
