package db;

import conf.PropertiesReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CounterDAOTest {

    private CounterDAO counterDAO;

    @Before
    public void setUp() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        PropertiesReader propertiesReader = new PropertiesReader("iTest.properties");
        ConnectionProvider connectionProvider = new ConnectionProvider(propertiesReader);
        counterDAO = new CounterDAO(connectionProvider);
    }

    @Test
    public void testResetGetUpdateIncrement() {
        counterDAO.reset();
        assertThat(counterDAO.get(), is(0));
        counterDAO.update(5);
        assertThat(counterDAO.get(), is(5));
        counterDAO.update(2);
        assertThat(counterDAO.get(), is(2));
        counterDAO.increment();
        assertThat(counterDAO.get(), is(3));
        counterDAO.increment();
        assertThat(counterDAO.get(), is(4));
    }
}
