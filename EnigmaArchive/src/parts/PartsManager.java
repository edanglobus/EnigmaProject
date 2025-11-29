package parts;

public class PartsManager {

    PartsConfigValidator PV = new PartsConfigValidator();

    public int getPartCount(PartsStorage storage) {
        return storage.getPartCount();
    }
}
