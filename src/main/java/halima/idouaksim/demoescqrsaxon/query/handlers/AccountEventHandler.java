package halima.idouaksim.demoescqrsaxon.query.handlers;

import halima.idouaksim.demoescqrsaxon.enums.OperationType;
import halima.idouaksim.demoescqrsaxon.events.*;
import halima.idouaksim.demoescqrsaxon.query.entities.Account;
import halima.idouaksim.demoescqrsaxon.query.entities.AccountOperation;
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
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);

    }

    @EventHandler
    public void on (AccountActivatedEvent event) {
        log.info("#######################33 Query side AccountActivatedEvent received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on (AccountStatusUpdatedEvent event) {
        log.info("#######################33 Query side AccountStatusUpdatedEvent received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on (AccountDebitedEvent event, EventMessage eventMessage) {
        log.info("#######################33 Query side AccountDebitedEvent received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on (AccountCreditedEvent event, EventMessage eventMessage){
        log.info("#######################33 Query side AccountCreditedEvent received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance()+accountOperation.getAmount());
        accountRepository.save(account);
    }

}
