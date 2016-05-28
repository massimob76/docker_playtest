package conf;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String propertyFile) throws IOException {
        Path path = FileSystems.getDefault().getPath(propertyFile);
        Reader reader = Files.newBufferedReader(path);
        Properties properties = new Properties();
        properties.load(reader);
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
