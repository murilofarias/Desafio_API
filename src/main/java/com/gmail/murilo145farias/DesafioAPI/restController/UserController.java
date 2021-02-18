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
    public void excluir(@PathVariable("idGroup") String idGroup, @PathVariable("idUser") String idUser) {

        service.delete(idUser, idGroup);
    }

    @PutMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public User editar(@PathVariable("idGroup") String idGroup,
                       @PathVariable("idUser") String idUser, @RequestBody User user) {

        service.update(idUser, idGroup, user);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> salvar(@PathVariable("idGroup") String idGroup, @RequestBody User user) {

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
    public List<User> listar(@PathVariable("idGroup") String idGroup,
                             @RequestParam(name = "fields", required = false, defaultValue = "") String fields) {

        return service.findAllByGroup(idGroup, fields);
    }

    @GetMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("idGroup") String idGroup,
                                  @PathVariable("idUser") String idUser) {

        return service.findByIdUserAndIdGroup(idUser, idGroup);
    }
}
