package Service;

import java.util.List;
import java.util.logging.Logger;

public class Engine {
    private final Router[] routers;
    private final Reflector reflactor;

    public Engine(Router[] routers, Reflector reflactor) {
        this.routers = routers;
        this.reflactor = reflactor;
    }
}
