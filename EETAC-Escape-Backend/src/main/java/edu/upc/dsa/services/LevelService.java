package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.Level;
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

@Api(value = "/levels", description = "Endpoint per als nivells")
@Path("/levels")
@Produces(MediaType.APPLICATION_JSON)
public class LevelService {
    private GameManager gm;

    public LevelService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Llista de nivells", notes = "Retorna els 6 nivells")
    @ApiResponse(code = 200, message = "Llista de nivells")
    public Response getLevels() {
        List<Level> levels = this.gm.getLevels();
        GenericEntity<List<Level>> entity = new GenericEntity<List<Level>>(levels) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Obtenir un nivell", notes = "Cerca un nivell pel seu id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nivell trobat"),
            @ApiResponse(code = 404, message = "Nivell no trobat")
    })
    public Response getLevel(@PathParam("id") String id) {
        Level level = this.gm.getLevel(id);
        if (level == null) {
            return Response.status(404).entity("{\"error\":\"Nivell no trobat\"}").build();
        }
        return Response.ok(level).build();
    }
}
