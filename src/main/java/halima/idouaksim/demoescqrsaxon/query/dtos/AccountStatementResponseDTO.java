package halima.idouaksim.demoescqrsaxon.query.dtos;

import halima.idouaksim.demoescqrsaxon.query.entities.Account;
import halima.idouaksim.demoescqrsaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account, List<AccountOperation> operations){}
