package Service;

import java.util.Objects;

public class Router implements Roundable, CipherMapable {
    final int ID;
    final int noches;

    public Router(int ID, int noches) {
        this.ID = ID;
        this.noches = noches;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return ID == router.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }
}
