package engine.interfaces;

public interface IGameLogic {

    void preInit();

    void init();

    void postInit();

    void loop();

    void handleEvents();

    void tick();

    void destroy();

    void print(String mes);

}
