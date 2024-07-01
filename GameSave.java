import java.io.*;

public class GameSave implements Serializable {
    private TensuraQuestGame game;

    public GameSave(TensuraQuestGame game) {
        this.game = game;
    }

    public void saveGame(String arquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(this);
        }
    }

    public static GameSave loadGame(String arquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (GameSave) ois.readObject();
        }
    }

    public TensuraQuestGame getGame() {
        return game;
    }
}
