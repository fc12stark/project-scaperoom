package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.User;
import edu.upc.dsa.services.dto.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/sessions", description = "Endpoint per al login")
@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionService {
    private GameManager gm;

    public SessionService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Iniciar sessio", notes = "Comprova username i password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login correcte"),
            @ApiResponse(code = 401, message = "Credencials incorrectes")
    })
    public Response login(LoginRequest request) {
        if (request == null) {
            return Response.status(401).entity("{\"error\":\"Credencials incorrectes\"}").build();
        }
        User user = this.gm.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return Response.status(401).entity("{\"error\":\"Credencials incorrectes\"}").build();
        }
        return Response.ok(user).build();
    }
}
