package parts.routor;

import Service.Rotor;
import parts.PartsStorage;


import java.util.Map;

public class RotorStorage implements PartsStorage {
    Map<Integer, Rotor> rotorMap;

    public RotorStorage(Map<Integer, Rotor> routorMap) {
        this.rotorMap = routorMap;
    }

    public Rotor getRotorByID(int id) {
        return rotorMap.get(id);
    }

    @Override
    public int getPartCount() {
        return rotorMap.size();
    }

    @Override
    public String toString() {
        return "ReflectorStorage: \n" + rotorMap.values();
    }
}
