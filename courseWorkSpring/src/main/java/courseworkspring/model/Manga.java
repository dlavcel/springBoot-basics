package courseworkspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import courseworkspring.model.enums.Demographic;
import courseworkspring.model.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manga extends Publication {

    private String illustrator;
    @Enumerated(EnumType.STRING)
    private Language originalLanguage;
    private int volumeNumber;
    @Enumerated(EnumType.STRING)
    private Demographic demographic;
    @JsonProperty("isColor")
    private boolean isColor;

    public Manga(Client owner, String title, String author, String illustrator, Language originalLanguage, int volumeNumber, Demographic demographic, boolean isColor) {
        super(owner, title, author);
        this.illustrator = illustrator;
        this.originalLanguage = originalLanguage;
        this.volumeNumber = volumeNumber;
        this.demographic = demographic;
        this.isColor = isColor;
    }

    @Override
    public String toString() {
        return title;
    }
}
