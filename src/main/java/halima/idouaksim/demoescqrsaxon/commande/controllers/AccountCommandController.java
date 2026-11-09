package halima.idouaksim.demoescqrsaxon.commande.controllers;


import halima.idouaksim.demoescqrsaxon.commande.dtos.AddNewAccountRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    @PostMapping("/add")
    public String addNewAccount(@RequestBody AddNewAccountRequestDTO request){

    }
}
