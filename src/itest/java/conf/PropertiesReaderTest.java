package conf;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class PropertiesReaderTest {

    private PropertiesReader propertiesReader;

    @Before
    public void setUp() throws IOException {
        propertiesReader = new PropertiesReader();
    }

    @Test
    public void propertiesAreReadCorrectly() {
        assertThat(propertiesReader.getProperty("projectName"), is("docker_playtest"));
    }

    @Test
    public void nonExistingPropertiesReturnNull() {
        assertThat(propertiesReader.getProperty("non-existent"), is(nullValue()));
    }

}