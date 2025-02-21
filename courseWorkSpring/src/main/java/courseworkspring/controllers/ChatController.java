package courseworkspring.controllers;

import courseworkspring.errorHandling.ChatNotFound;
import courseworkspring.errorHandling.PublicationNotFound;
import courseworkspring.model.Chat;
import courseworkspring.model.Publication;
import courseworkspring.repos.ChatRepository;
import courseworkspring.repos.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @PostMapping("createChat")
    public Chat createChat(@RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        int publicationId = (int) request.get("publicationId");

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFound(publicationId));

        Chat chat = new Chat(title, publication);
        return chatRepository.save(chat);
    }

    @GetMapping(value = "allChats")
    @ResponseBody
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @GetMapping(value = "chat/{id}")
    @ResponseBody
    public Chat getChat(@PathVariable int id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new ChatNotFound(id));
    }

    @PatchMapping(value = "updateChat/{id}")
    @ResponseBody
    public Chat updateChat(@PathVariable int id, @RequestBody Map<String, Object> request) {
        Chat existingChat = chatRepository.findById(id)
                .orElseThrow(() -> new ChatNotFound(id));
        if (request.get("title") != null) {
            existingChat.setTitle((String) request.get("title"));
        }
        return chatRepository.save(existingChat);
    }

    @DeleteMapping(value = "deleteChat/{id}")
    @ResponseBody
    public String deleteChat(@PathVariable int id) {
        Chat existingChat = chatRepository.findById(id)
                .orElseThrow(() -> new ChatNotFound(id));
        chatRepository.delete(existingChat);
        return "Successfully deleted chat with id " + id;
    }
}

