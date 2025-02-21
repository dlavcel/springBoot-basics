package courseworkspring.controllers;

import courseworkspring.errorHandling.PublicationNotFound;
import courseworkspring.errorHandling.UserNotFound;
import courseworkspring.model.*;
import courseworkspring.model.enums.*;
import courseworkspring.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class PublicationController {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private MangaRepository mangaRepository;
    @Autowired
    private PeriodicalRepository periodicalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "allPublications")
    @ResponseBody
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @GetMapping(value = "publication/{id}")
    @ResponseBody
    public Publication getPublication(@PathVariable int id) {
        Publication publication = publicationRepository.getReferenceById(id);

        if (publication instanceof Periodical) {
            return periodicalRepository.getReferenceById(id);
        } else if (publication instanceof Manga) {
            return mangaRepository.getReferenceById(id);
        } else if (publication instanceof Book) {
            return bookRepository.getReferenceById(id);
        }
        return publication;
    }

    @DeleteMapping(value = "deletePublication/{id}")
    @ResponseBody
    public void deletePublication(@PathVariable int id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));
        publicationRepository.delete(publication);
    }

    @PatchMapping(value = "/updatePublication/{id}")
    @ResponseBody
    public Publication updatePublication(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    publication.setTitle((String) value);
                    break;
                case "author":
                    publication.setAuthor((String) value);
                    break;
            }
        });

        return publicationRepository.save(publication);
    }


    //------------------------------MANGA-------------------------------------------
    @GetMapping(value = "allMangas")
    @ResponseBody
    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }

    @GetMapping(value = "manga/{id}")
    @ResponseBody
    public Manga getManga(@PathVariable int id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));
    }

    @PostMapping(value = "createManga")
    @ResponseBody
    public Publication createManga(@RequestBody Manga manga) {
        int ownerId = manga.getOwner().getId();
        Client owner = clientRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFound(ownerId));

        manga.setOwner(owner);
        //manga.setRequestDate(LocalDate.now());
        //manga.setPublicationStatus(PublicationStatus.AVAILABLE);

        //owner.getOwnedPublications().add(manga);
        return mangaRepository.save(manga);
    }

    @PatchMapping(value = "updateManga/{id}")
    @ResponseBody
    public Manga updateManga(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Manga existingManga = mangaRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    existingManga.setTitle((String) value);
                    break;
                case "author":
                    existingManga.setAuthor((String) value);
                    break;
                case "illustrator":
                    existingManga.setIllustrator((String) value);
                    break;
                case "originalLanguage":
                    existingManga.setOriginalLanguage(Language.valueOf((String) value));
                    break;
                case "volumeNumber":
                    existingManga.setVolumeNumber((Integer) value);
                    break;
                case "demographic":
                    existingManga.setDemographic(Demographic.valueOf((String) value));
                    break;
                case "isColor":
                    existingManga.setColor((Boolean) value);
                    break;
            }
        });

        return mangaRepository.save(existingManga);
    }

    @DeleteMapping(value = "deleteManga/{id}")
    @ResponseBody
    public String deleteManga(@PathVariable int id) {
        Manga existingManga = mangaRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        mangaRepository.delete(existingManga);
        return "Successfully deleted manga by id: " + id;
    }

    //---------------------PERIODICAL-----------------------
    @GetMapping(value = "allPeriodicals")
    @ResponseBody
    public List<Periodical> getAllPeriodicals() {
        return periodicalRepository.findAll();
    }

    @GetMapping(value = "periodical/{id}")
    @ResponseBody
    public Periodical getPeriodical(@PathVariable int id) {
        return periodicalRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));
    }

    @PostMapping(value = "createPeriodical")
    @ResponseBody
    public Periodical createPeriodical(@RequestBody Periodical periodical) {
        int ownerId = periodical.getOwner().getId();
        Client owner = clientRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFound(ownerId));

        periodical.setOwner(owner);
        //periodical.setRequestDate(LocalDate.now());
        //periodical.setPublicationStatus(PublicationStatus.AVAILABLE);

        return periodicalRepository.save(periodical);
    }

    @PatchMapping(value = "updatePeriodical/{id}")
    @ResponseBody
    public Periodical updatePeriodical(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Periodical existingPeriodical = periodicalRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    existingPeriodical.setTitle((String) value);
                    break;
                case "author":
                    existingPeriodical.setAuthor((String) value);
                    break;
                case "issueNumber":
                    existingPeriodical.setIssueNumber((Integer) value);
                    break;
                case "publicationDate":
                    if (value instanceof String) {
                        existingPeriodical.setPublicationDate(LocalDate.parse((String) value));
                    }
                    break;
                case "editor":
                    existingPeriodical.setEditor((String) value);
                    break;
                case "frequency":
                    existingPeriodical.setFrequency(Frequency.valueOf((String) value));
                    break;
                case "publisher":
                    existingPeriodical.setPublisher((String) value);
                    break;
            }
        });

        return periodicalRepository.save(existingPeriodical);
    }

    @DeleteMapping(value = "deletePeriodical/{id}")
    @ResponseBody
    public String deletePeriodical(@PathVariable int id) {
        Periodical existingPeriodical = periodicalRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        periodicalRepository.delete(existingPeriodical);
        return "Successfully deleted periodical by id: " + id;
    }

    //----------------------BOOK---------------------------
    @GetMapping(value = "allBooks")
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "book/{id}")
    @ResponseBody
    public Book getBook(@PathVariable int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));
    }

    @PostMapping(value = "createBook")
    @ResponseBody
    public Book createBook(@RequestBody Book book) {
        int ownerId = book.getOwner().getId();
        Client owner = clientRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFound(ownerId));

        book.setOwner(owner);
        //book.setRequestDate(LocalDate.now());
        //book.setPublicationStatus(PublicationStatus.AVAILABLE);

        return bookRepository.save(book);
    }

    @PatchMapping(value = "updateBook/{id}")
    @ResponseBody
    public Book updateBook(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    existingBook.setTitle((String) value);
                    break;
                case "author":
                    existingBook.setAuthor((String) value);
                    break;
                case "publisher":
                    existingBook.setPublisher((String) value);
                    break;
                case "isbn":
                    existingBook.setIsbn((String) value);
                    break;
                case "genre":
                    existingBook.setGenre(Genre.valueOf((String) value));
                    break;
                case "pageCount":
                    existingBook.setPageCount((Integer) value);
                    break;
                case "language":
                    existingBook.setLanguage(Language.valueOf((String) value));
                    break;
                case "format":
                    existingBook.setFormat(Format.valueOf((String) value));
                    break;
                case "summary":
                    existingBook.setSummary((String) value);
            }
        });

        return bookRepository.save(existingBook);
    }

    @DeleteMapping(value = "deleteBook/{id}")
    @ResponseBody
    public String deleteBook(@PathVariable int id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new PublicationNotFound(id));

        bookRepository.delete(existingBook);
        return "Successfully deleted book by id: " + id;
    }
}
