package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class DrawingFileStrategy implements FileStrategy {
    @Override
    public void save(Object data, File file) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
    }
}
