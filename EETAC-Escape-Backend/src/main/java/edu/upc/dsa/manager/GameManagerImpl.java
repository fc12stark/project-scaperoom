package edu.upc.dsa.manager;

import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.models.Hint;
import edu.upc.dsa.models.InventoryItem;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Level;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;

    private HashMap<String, User> users;
    private HashMap<String, Item> items;
    private List<InventoryItem> inventory;
    private HashMap<String, Level> levels;
    private HashMap<String, GameSession> games;
    private List<Hint> hints;

    private static final Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        logger.info("Entrant al constructor de GameManagerImpl");

        this.users = new HashMap<String, User>();
        this.items = new HashMap<String, Item>();
        this.inventory = new ArrayList<InventoryItem>();
        this.levels = new HashMap<String, Level>();
        this.games = new HashMap<String, GameSession>();
        this.hints = new ArrayList<Hint>();

        this.initialize();

        logger.info("Sortint del constructor de GameManagerImpl");
    }

    private void initialize() {
        this.addLevel("MV", "Mecanica de vol", "MV", 1, 10, "FRAG_MV");
        this.addLevel("CESA", "CESA", "CESA", 2, 10, "FRAG_CESA");
        this.addLevel("INFO", "Informatica", "INFO", 3, 10, "FRAG_INFO");
        this.addLevel("ERM", "Estructures i Resistencia dels Materials", "ERM", 4, 10, "FRAG_ERM");
        this.addLevel("ER", "Emissors i Receptors", "ER", 5, 10, "FRAG_ER");
        this.addLevel("EG", "Expressio Grafica", "EG", 6, 20, "FRAG_EG");

        this.addItem("FRAG_MV", "Blueprint MV", "Fragment de la assignatura MV", 0, "FRAGMENT");
        this.addItem("FRAG_CESA", "Blueprint CESA", "Fragment de la assignatura CESA", 0, "FRAGMENT");
        this.addItem("FRAG_INFO", "Blueprint INFO", "Fragment de la assignatura INFO", 0, "FRAGMENT");
        this.addItem("FRAG_ERM", "Blueprint ERM", "Fragment de la assignatura ERM", 0, "FRAGMENT");
        this.addItem("FRAG_ER", "Blueprint ER", "Fragment de la assignatura ER", 0, "FRAGMENT");
        this.addItem("CAFE", "Cafe", "Cafe per recuperar energies", 5, "BOTIGA");
        this.addItem("PISTA", "Pista extra", "Pista addicional per a qualsevol nivell", 10, "BOTIGA");
        this.addItem("CASC", "Casc de proteccio", "Casc per al nivell ERM", 15, "BOTIGA");

        this.addHint("H1", "MV", "Pensa en la sustentacio", 2);
        this.addHint("H2", "CESA", "Comprova els senyals", 2);
        this.addHint("H3", "INFO", "Revisa el codi", 2);
        this.addHint("H4", "ERM", "Mira les forces", 2);
        this.addHint("H5", "ER", "Comprova la frequencia", 2);

        User admin = new User("admin", "1234", "admin@eetac.upc.edu", 100);
        admin.setId("admin");
        this.users.put(admin.getId(), admin);
    }

    public static GameManager getInstance() {
        logger.info("Entrant a getInstance");
        if (instance == null) {
            instance = new GameManagerImpl();
        }
        logger.info("Sortint de getInstance");
        return instance;
    }

    public User addUser(String username, String password, String email) {
        logger.info("Entrant a addUser amb username " + username);
        if (username == null || username.equals("") || password == null || password.equals("")) {
            logger.error("Error a addUser: username o password buits");
            return null;
        }
        if (this.getUserByUsername(username) != null) {
            logger.error("Error a addUser:username repetit " + username);
            return null;
        }
        User user = new User(username, password, email, 0);
        this.users.put(user.getId(), user);
        logger.info("Sortint de addUser amb id " + user.getId());
        return user;
    }

    public User login(String username, String password) {
        logger.info("Entrant a login amb username " + username);
        User user = this.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            logger.error("Error a login: credencials incorrectes");
            return null;
        }
        logger.info("Sortint de login");
        return user;
    }

    public User getUser(String id) {
        logger.info("Entrant a getUser amb id " + id);
        User user = this.users.get(id);
        logger.info("Sortint de getUser");
        return user;
    }

    public User getUserByUsername(String username) {
        logger.info("Entrant a getUserByUsername amb username " + username);
        for (String key : this.users.keySet()) {
            User user = this.users.get(key);
            if (user.getUsername().equals(username)) {
                logger.info("Sortint de getUserByUsername");
                return user;
            }
        }
        logger.info("Sortint de getUserByUsername sense resultat");
        return null;
    }

    public Item addItem(String id, String nom, String descripcio, int preu, String tipus) {
        logger.info("Entrant a addItem amb id " + id);
        Item item = new Item(id, nom, descripcio, preu, tipus);
        this.items.put(id, item);
        logger.info("Sortint de addItem");
        return item;
    }

    public Item getItem(String id) {
        logger.info("Entrant a getItem amb id " + id);
        Item item = this.items.get(id);
        logger.info("Sortint de getItem");
        return item;
    }

    public List<Item> getItems() {
        logger.info("Entrant a getItems");
        List<Item> result = new ArrayList<Item>();
        for (String key : this.items.keySet()) {
            result.add(this.items.get(key));
        }
        logger.info("Sortint de getItems");
        return result;
    }

    public List<Item> getShopItems() {
        logger.info("Entrant a getShopItems");
        List<Item> result = new ArrayList<Item>();
        for (String key : this.items.keySet()) {
            Item item = this.items.get(key);
            if (item.getTipus().equals("BOTIGA")) {
                result.add(item);
            }
        }
        logger.info("Sortint de getShopItems amb " + result.size() + " items");
        return result;
    }

    public List<InventoryItem> getInventory(String userId) {
        logger.info("Entrant a getInventory amb userId " + userId);
        List<InventoryItem> result = new ArrayList<InventoryItem>();
        for (int i = 0; i < this.inventory.size(); i++) {
            InventoryItem inventoryItem = this.inventory.get(i);
            if (inventoryItem.getUserId().equals(userId)) {
                result.add(inventoryItem);
            }
        }
        logger.info("Sortint de getInventory amb " + result.size() + " items");
        return result;
    }

    public InventoryItem addInventoryItem(String userId, String itemId, int quantitat) {
        logger.info("Entrant a addInventoryItem amb userId " + userId + " i itemId " + itemId);
        User user = this.getUser(userId);
        Item item = this.getItem(itemId);
        if (user == null || item == null) {
            logger.error("Error a addInventoryItem: usuari o item no trobat");
            return null;
        }
        if (quantitat <= 0) {
            logger.error("Error a addInventoryItem: quantitat no valida");
            return null;
        }
        int cost = item.getPreu() * quantitat;
        if (user.getEcts() < cost) {
            logger.error("Error a addInventoryItem: ECTS insuficients");
            return null;
        }

        user.setEcts(user.getEcts() - cost);
        InventoryItem found = null;
        for (int i = 0; i < this.inventory.size(); i++) {
            InventoryItem inventoryItem = this.inventory.get(i);
            if (inventoryItem.getUserId().equals(userId) && inventoryItem.getItemId().equals(itemId)) {
                found = inventoryItem;
            }
        }
        if (found == null) {
            found = new InventoryItem(userId, itemId, quantitat);
            this.inventory.add(found);
        } else {
            found.setQuantitat(found.getQuantitat() + quantitat);
        }
        logger.info("Sortint de addInventoryItem amb id " + found.getId());
        return found;
    }

    public InventoryItem returnItem(String userId, String itemId) {
        logger.info("Entrant a returnItem amb userId " + userId + " i itemId " + itemId);
        User user = this.getUser(userId);
        Item item = this.getItem(itemId);
        if (user == null || item == null) {
            logger.error("Error a returnItem: usuari o item no trobat");
            return null;
        }

        InventoryItem found = null;
        for (int i = 0; i < this.inventory.size(); i++) {
            InventoryItem inventoryItem = this.inventory.get(i);
            if (inventoryItem.getUserId().equals(userId) && inventoryItem.getItemId().equals(itemId)) {
                found = inventoryItem;
            }
        }
        if (found == null || found.getQuantitat() <= 0) {
            logger.error("Error a returnItem: objecte no present a l'inventari");
            return null;
        }

        found.setQuantitat(found.getQuantitat() - 1);
        user.setEcts(user.getEcts() + item.getPreu());
        if (found.getQuantitat() == 0) {
            this.inventory.remove(found);
        }
        logger.info("Sortint de returnItem, retornats " + item.getPreu() + " ECTS");
        return found;
    }

    public Level addLevel(String id, String nom, String assignatura, int ordre, int recompensaEcts, String fragmentId) {
        logger.info("Entrant a addLevel amb id " + id);
        Level level = new Level(id, nom, assignatura, ordre, recompensaEcts, fragmentId);
        this.levels.put(id, level);
        logger.info("Sortint de addLevel");
        return level;
    }

    public Level getLevel(String id) {
        logger.info("Entrant a getLevel amb id " + id);
        Level level = this.levels.get(id);
        logger.info("Sortint de getLevel");
        return level;
    }

    public Level getLevelByOrdre(int ordre) {
        logger.info("Entrant a getLevelByOrdre amb ordre " + ordre);
        for (String key : this.levels.keySet()) {
            Level level = this.levels.get(key);
            if (level.getOrdre() == ordre) {
                logger.info("Sortint de getLevelByOrdre");
                return level;
            }
        }
        logger.info("Sortint de getLevelByOrdre sense resultat");
        return null;
    }

    public List<Level> getLevels() {
        logger.info("Entrant a getLevels");
        List<Level> result = new ArrayList<Level>();
        for (int i = 1; i <= this.levels.size(); i++) {
            result.add(this.getLevelByOrdre(i));
        }
        logger.info("Sortint de getLevels");
        return result;
    }

    public Hint addHint(String id, String levelId, String text, int cost) {
        logger.info("Entrant a addHint amb id " + id);
        Hint hint = new Hint(id, levelId, text, cost);
        this.hints.add(hint);
        logger.info("Sortint de addHint");
        return hint;
    }

    public List<Hint> getHintsByLevel(String levelId) {
        logger.info("Entrant a getHintsByLevel amb levelId " + levelId);
        List<Hint> result = new ArrayList<Hint>();
        for (int i = 0; i < this.hints.size(); i++) {
            Hint hint = this.hints.get(i);
            if (hint.getLevelId().equals(levelId)) {
                result.add(hint);
            }
        }
        logger.info("Sortint de getHintsByLevel");
        return result;
    }

    public User updateUserLevel(String userId, int ordre) {
        logger.info("Entrant a updateUserLevel amb userId " + userId + " i ordre " + ordre);
        User user = this.getUser(userId);
        Level level = this.getLevelByOrdre(ordre);
        if (user == null || level == null) {
            logger.error("Error a updateUserLevel: usuari o nivell no trobat");
            return null;
        }
        if (ordre == 6) {
            List<InventoryItem> inventoryUser = this.getInventory(userId);
            int fragments = 0;
            for (int i = 0; i < inventoryUser.size(); i++) {
                InventoryItem inventoryItem = inventoryUser.get(i);
                Item item = this.getItem(inventoryItem.getItemId());
                if (item != null && item.getTipus().equals("FRAGMENT") && inventoryItem.getQuantitat() > 0) {
                    fragments++;
                }
            }
            if (fragments < 5) {
                logger.error("Error a updateUserLevel: falten fragments per al nivell 6");
                return null;
            }
        }
        user.setNivellActual(ordre);
        logger.info("Sortint de updateUserLevel amb nivell " + ordre);
        return user;
    }

    public GameSession createGameSession(String userId, String data) {
        logger.info("Entrant a createGameSession amb userId " + userId);
        if (userId == null || this.getUser(userId) == null) {
            logger.error("Error a createGameSession: usuari no trobat");
            return null;
        }
        GameSession game = new GameSession(userId, data);
        this.games.put(game.getId(), game);
        logger.info("Sortint de createGameSession amb id " + game.getId());
        return game;
    }

    public GameSession updateGameSession(String id, int errors, boolean completat, int puntuacio, int tempsEmprat, int pistesUsades) {
        logger.info("Entrant a updateGameSession amb id " + id);
        GameSession game = this.games.get(id);
        if (game == null) {
            logger.error("Error a updateGameSession: partida no trobada");
            return null;
        }
        game.setErrors(errors);
        game.setCompletat(completat);
        game.setPuntuacio(puntuacio);
        game.setTempsEmprat(tempsEmprat);
        game.setPistesUsades(pistesUsades);
        logger.info("Sortint de updateGameSession");
        return game;
    }

    public GameSession getGameSession(String id) {
        logger.info("Entrant a getGameSession amb id " + id);
        GameSession game = this.games.get(id);
        logger.info("Sortint de getGameSession");
        return game;
    }

    public List<GameSession> getGamesByUser(String userId) {
        logger.info("Entrant a getGamesByUser amb userId " + userId);
        List<GameSession> result = new ArrayList<GameSession>();
        for (String key : this.games.keySet()) {
            GameSession game = this.games.get(key);
            if (game.getUserId().equals(userId)) {
                result.add(game);
            }
        }
        logger.info("Sortint de getGamesByUser amb " + result.size() + " partides");
        return result;
    }

    public List<GameSession> getRanking() {
        logger.info("Entrant a getRanking");
        List<GameSession> result = new ArrayList<GameSession>();
        for (String key : this.games.keySet()) {
            result.add(this.games.get(key));
        }
        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(j).getPuntuacio() > result.get(i).getPuntuacio()) {
                    GameSession canvi = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, canvi);
                }
            }
        }
        logger.info("Sortint de getRanking");
        return result;
    }

    public void clear() {
        logger.info("Entrant a clear");
        this.users.clear();
        this.items.clear();
        this.inventory.clear();
        this.levels.clear();
        this.games.clear();
        this.hints.clear();
        this.initialize();
        logger.info("Sortint de clear");
    }
}
