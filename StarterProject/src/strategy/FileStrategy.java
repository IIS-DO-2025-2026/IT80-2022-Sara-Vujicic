package strategy;

import java.io.File;

public interface FileStrategy {
    void save(Object data, File file) throws Exception;
}
