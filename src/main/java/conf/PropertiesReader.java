package conf;

import com.google.inject.Inject;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesReader {

    private final Properties properties;

    @Inject
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

    public boolean getBooleanProperty(String key) {
        String property = getProperty(key);
        if (property == null) {
            throw new MissingConfigurationException(key);
        }
        return Boolean.parseBoolean(getProperty(key));
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String property = getProperty(key);
        return (property == null) ? defaultValue : Boolean.parseBoolean(getProperty(key));
    }

    public int getIntProperty(String key) {
        String property = getProperty(key);
        if (property == null) {
            throw new MissingConfigurationException(key);
        }
        return Integer.parseInt(property);
    }

    public int getIntProperty(String key, int defaultValue) {
        String property = getProperty(key);
        return (property == null) ? defaultValue : Integer.parseInt(property);
    }

    public static class MissingConfigurationException extends RuntimeException {
        public MissingConfigurationException(String key) {
            super("Missing configuration for key " + key);
        }
    }
}
