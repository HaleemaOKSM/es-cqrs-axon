package halima.idouaksim.demoescqrsaxon.query.handlers;

import halima.idouaksim.demoescqrsaxon.events.AccountCreatedEvent;
import halima.idouaksim.demoescqrsaxon.events.AccountCreditedEvent;
import halima.idouaksim.demoescqrsaxon.query.entities.Account;
import halima.idouaksim.demoescqrsaxon.query.repository.AccountRepository;
import halima.idouaksim.demoescqrsaxon.query.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("#######################33 Query side AccountCreatedEvent received");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getAccountStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);

    }
}
