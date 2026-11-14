package halima.idouaksim.demoescqrsaxon.commande.dtos;

import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;

public record UpdateAccountStatusDTO(String accountId, AccountStatus status) {
}
