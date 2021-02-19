package com.gmail.murilo145farias.DesafioAPI.restController;

import com.gmail.murilo145farias.DesafioAPI.domain.User;
import com.gmail.murilo145farias.DesafioAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "/groups/{idGroup}/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    @Autowired
    private UserService service;

    @DeleteMapping("/{idUser}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("idGroup") String idGroup, @PathVariable("idUser") String idUser) {

        service.delete(idUser, idGroup);
    }

    @PutMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public User putUser(@PathVariable("idGroup") String idGroup,
                       @PathVariable("idUser") String idUser, @RequestBody User user) {

        service.update(idUser, idGroup, user);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postUser(@PathVariable("idGroup") String idGroup, @RequestBody User user) {

        service.save(idGroup, user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(@PathVariable("idGroup") String idGroup,
                             @RequestParam(name = "showGroupField", required = false, defaultValue = "false")
                                     String stringShowGroupField,
                             @RequestParam(name = "searchName", required = false, defaultValue = "") String name,
                             @RequestParam(name ="exactMatch", required = false, defaultValue = "false")
                                              String stringExactMatch) {

        boolean showGroupField = stringShowGroupField.equals("true") ? true : false;
        boolean exactMatch = stringExactMatch.equals("true") ? true : false;

        return service.findAllByGroup(idGroup, showGroupField, name, exactMatch);
    }

    @GetMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("idGroup") String idGroup,
                                  @PathVariable("idUser") String idUser) {

        return service.findByIdUserAndIdGroup(idUser, idGroup);
    }
}
