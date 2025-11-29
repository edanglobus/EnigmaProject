package parts.routor;

import Service.Router;
import parts.PartsStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutorStorage implements PartsStorage {
    Map<Integer, Router> routorMap = new HashMap<>();


    // to do that only jaxb can create storage


    public Router getRoutorByID(int id) {
        return routorMap.get(id);
    }

    @Override
    public int getPartCount() {
        return routorMap.size();
    }

    public void setRoutorMap(List<Router> routors) {
        for (Router r : routors) {
            routorMap.put(r.getID(), r);
        }
    }
}
