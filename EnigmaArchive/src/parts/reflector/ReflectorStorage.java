package parts.reflector;

import Service.Reflector;
import parts.PartsStorage;

import java.util.Map;

public class ReflectorStorage implements PartsStorage {
    private Map<String, Reflector> reflectorMap;


    public Reflector getReflectorByID(String id) {
        return reflectorMap.get(id);
    }

    @Override
    public int getPartCount() {
        return reflectorMap.size();
    }

}
