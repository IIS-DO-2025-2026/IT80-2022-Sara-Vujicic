package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class LogFileStrategy implements FileStrategy {
    @Override
    public void save(Object data, File file) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if (data instanceof String) {
                writer.write((String) data);
            } else if (data instanceof List) {
                List<?> list = (List<?>) data;
                for (Object line : list) {
                    writer.write(line.toString());
                    writer.newLine();
                }
            } else {
                writer.write(data.toString());
            }
        }
    }
}
