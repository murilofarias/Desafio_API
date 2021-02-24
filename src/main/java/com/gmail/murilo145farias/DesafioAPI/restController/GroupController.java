package com.gmail.murilo145farias.DesafioAPI.restController;

import com.gmail.murilo145farias.DesafioAPI.domain.DetalheErro;
import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.domain.User;
import com.gmail.murilo145farias.DesafioAPI.exception.IdNaoValidoServiceException;
import com.gmail.murilo145farias.DesafioAPI.service.GroupService;
import io.swagger.annotations.*;
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
        value = "/groups"
)
public class GroupController {
    @Autowired
    private GroupService service;

    @ApiOperation(value= "Deleta uma instância de Group pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 204,
                    message = "Recurso Deletado com sucesso"),

            @ApiResponse(
                    code = 404,
                    message = "Recurso não encontrado",
                    response= DetalheErro.class)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@ApiParam(value="Id do Group a ser deletado", required = true)
                                @PathVariable("id") String id) {

        service.delete(id);
    }

    @ApiOperation(value= "Atualiza a instância do id informado",
            notes="O único campo de Group a ser atualizado é name. Todos os outros são ignorados." +
                  "O corpo da Resposta é a instância de Group resultante da operação")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Recurso Atualizado com sucesso! O corpo da resposta é o recurso atualizado",
                    response = Group.class),

            @ApiResponse(
                    code = 404,
                    message = "Recurso não encontrado",
                    response = DetalheErro.class)})
    @PutMapping(path="/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Group putGroup(@ApiParam(value="Id do Group que deve ser atualizado", required = true)
                              @PathVariable("id") String id, @RequestBody Group group) {
        service.update(id, group);
        return group;
    }
    

    @ApiOperation(value= "Cria uma nova instância de group com os dados informados no corpo da requisição",
            notes = "É obrigatório um valor no campo name e é opcional uma lista de Users." +
                    " Tentar colocar um valor no  campo id retorna uma exceção correspondente." +
                    " O campo createdAt é automaticamente colocado pela API.")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Recurso criado com sucesso! O campo location do header tem a url do novo Recurso",
                    responseHeaders = @ResponseHeader(name = "location", response = URI.class,
                            description = "URI do novo recurso Group criado")),

            @ApiResponse(
                    code = 400,
                    message = "Recurso não pode ser criado corretamente pelo  corpo da requisição dada",
                    response = DetalheErro.class)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUser(@RequestBody Group group) {
        service.save(group);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(group.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value= "Retorna a instância de Group com o id informado")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Recurso encontrado e encaminhado com sucesso!",
                    response = Group.class),

            @ApiResponse(
                    code = 404,
                    message = "Recurso não encontrado.",
                    response = DetalheErro.class)})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Group getGroupById(@ApiParam(value="Id do Group que deve ser retornado", required = true)
                                @PathVariable("id") String id) {

        return service.findById(id);
    }

    @ApiOperation(value= "Retorna uma lista de instâncias de Group",
            notes="Sem os parâmetros da query string, essa operação retorna todas as instâncias de Group. " +
                  "Caso um filtro de name seja dado, ele retorna todas as instâncias que satisfazem a condição do " +
                  "filtro.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Recursos encontrados e encaminhados com sucesso!",
                    response = Group.class,
                    responseContainer = "List"),

            @ApiResponse(
                    code = 404,
                    message = "Recursos não encontrados.",
                    response = DetalheErro.class)})
    @ResponseStatus(HttpStatus.OK)
    public List<Group> getAllGroups(@ApiParam(value="Termo filtro do campo name")
                                        @RequestParam(name = "searchName", required = false, defaultValue = "")
                                                String name,
                              @ApiParam(value="Determina se o valor de searchName será correspondido exatamente (true)"
                                      + " ou parcialmente (qualquer outro valor será false)")
                                @RequestParam(name ="exactMatch", required = false, defaultValue = "false")
                                      String stringExactMatch,
                              @ApiParam(value="Determina se os Groups devem ser exibidos com os seus Users (true) ou não"
                                            + " (qualquer outro valor será false)")
                                @RequestParam(name="showUsers", required =false, defaultValue = "false")
                                          String stringShowUsers)
    {
        boolean showUsers = stringShowUsers.equals("true") ? true : false;
        boolean exactMatch = stringExactMatch.equals("true") ? true : false;
        if(showUsers) {
            return service.findAll(name, exactMatch);
        }
        else {
            return service.findAllWithoutUsers(name, exactMatch);
        }

    }
}
