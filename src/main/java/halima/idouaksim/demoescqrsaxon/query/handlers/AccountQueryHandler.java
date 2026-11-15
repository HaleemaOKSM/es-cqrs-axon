package halima.idouaksim.demoescqrsaxon.query.handlers;


import halima.idouaksim.demoescqrsaxon.query.dtos.AccountStatementResponseDTO;
import halima.idouaksim.demoescqrsaxon.query.entities.Account;
import halima.idouaksim.demoescqrsaxon.query.entities.AccountOperation;
import halima.idouaksim.demoescqrsaxon.query.queries.GetAccountStatementQuery;
import halima.idouaksim.demoescqrsaxon.query.queries.GetAllAccountsQuery;
import halima.idouaksim.demoescqrsaxon.query.queries.WatchEventQuery;
import halima.idouaksim.demoescqrsaxon.query.repository.AccountRepository;
import halima.idouaksim.demoescqrsaxon.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }

    @QueryHandler
    public AccountOperation on (WatchEventQuery query) {
        return AccountOperation.builder().build();
    }

}