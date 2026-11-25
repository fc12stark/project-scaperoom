package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.GameSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/ranking", description = "Endpoint per al ranking")
@Path("/ranking")
@Produces(MediaType.APPLICATION_JSON)
public class RankingService {
    private GameManager gm;

    public RankingService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Ranking", notes = "Partides ordenades per puntuacio")
    @ApiResponse(code = 200, message = "Ranking obtingut")
    public Response getRanking() {
        List<GameSession> ranking = this.gm.getRanking();
        GenericEntity<List<GameSession>> entity = new GenericEntity<List<GameSession>>(ranking) {
        };
        return Response.ok(entity).build();
    }
}
