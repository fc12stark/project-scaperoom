package edu.upc.dsa.manager;

import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.models.Hint;
import edu.upc.dsa.models.InventoryItem;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Level;
import edu.upc.dsa.models.User;

import java.util.List;

public interface GameManager {
    User addUser(String username, String password, String email);

    User login(String username, String password);

    User getUser(String id);

    User getUserByUsername(String username);

    Item addItem(String id, String nom, String descripcio, int preu, String tipus);

    Item getItem(String id);

    List<Item> getItems();

    List<Item> getShopItems();

    List<InventoryItem> getInventory(String userId);

    InventoryItem addInventoryItem(String userId, String itemId, int quantitat);

    InventoryItem returnItem(String userId, String itemId);

    Level addLevel(String id, String nom, String assignatura, int ordre, int recompensaEcts, String fragmentId);

    Level getLevel(String id);

    Level getLevelByOrdre(int ordre);

    List<Level> getLevels();

    Hint addHint(String id, String levelId, String text, int cost);

    List<Hint> getHintsByLevel(String levelId);

    User updateUserLevel(String userId, int ordre);

    GameSession createGameSession(String userId, String data);

    GameSession updateGameSession(String id, int errors, boolean completat, int puntuacio, int tempsEmprat, int pistesUsades);

    GameSession getGameSession(String id);

    List<GameSession> getGamesByUser(String userId);

    List<GameSession> getRanking();

    void clear();
}
