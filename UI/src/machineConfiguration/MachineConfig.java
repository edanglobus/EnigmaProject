package Manual;

import Service.Engine;
import parts.StorageManager;


public abstract class MachineConfig {
    protected final StorageManager storageManager;

    public MachineConfig(StorageManager SM) {
        this.storageManager = SM;
    }

    public abstract Engine configureAndGetEngine();
}

