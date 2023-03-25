import engine.Game;
import engine.interfaces.IGameLogic;
import org.lwjgl.opengl.*;

public class Main {
    final static Game game= new Game(1280, 720, "Avocado Engine");

    public static void main(String[] args)
    {
        game.preInit();
    }
}