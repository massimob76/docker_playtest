package db;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CounterDAO {

    private static final String CREATE_AND_POPULATE_COUNTER_STMT =
            "CREATE TABLE IF NOT EXISTS Playtest (name VARCHAR(10) NOT NULL, value int NOT NULL, PRIMARY KEY (name));" +
            "INSERT INTO Playtest SELECT * FROM (SELECT 'counter', 0) x WHERE NOT EXISTS(SELECT * FROM Playtest);";

    private static final String GET_COUNTER_STMT = "SELECT value FROM Playtest WHERE name = 'counter';";
    private static final String UPDATE_COUNTER_STMT = "UPDATE Playtest SET value = ? WHERE name = 'counter';";
    private static final String INCREMENT_COUNTER_STMT = "UPDATE Playtest set value = value + 1 WHERE name = 'counter';";
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterDAO.class);

    private final ConnectionProvider connectionProvider;

    @Inject
    public CounterDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createTableIfNotExistsAndPopulateIt();
    }

    public int get() {
        try (Connection conn = connectionProvider.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_COUNTER_STMT);
            rs.next();
            return rs.getInt("value");
        } catch (SQLException e) {
            LOGGER.error("exception while getting counter value", e);
            return 0;
        }
    }

    public void update(int newIdx) {
        try (Connection conn = connectionProvider.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_COUNTER_STMT)) {
            stmt.setInt(1, newIdx);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("exception while updating counter", e);
        }
    }

    public void increment() {
        try (Connection conn = connectionProvider.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(INCREMENT_COUNTER_STMT);
        } catch (SQLException e) {
            LOGGER.error("exception while incrementing counter", e);
        }
    }

    public void reset() {
        update(0);
    }

    private void createTableIfNotExistsAndPopulateIt() {
        try (Connection conn = connectionProvider.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_AND_POPULATE_COUNTER_STMT);
        } catch (SQLException e) {
            LOGGER.error("exception while creating Counter table", e);
        }
    }

}
