package conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesReader {

    private static final String PROPERTIES_FILE = "config.properties";
    private final Properties properties;

    public PropertiesReader() throws IOException {
        Path path = FileSystems.getDefault().getPath(PROPERTIES_FILE);
        Reader reader = Files.newBufferedReader(path);
        Properties properties = new Properties();
        properties.load(reader);
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) throws IOException {
        String value = new PropertiesReader().getProperty("test");
        System.out.println("value: " + value);
    }
}
