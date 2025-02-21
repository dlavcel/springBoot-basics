package courseworkspring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import courseworkspring.model.enums.Frequency;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Periodical extends Publication {

    private int issueNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    private String editor;
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    private String publisher;
//
//    public Periodical(Client owner, String author, String title, int issueNumber, LocalDate publicationDate, String editor, Frequency frequency, String publisher) {
//        super(owner, author, title);
//        this.issueNumber = issueNumber;
//        this.publicationDate = publicationDate;
//        this.editor = editor;
//        this.frequency = frequency;
//        this.publisher = publisher;
//    }

    public Periodical() {

    }

    @Override
    public String toString() {
        return title;
    }
}
