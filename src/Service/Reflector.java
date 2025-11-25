package Service;

import java.util.Objects;

public class Reflector implements CipherMapable {
    String ID;
    int[] wires;


    public Reflector(String ID, int[] wires) {
        this.ID = ID;
        this.wires = wires;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reflector reflector = (Reflector) o;
        return Objects.equals(ID, reflector.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }
}
