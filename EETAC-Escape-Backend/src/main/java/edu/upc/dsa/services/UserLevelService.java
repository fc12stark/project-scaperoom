package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.User;
import edu.upc.dsa.services.dto.LevelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/users/{id}/level", description = "Endpoint per canviar de nivell")
@Path("/users/{id}/level")
@Produces(MediaType.APPLICATION_JSON)
public class UserLevelService {
    private GameManager gm;

    public UserLevelService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Inicialitzar un nivell", notes = "Actualitza el nivell actual de l'usuari")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nivell actualitzat"),
            @ApiResponse(code = 403, message = "Falten fragments"),
            @ApiResponse(code = 404, message = "Usuari o nivell no trobat")
    })
    public Response updateLevel(@PathParam("id") String id, LevelRequest request) {
        if (request == null) {
            return Response.status(404).entity("{\"error\":\"Nivell no trobat\"}").build();
        }
        if (this.gm.getUser(id) == null || this.gm.getLevelByOrdre(request.getOrdre()) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari o nivell no trobat\"}").build();
        }
        User user = this.gm.updateUserLevel(id, request.getOrdre());
        if (user == null) {
            return Response.status(403).entity("{\"error\":\"Falten fragments per al nivell 6\"}").build();
        }
        return Response.ok(user).build();
    }
}
