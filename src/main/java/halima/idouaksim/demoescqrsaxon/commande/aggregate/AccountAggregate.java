package halima.idouaksim.demoescqrsaxon.commande.aggregate;

import halima.idouaksim.demoescqrsaxon.commande.commands.AddAccountCommand;
import halima.idouaksim.demoescqrsaxon.commande.commands.CreditAccountCommand;
import halima.idouaksim.demoescqrsaxon.commande.commands.DebitAccountCommand;
import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import halima.idouaksim.demoescqrsaxon.events.AccountActivatedEvent;
import halima.idouaksim.demoescqrsaxon.events.AccountCreatedEvent;
import halima.idouaksim.demoescqrsaxon.events.AccountCreditedEvent;
import halima.idouaksim.demoescqrsaxon.events.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
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
        AggregateLifecycle.apply(
                new AccountActivatedEvent(
                        command.getId(),
                        AccountStatus.ACTIVATED
                ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("############3 AccountCreatedEvent occured");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getAccountStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("############3 AccountActivatedEvent occured");
        this.accountId = event.getAccountId();
        this.status = event.getAccountStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        //fonction de decision
        log.info("############3 CreditAccountCommand received");
        if (!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("Status is not ACTIVATED for the account : "+ command.getId());
        if (command.getAmount()<=0){
            throw new IllegalArgumentException(
                    "amount must be greater than zero"
            );
        }
        AggregateLifecycle.apply(
                new AccountCreditedEvent(
                        command.getId(),
                        command.getAmount(),
                        command.getCurrency()
                ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("############3 AccountCreditedEvent occured");
        this.accountId = event.getAccountId();
        this.balance = this.balance + event.getAmount();
    }

    //debit
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        //fonction de decision
        log.info("############3 DebitAccountCommand received");
        if (!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("Status is not ACTIVATED for the account : "+ command.getId());
        if (balance<command.getAmount()) throw new RuntimeException("Balance must be greater than zero");
        if (command.getAmount()<=0) throw new IllegalArgumentException("amount must be greater than zero");

        AggregateLifecycle.apply(
                new AccountDebitedEvent(
                        command.getId(),
                        command.getAmount(),
                        command.getCurrency()
                ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        log.info("############3 AccountDebitedEvent occured");
        this.accountId = event.getAccountId();
        this.balance = this.balance - event.getAmount();
    }
}
