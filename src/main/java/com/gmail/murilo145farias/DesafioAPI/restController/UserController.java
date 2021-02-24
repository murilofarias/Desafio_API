package com.gmail.murilo145farias.DesafioAPI.restController;

import com.gmail.murilo145farias.DesafioAPI.domain.DetalheErro;
import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.domain.User;
import com.gmail.murilo145farias.DesafioAPI.service.UserService;
import io.swagger.annotations.*;
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
        value = "/groups/{idGroup}/users")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value= "Deleta uma instância de User pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 204,
                    message = "Subrecurso Deletado com sucesso"),

            @ApiResponse(
                    code = 404,
                    message = "Subrecurso não encontrado",
                    response= DetalheErro.class)})
    @DeleteMapping("/{idUser}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value="Id do Group que contém o User a ser deletado", required = true)
                               @PathVariable("idGroup") String idGroup,
                           @ApiParam(value="Id do User a ser deletado")
                            @PathVariable("idUser") String idUser) {

        service.delete(idUser, idGroup);
    }

    @ApiOperation(value= "Atualiza a instância de User do id informado",
            notes="Os únicos campos de User a serem atualizados são name e phone. Todos os outros são ignorados." +
                  "O corpo da Resposta é a instância de User resultante da operação")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Subrecurso Atualizado com sucesso! O corpo da resposta é o recurso atualizado",
                    response = User.class),

            @ApiResponse(
                    code = 404,
                    message = "Recurso não encontrado",
                    response = DetalheErro.class)})
    @PutMapping(path = "/{idUser}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User putUser(@ApiParam(value="Id do Group que contém o User a ser atualizado", required = true)
                            @PathVariable("idGroup") String idGroup,
                       @ApiParam(value="Id do Group que contém o User a ser atualizado", required = true)
                            @PathVariable("idUser") String idUser,
                       @RequestBody User user) {

        service.update(idUser, idGroup, user);
        return user;
    }

    @ApiOperation(value= "Cria uma nova instância de User.",
            notes="O campo name é obrigatório e phone opcional. Um valor submetido no campo id lançará uma exceção" +
                  " correspondente. O createdAt é preenchido automaticamente")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Subrecurso criado com sucesso! O campo location do header tem a url do novo subrecurso",
                    responseHeaders = @ResponseHeader(name = "location", response = URI.class,
                            description = "URI do novo subrecurso User criado")),
            @ApiResponse(
                    code = 400,
                    message = "Subrecurso não pode ser criado corretamente pelo  corpo da requisição dada",
                    response = DetalheErro.class)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postUser(@ApiParam(value="Id do Group que conterá o User a ser criado", required = true)
                                             @PathVariable("idGroup") String idGroup,
                                         @RequestBody User user) {

        service.save(idGroup, user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value= "Retorna lista de Users dentro de um grupo.",
            notes="Sem os parâmetros da query string, essa operação retorna todas as instâncias de Users no Group. " +
                  "Caso um filtro de name seja dado, ele retorna todas as instâncias que satisfazem a condição do " +
                  "filtro em um Group.")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Subrecursos encontrados e encaminhados com sucesso!",
                    response = User.class,
                    responseContainer = "List"),

            @ApiResponse(
                    code = 404,
                    message = "Recursos não encontrados.",
                    response = DetalheErro.class)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(@ApiParam(value="Id do Grupo de onde será procurado os Users", required = true)
                                      @PathVariable("idGroup") String idGroup,
                             @ApiParam(value="Determina se o campo Group é exibido (true) ou não (qualquer outro valor" +
                                     " será false)")
                                @RequestParam(name = "showGroupField", required = false, defaultValue = "false")
                                     String stringShowGroupField,
                             @ApiParam(value="Termo filtro para o campo name")
                                @RequestParam(name = "searchName", required = false, defaultValue = "") String name,
                             @ApiParam(value="Determina se os Groups devem ser exibidos com os seus Users (true) ou não"
                                      + " (qualquer outro valor será false)")
                                @RequestParam(name ="exactMatch", required = false, defaultValue = "false")
                                              String stringExactMatch) {

        boolean showGroupField = stringShowGroupField.equals("true") ? true : false;
        boolean exactMatch = stringExactMatch.equals("true") ? true : false;

        return service.findAllByGroup(idGroup, showGroupField, name, exactMatch);
    }


    @ApiOperation(value= "Retorna a instância de User pelo id dentro do Group informado pelo id")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Subrecurso encontrado e encaminhado com sucesso!",
                    response = User.class),

            @ApiResponse(
                    code = 404,
                    message = "Subrecurso não encontrado.",
                    response = DetalheErro.class)})
    @GetMapping(path= "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@ApiParam(value="Id do Group que contém o User", required = true)
                                 @PathVariable("idGroup") String idGroup,
                            @ApiParam(value="Id do User a ser retornado", required = true)
                                @PathVariable("idUser") String idUser) {

        return service.findByIdUserAndIdGroup(idUser, idGroup);
    }
}
