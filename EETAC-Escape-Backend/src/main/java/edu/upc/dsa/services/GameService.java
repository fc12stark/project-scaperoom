package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.services.dto.GameRequest;
import edu.upc.dsa.services.dto.GameUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(value = "/games", description = "Endpoint per a les partides")
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
public class GameService {
    private GameManager gm;

    public GameService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Crear una partida", notes = "Crea una partida global per a un usuari")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Partida creada"),
            @ApiResponse(code = 400, message = "Dades incorrectes"),
            @ApiResponse(code = 404, message = "Usuari no trobat")
    })
    public Response createGame(GameRequest request) {
        if (request == null || request.getUserId() == null || request.getUserId().equals("")) {
            return Response.status(400).entity("{\"error\":\"Dades incorrectes\"}").build();
        }
        if (this.gm.getUser(request.getUserId()) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari no trobat\"}").build();
        }
        String data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        GameSession game = this.gm.createGameSession(request.getUserId(), data);
        return Response.status(201).entity(game).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Actualitzar una partida", notes = "Actualitza el progrés global")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Partida actualitzada"),
            @ApiResponse(code = 404, message = "Partida no trobada")
    })
    public Response updateGame(@PathParam("id") String id, GameUpdateRequest request) {
        GameSession game = this.gm.getGameSession(id);
        if (game == null || request == null) {
            return Response.status(404).entity("{\"error\":\"Partida no trobada\"}").build();
        }
        int errors = request.getErrors() == null ? game.getErrors() : request.getErrors();
        boolean completat = request.getCompletat() == null ? game.isCompletat() : request.getCompletat();
        int puntuacio = request.getPuntuacio() == null ? game.getPuntuacio() : request.getPuntuacio();
        int tempsEmprat = request.getTempsEmprat() == null ? game.getTempsEmprat() : request.getTempsEmprat();
        int pistesUsades = request.getPistesUsades() == null ? game.getPistesUsades() : request.getPistesUsades();
        GameSession updated = this.gm.updateGameSession(id, errors, completat, puntuacio, tempsEmprat, pistesUsades);
        return Response.ok(updated).build();
    }

}
