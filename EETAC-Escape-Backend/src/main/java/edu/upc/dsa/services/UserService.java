package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/users", description = "Endpoint per a usuaris i inventari")
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
    private GameManager gm;

    public UserService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Registrar un usuari", notes = "Crea un usuari nou")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuari creat"),
            @ApiResponse(code = 400, message = "Dades incorrectes"),
            @ApiResponse(code = 409, message = "El nom d'usuari ja existeix")
    })
    public Response addUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().equals("")
                || user.getPassword() == null || user.getPassword().equals("")) {
            return Response.status(400).entity("{\"error\":\"Dades incorrectes\"}").build();
        }
        if (this.gm.getUserByUsername(user.getUsername()) != null) {
            return Response.status(409).entity("{\"error\":\"Nom d'usuari repetit\"}").build();
        }
        User created = this.gm.addUser(user.getUsername(), user.getPassword(), user.getEmail());
        return Response.status(201).entity(created).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Obtenir un usuari", notes = "Cerca un usuari pel seu id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuari trobat"),
            @ApiResponse(code = 404, message = "Usuari no trobat")
    })
    public Response getUser(@PathParam("id") String id) {
        User user = this.gm.getUser(id);
        if (user == null) {
            return Response.status(404).entity("{\"error\":\"Usuari no trobat\"}").build();
        }
        return Response.ok(user).build();
    }

}
