package courseworkspring.model;

import courseworkspring.model.enums.PublicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PublicationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "ownerId")
//    private Client owner;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "publicationId")
    private Publication publication;
    private LocalDate transactionDate;
    @Enumerated(EnumType.STRING)
    private PublicationStatus status;

    public PublicationRecord(Client client, Publication publication, LocalDate transactionDate, PublicationStatus status) {
        this.client = client;
        this.publication = publication;
        this.transactionDate = transactionDate;
        this.status = status;
    }
}
