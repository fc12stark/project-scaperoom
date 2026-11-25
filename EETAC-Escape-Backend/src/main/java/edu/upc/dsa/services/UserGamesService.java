package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.GameSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/users/{id}/games", description = "Endpoint per a les partides d'un usuari")
@Path("/users/{id}/games")
@Produces(MediaType.APPLICATION_JSON)
public class UserGamesService {
    private GameManager gm;

    public UserGamesService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Partides d'un usuari", notes = "Llista de partides d'un usuari")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Llista de partides"),
            @ApiResponse(code = 404, message = "Usuari no trobat")
    })
    public Response getGamesByUser(@PathParam("id") String id) {
        if (this.gm.getUser(id) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari no trobat\"}").build();
        }
        List<GameSession> games = this.gm.getGamesByUser(id);
        GenericEntity<List<GameSession>> entity = new GenericEntity<List<GameSession>>(games) {
        };
        return Response.ok(entity).build();
    }
}
