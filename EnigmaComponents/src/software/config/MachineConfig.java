package software.config;


import hardware.engine.Engine;
import storage.StorageManager;


public abstract class MachineConfig {
    protected final StorageManager storageManager;

    public MachineConfig(StorageManager SM) {
        this.storageManager = SM;
    }

    public abstract Engine configureAndGetEngine();
}

