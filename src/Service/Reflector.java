package Service;

import java.util.Objects;

public class Reflector implements CipherMapable {
    String ID;

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
