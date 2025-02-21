package courseworkspring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends User {
    private String address;
    //birthDate prideta demonstracijai kaip dirbt su LocalDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Comment> commentList;
    @JsonIgnore
    @Transient
    private List<Publication> ownedPublications;
    @JsonIgnore
    @Transient
    private List<Publication> boughtPublications = new ArrayList<>();

    public Client(String login, String password, String name, String surname, String address) {
        super(login, password, name, surname);
        this.address = address;
    }

    public Client(String login, String password, String name, String surname, String address, LocalDate birthDate) {
        super(login, password, name, surname);
        this.address = address;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
