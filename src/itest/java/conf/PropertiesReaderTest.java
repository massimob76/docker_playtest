package conf;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PropertiesReaderTest {

    private static final String ITEST_PROPERTIES = "conf/iTest.properties";
    private PropertiesReader propertiesReader;

    @Before
    public void setUp() throws IOException {
        propertiesReader = new PropertiesReader(ITEST_PROPERTIES);
    }

    @Test
    public void propertiesAreReadCorrectly() {
        assertThat(propertiesReader.getProperty("test"), is("true"));
    }

    @Test
    public void nonExistingPropertiesReturnNull() {
        assertThat(propertiesReader.getProperty("non-existent"), is(nullValue()));
    }

    @Test
    public void booleanPropertiesAreReadCorrectly() {
        assertThat(propertiesReader.getBooleanProperty("booleanProperty"), is(true));
    }

    @Test(expected = PropertiesReader.MissingConfigurationException.class)
    public void nonExistingBooleanPropertiesReturnException() {
        propertiesReader.getBooleanProperty("non-existent");
    }

    @Test
    public void nonExistingBooleanPropertiesReturnDefault() {
        assertThat(propertiesReader.getBooleanProperty("non-existent", true), is(true));
    }

    @Test
    public void intPropertiesAreReadCorrectly() {
        assertThat(propertiesReader.getIntProperty("intProperty"), is(123));
    }

    @Test(expected = PropertiesReader.MissingConfigurationException.class)
    public void nonExistingIntPropertiesReturnException() {
        propertiesReader.getIntProperty("non-existent");
    }

    @Test
    public void nonExistingIntPropertiesReturnDefault() {
        assertThat(propertiesReader.getIntProperty("non-existent", 0), is(0));
    }

}