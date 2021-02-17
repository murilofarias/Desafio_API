package com.gmail.murilo145farias.DesafioAPI.restController;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.exception.IdNaoValidoServiceException;
import com.gmail.murilo145farias.DesafioAPI.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
        value = "/groups",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class GroupController {
    @Autowired
    private GroupService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") String id) {

        service.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group editarDataInicio(@PathVariable("id") String id, @RequestBody Group group) {

        return service.updateDataInicio(id, group.getCreatedAt());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group editar(@PathVariable("id") String id, @RequestBody Group group) {

        service.update(id, group);
        return group;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group getGroup(@PathVariable("id") String id) {

        return service.findById(id);
    }


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Group group) {
        service.save(group);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(group.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Group> listar() {
        return service.findAll();
    }
}
