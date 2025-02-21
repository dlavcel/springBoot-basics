package courseworkspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import courseworkspring.model.enums.PublicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Publication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne
    @JsonIgnoreProperties("ownedPublications")
    @JoinColumn(name = "ownerId")
    protected Client owner;

    protected String title;
    protected String author;

//    @ManyToOne
//    @JoinColumn(name = "clientId")
//    protected Client client;

//    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
//    protected List<PublicationRecord> records;

    //@Enumerated(EnumType.STRING)
    //protected PublicationStatus publicationStatus;

    //protected LocalDate requestDate;

    public Publication(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Publication(Client owner, String title, String author) {
        this.owner = owner;
        this.title = title;
        this.author = author;
    }
}
