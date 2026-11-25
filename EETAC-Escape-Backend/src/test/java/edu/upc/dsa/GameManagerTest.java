package edu.upc.dsa;

import edu.upc.dsa.manager.GameManager;
import edu.upc.dsa.manager.GameManagerImpl;
import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.models.InventoryItem;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameManagerTest {
    private GameManager gm;

    @Before
    public void setUp() {
        this.gm = GameManagerImpl.getInstance();
        this.gm.clear();
    }

    @After
    public void tearDown() {
        this.gm.clear();
    }

    @Test
    public void testRegisterUserAndDuplicate() {
        User user = this.gm.addUser("prova", "1234", "prova@eetac.upc.edu");
        assertNotNull(user);
        assertEquals("prova", user.getUsername());
        assertEquals(1, user.getNivellActual());
        assertNull(this.gm.addUser("prova", "1234", "prova@eetac.upc.edu"));
    }

    @Test
    public void testLoginValidAndInvalid() {
        User admin = this.gm.getUser("admin");
        assertNotNull(admin);
        assertEquals("admin", this.gm.login("admin", "1234").getUsername());
        assertNull(this.gm.login("admin", "0000"));
        assertNull(this.gm.login("noexisteix", "1234"));
    }

    @Test
    public void testBuyItemIntoInventory() {
        User admin = this.gm.getUser("admin");
        int ectsAbans = admin.getEcts();
        InventoryItem item = this.gm.addInventoryItem(admin.getId(), "CAFE", 2);
        assertNotNull(item);
        assertEquals("admin", item.getUserId());
        assertEquals("CAFE", item.getItemId());
        assertEquals(2, item.getQuantitat());
        assertEquals(ectsAbans - 10, admin.getEcts());
        assertEquals(1, this.gm.getInventory(admin.getId()).size());
    }

    @Test
    public void testListShopItems() {
        List<Item> shopItems = this.gm.getShopItems();
        assertNotNull(shopItems);
        assertEquals(3, shopItems.size());

        Item cafe = findItem(shopItems, "CAFE");
        assertNotNull(cafe);
        assertEquals("Cafe", cafe.getNom());
        assertEquals(5, cafe.getPreu());
        assertEquals("BOTIGA", cafe.getTipus());

        Item pista = findItem(shopItems, "PISTA");
        assertNotNull(pista);
        assertEquals("Pista extra", pista.getNom());
        assertEquals(10, pista.getPreu());
        assertEquals("BOTIGA", pista.getTipus());

        Item casc = findItem(shopItems, "CASC");
        assertNotNull(casc);
        assertEquals("Casc de proteccio", casc.getNom());
        assertEquals(15, casc.getPreu());
        assertEquals("BOTIGA", casc.getTipus());
    }

    private Item findItem(List<Item> items, String id) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Test
    public void testReturnItemAndRefund() {
        User admin = this.gm.getUser("admin");
        int ectsAbans = admin.getEcts();
        this.gm.addInventoryItem(admin.getId(), "CAFE", 2);
        assertEquals(ectsAbans - 10, admin.getEcts());

        InventoryItem firstReturn = this.gm.returnItem(admin.getId(), "CAFE");
        assertNotNull(firstReturn);
        assertEquals(1, firstReturn.getQuantitat());
        assertEquals(ectsAbans - 5, admin.getEcts());

        InventoryItem secondReturn = this.gm.returnItem(admin.getId(), "CAFE");
        assertNotNull(secondReturn);
        assertEquals(0, secondReturn.getQuantitat());
        assertEquals(ectsAbans, admin.getEcts());
        assertEquals(0, this.gm.getInventory(admin.getId()).size());
        assertNull(this.gm.returnItem(admin.getId(), "CAFE"));
        assertEquals(ectsAbans, admin.getEcts());
    }

    @Test
    public void testLevelSixLockedAndUnlocked() {
        User admin = this.gm.getUser("admin");
        assertNull(this.gm.updateUserLevel(admin.getId(), 6));
        assertEquals(1, admin.getNivellActual());

        this.gm.addInventoryItem(admin.getId(), "FRAG_MV", 1);
        this.gm.addInventoryItem(admin.getId(), "FRAG_CESA", 1);
        this.gm.addInventoryItem(admin.getId(), "FRAG_INFO", 1);
        this.gm.addInventoryItem(admin.getId(), "FRAG_ERM", 1);
        this.gm.addInventoryItem(admin.getId(), "FRAG_ER", 1);

        User updated = this.gm.updateUserLevel(admin.getId(), 6);
        assertNotNull(updated);
        assertEquals(6, updated.getNivellActual());
        assertEquals(5, this.gm.getInventory(admin.getId()).size());
    }

    @Test
    public void testGameSessionCreateAndUpdate() {
        GameSession game = this.gm.createGameSession("admin", "2026-11-25 10:00:00");
        assertNotNull(game);
        assertNotNull(game.getId());
        assertEquals("admin", game.getUserId());
        assertFalse(game.isCompletat());

        GameSession updated = this.gm.updateGameSession(game.getId(), 1, true, 90, 120, 2);
        assertNotNull(updated);
        assertEquals(1, updated.getErrors());
        assertTrue(updated.isCompletat());
        assertEquals(90, updated.getPuntuacio());
        assertEquals(120, updated.getTempsEmprat());
        assertEquals(2, updated.getPistesUsades());
        assertEquals(1, this.gm.getGamesByUser("admin").size());
        assertEquals(1, this.gm.getRanking().size());
        List<GameSession> ranking = this.gm.getRanking();
        assertEquals(game.getId(), ranking.get(0).getId());
    }
}
