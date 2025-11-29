package parts.reflector;

import Service.Reflector;
import parts.PartsStorage;

import java.util.Map;

public class ReflectorStorage implements PartsStorage {
    private Map<String, Reflector> reflectorMap;

    public ReflectorStorage(Map<String, Reflector> reflectors) {
        this.reflectorMap = reflectors;
    }

    public Reflector getReflectorByID(String id) {
        return reflectorMap.get(id);
    }

    @Override
    public int getPartCount() {
        return reflectorMap.size();
    }

    public void setReflectorMap(Map<String, Reflector> reflectors) {
        this.reflectorMap = reflectors;
    }

    @Override
    public String toString() {
        return "ReflectorStorage: \n" + reflectorMap.values();
    }

}
