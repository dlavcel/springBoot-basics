package courseworkspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String body;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> replies;
    @ManyToOne
    @JoinColumn(name = "parentCommentId")
    @JsonManagedReference
    private Comment parentComment;
    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    @JsonBackReference
    private Client client;
    @ManyToOne
    @JoinColumn(name = "chatId", nullable = false)
    @JsonBackReference
    private Chat chat;

    public Comment(String title, String body, LocalDateTime timestamp, Chat chat, Client client) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.chat = chat;
        this.client = client;
    }

    public Comment(String title, String body, LocalDateTime timestamp, Comment parentComment, Chat chat, Client client) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.parentComment = parentComment;
        this.chat = chat;
        this.client = client;
    }

    @Override
    public String toString() {
        return title;
    }
}

