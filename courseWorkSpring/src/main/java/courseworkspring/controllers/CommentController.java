package courseworkspring.controllers;

import courseworkspring.errorHandling.ChatNotFound;
import courseworkspring.errorHandling.UserNotFound;
import courseworkspring.model.Chat;
import courseworkspring.model.Client;
import courseworkspring.model.Comment;
import courseworkspring.repos.ChatRepository;
import courseworkspring.repos.ClientRepository;
import courseworkspring.repos.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ChatRepository chatRepository;

    @PostMapping(value = "createComment")
    @ResponseBody
    public Comment createComment(@RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String body = (String) request.get("body");


        int chatId = (int) request.get("chat");
        int clientId = (int) request.get("client");

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFound(chatId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFound(clientId));

        Comment comment = new Comment(title, body, LocalDateTime.now(), chat, client);
        return commentRepository.save(comment);
    }

    @PostMapping(value = "comments/reply/{parentCommentId}")
    @ResponseBody
    public Comment replyComment(@PathVariable int parentCommentId, @RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String body = (String) request.get("body");

        int clientId = (int) request.get("client");

        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found: " + parentCommentId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFound(clientId));

        Comment comment = new Comment(
                title,
                body,
                LocalDateTime.now(),
                parentComment,
                parentComment.getChat(),
                client);

        commentRepository.save(comment);
        return comment;
    }

    @GetMapping(value = "allComments")
    @ResponseBody
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @GetMapping(value = "comment/{id}")
    @ResponseBody
    public Comment getComment(@PathVariable int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }

    @PatchMapping(value = "updateComment/{id}")
    @ResponseBody
    public Comment updateComment(@PathVariable int id, @RequestBody Map<String, Object> request) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));

        request.forEach((key, value) -> {
            switch (key) {
                case "title":
                    existingComment.setTitle((String) value);
                    break;
                case "body":
                        existingComment.setBody((String) value);
                        break;
            }
        });
        return commentRepository.save(existingComment);
    }

    @DeleteMapping(value = "deleteComment/{id}")
    @ResponseBody
    public String deleteComment(@PathVariable int id) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
        commentRepository.delete(existingComment);

        return "Successfully deleted comment: " + id;
    }
}
