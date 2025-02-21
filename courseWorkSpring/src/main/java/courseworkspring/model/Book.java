package courseworkspring.model;

import courseworkspring.model.enums.Format;
import courseworkspring.model.enums.Genre;
import courseworkspring.model.enums.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//You can generate getters and setters, here Lombok lib is used. They are generated for you
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book extends Publication {

    //Variables should be private and their values retrieved and changed using getters and setters respectively

    private String publisher;
    private String isbn;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private int pageCount;
    @Enumerated(EnumType.STRING)
    private Language language;
    private int publicationYear;
    @Enumerated(EnumType.STRING)
    private Format format; // Hardcover, Paperback, Digital, etc.
    private String summary;

    //Define constructors - how to create objects and assign values to attributes
    public Book(Client owner, String author, String title, String publisher, String isbn, Genre genre, int pageCount, Language language, int publicationYear, Format format, String summary) {
        super(owner, author, title);
        this.publisher = publisher;
        this.isbn = isbn;
        this.genre = genre;
        this.pageCount = pageCount;
        this.language = language;
        this.publicationYear = publicationYear;
        this.format = format;
        this.summary = summary;
    }


    @Override
    public String toString() {
        return title;
    }
}
