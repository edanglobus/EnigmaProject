package fileHandler;

import java.util.List;

public interface WiredPart<IO> {


    String getPartType();
    int getID();
    List<IO> getWires();
}
