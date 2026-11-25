package edu.upc.dsa.services;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.Item;
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

@Api(value = "/items", description = "Endpoint per a la botiga")
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemService {
    private GameManager gm;

    public ItemService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Llista d'objectes de la botiga", notes = "Retorna els objectes amb tipus BOTIGA")
    @ApiResponse(code = 200, message = "Llista d'objectes de la botiga")
    public Response getShopItems() {
        List<Item> items = this.gm.getShopItems();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {
        };
        return Response.ok(entity).build();
    }
}
