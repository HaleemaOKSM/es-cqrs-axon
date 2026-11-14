package halima.idouaksim.demoescqrsaxon.query.entities;

import halima.idouaksim.demoescqrsaxon.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;
}
