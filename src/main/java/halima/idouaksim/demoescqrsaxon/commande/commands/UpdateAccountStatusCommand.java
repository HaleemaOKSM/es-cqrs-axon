package halima.idouaksim.demoescqrsaxon.commande.commands;

import halima.idouaksim.demoescqrsaxon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {

    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
