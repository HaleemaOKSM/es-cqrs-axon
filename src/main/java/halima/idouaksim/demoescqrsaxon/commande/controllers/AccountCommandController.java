package halima.idouaksim.demoescqrsaxon.commande.controllers;


import halima.idouaksim.demoescqrsaxon.commande.commands.AddAccountCommand;
import halima.idouaksim.demoescqrsaxon.commande.commands.CreditAccountCommand;
import halima.idouaksim.demoescqrsaxon.commande.commands.DebitAccountCommand;
import halima.idouaksim.demoescqrsaxon.commande.dtos.AddNewAccountRequestDTO;
import halima.idouaksim.demoescqrsaxon.commande.dtos.CreditAccountRequestDTO;
import halima.idouaksim.demoescqrsaxon.commande.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request){
            CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                    UUID.randomUUID().toString(),
                    request.initialBalance(),
                    request.currency()
            ));
        return response;
    }
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
