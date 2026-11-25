package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.InventoryItem;
import edu.upc.dsa.services.dto.InventoryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/users/{id}/inventory", description = "Endpoint per a la botiga")
@Path("/users/{id}/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryService {
    private GameManager gm;

    public InventoryService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Obtenir l'inventari", notes = "Llista d'objectes d'un usuari")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Inventari obtingut"),
            @ApiResponse(code = 404, message = "Usuari no trobat")
    })
    public Response getInventory(@PathParam("id") String id) {
        if (this.gm.getUser(id) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari no trobat\"}").build();
        }
        return Response.ok(this.gm.getInventory(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Comprar un objecte", notes = "Afegeix un objecte a l'inventari i descompta ECTS")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Objecte comprat"),
            @ApiResponse(code = 402, message = "ECTS insuficients"),
            @ApiResponse(code = 404, message = "Usuari o objecte no trobat")
    })
    public Response addInventoryItem(@PathParam("id") String id, InventoryRequest request) {
        if (this.gm.getUser(id) == null || request == null || this.gm.getItem(request.getItemId()) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari o objecte no trobat\"}").build();
        }
        InventoryItem item = this.gm.addInventoryItem(id, request.getItemId(), request.getQuantitat());
        if (item == null) {
            return Response.status(402).entity("{\"error\":\"ECTS insuficients\"}").build();
        }
        return Response.ok(item).build();
    }

    @DELETE
    @Path("/{itemId}")
    @ApiOperation(value = "Retornar un objecte", notes = "Treu una unitat de l'inventari i retorna el seu preu en ECTS")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Objecte retornat"),
            @ApiResponse(code = 404, message = "Usuari, objecte o unitat no trobada")
    })
    public Response returnItem(@PathParam("id") String id, @PathParam("itemId") String itemId) {
        if (this.gm.getUser(id) == null || this.gm.getItem(itemId) == null) {
            return Response.status(404).entity("{\"error\":\"Usuari o objecte no trobat\"}").build();
        }
        InventoryItem item = this.gm.returnItem(id, itemId);
        if (item == null) {
            return Response.status(404).entity("{\"error\":\"Objecte no present a l'inventari\"}").build();
        }
        return Response.ok(item).build();
    }
}
