package courseworkspring.controllers;

import com.google.gson.Gson;
import courseworkspring.errorHandling.UserNotFound;
import courseworkspring.model.Admin;
import courseworkspring.model.Client;
import courseworkspring.model.User;
import courseworkspring.repos.AdminRepository;
import courseworkspring.repos.ClientRepository;
import courseworkspring.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping(value = "allUsers")
    public
    @ResponseBody List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //--CLIENT--
    @GetMapping(value = "allClients")
    @ResponseBody
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping(value = "client/{id}")
    EntityModel<User> one(@PathVariable Integer id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        return EntityModel.of(client, //
                linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("clients"));
    }

    @PostMapping(value = "createClient")
    @ResponseBody
    public void createUser(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @PatchMapping(value = "updateClient/{id}")
    @ResponseBody
    public Client updateClient(@PathVariable int id, @RequestBody Map<String, String> updates) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

           updates.forEach((key, value) -> {
            switch (key) {
                case "login":
                    existingClient.setLogin(value);
                    break;
                case "password":
                    existingClient.setPassword(value);
                    break;
                case "name":
                    existingClient.setName(value);
                    break;
                case "surname":
                    existingClient.setSurname(value);
                    break;
                case "address":
                    existingClient.setAddress(value);
                    break;
                case "birthDate":
                    existingClient.setBirthDate(LocalDate.parse(value));
                    break;
            }
        });

        clientRepository.save(existingClient);
        return existingClient;
    }

    @PostMapping(value = "validateClient")
    @ResponseBody
    public Client validateClient(@RequestBody String logInfo) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(logInfo, Properties.class);

        String login = properties.getProperty("login");
        String password = properties.getProperty("password");

        return clientRepository.getClientByLoginAndPassword(login, password);
    }

    @DeleteMapping(value = "deleteClient/{id}")
    @ResponseBody
    public String deleteClient(@PathVariable int id) {
        userRepository.deleteById(id);
        return "success";
    }

    //--ADMIN--
    @GetMapping(value = "allAdmins")
    @ResponseBody
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping(value = "admin/{id}")
    @ResponseBody
    public Admin getAdmin(@PathVariable int id) {
        Admin admin = adminRepository.findById(id).
                orElseThrow(() -> new UserNotFound(id));
        return admin;
    }

    @PostMapping(value = "createAdmin")
    @ResponseBody
    public void createAdmin(@RequestBody Admin admin) {
        adminRepository.save(admin);
    }

    @PatchMapping(value = "updateAdmin/{id}")
    @ResponseBody
    public Admin updateAdmin(@PathVariable int id, @RequestBody Map<String, String> updates) {
        Admin existingAdmin = adminRepository.findById(id).
                orElseThrow(() -> new UserNotFound(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "login":
                    existingAdmin.setLogin(value);
                    break;
                case "password":
                    existingAdmin.setPassword(value);
                    break;
                case "name":
                    existingAdmin.setName(value);
                    break;
                case "surname":
                    existingAdmin.setSurname(value);
                    break;
                case "phoneNum":
                    existingAdmin.setPhoneNum(value);
                    break;
            }
        });
        adminRepository.save(existingAdmin);
        return existingAdmin;
    }

    @DeleteMapping(value = "deleteAdmin/{id}")
    @ResponseBody
    public String deleteAdmin(@PathVariable int id) {
        adminRepository.deleteById(id);
        return "Successfully deleted admin by id: " + id;
    }
}
