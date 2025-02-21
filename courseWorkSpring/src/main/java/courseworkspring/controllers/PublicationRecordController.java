package courseworkspring.controllers;

import courseworkspring.errorHandling.PublicationNotFound;
import courseworkspring.errorHandling.PublicationRecordNotFound;
import courseworkspring.errorHandling.UserNotFound;
import courseworkspring.model.Client;
import courseworkspring.model.Publication;
import courseworkspring.model.PublicationRecord;
import courseworkspring.model.enums.PublicationStatus;
import courseworkspring.repos.ClientRepository;
import courseworkspring.repos.PublicationRecordRepository;
import courseworkspring.repos.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class PublicationRecordController {
    @Autowired
    PublicationRecordRepository publicationRecordRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping(value = "allPublicationRecords")
    @ResponseBody
    public List<PublicationRecord> getAllPublicationRecords() {
        return publicationRecordRepository.findAll();
    }

    @GetMapping(value = "publicationRecord/{id}")
    @ResponseBody
    public PublicationRecord getPublicationRecord(@PathVariable int id) {
        return publicationRecordRepository.findById(id)
                .orElseThrow(() -> new PublicationRecordNotFound(id));
    }

    @PostMapping(value = "createPublicationRecord")
    @ResponseBody
    public PublicationRecord createPublicationRecord(@RequestBody Map<String, Object> request) {
        int clientId = (int) request.get("client");
        int publicationId = (int) request.get("publication");
        String transactionDateStr = (String) request.get("transactionDate");
        String statusStr = (String) request.get("status");

        LocalDate transactionDate = LocalDate.parse(transactionDateStr);
        PublicationStatus publicationStatus = PublicationStatus.valueOf(statusStr);

        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFound(clientId));

        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFound(publicationId));

        PublicationRecord publicationRecord = new PublicationRecord(
                existingClient,
                existingPublication,
                transactionDate,
                publicationStatus
        );

        return publicationRecordRepository.save(publicationRecord);
    }

    @PatchMapping(value = "updatePublicationRecord/{id}")
    @ResponseBody
    public PublicationRecord updatePublicationRecord(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        PublicationRecord existingRecord = publicationRecordRepository.findById(id)
                .orElseThrow(() -> new PublicationRecordNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "client":
                    int clientId = (int) value;
                    Client client = clientRepository.findById(clientId)
                            .orElseThrow(() -> new UserNotFound(clientId));
                    existingRecord.setClient(client);
                    break;

                case "publication":
                    int publicationId = (int) value;
                    Publication publication = publicationRepository.findById(publicationId)
                            .orElseThrow(() -> new PublicationNotFound(publicationId));
                    existingRecord.setPublication(publication);
                    break;

                case "transactionDate":
                    LocalDate transactionDate = LocalDate.parse((String) value);
                    existingRecord.setTransactionDate(transactionDate);
                    break;

                case "status":
                    PublicationStatus status = PublicationStatus.valueOf((String) value);
                    existingRecord.setStatus(status);
                    break;
            }
        });

        return publicationRecordRepository.save(existingRecord);
    }

    @DeleteMapping(value = "deletePublicationRecord/{id}")
    @ResponseBody
    public String deletePublicationRecord(@PathVariable int id) {
        PublicationRecord pb = publicationRecordRepository.findById(id)
                        .orElseThrow(() -> new PublicationRecordNotFound(id));

        publicationRecordRepository.deleteById(id);
        return "Successfully deleted publication record with id: " + id;
    }
}
