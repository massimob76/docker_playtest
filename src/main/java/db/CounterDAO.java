package db;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CounterDAO {

    private static final String GET_COUNTER_STMT = "SELECT Idx FROM Counter;";
    private static final String UPDATE_COUNTER_STMT = "UPDATE Counter SET Idx = ?;";
    private final Connection conn;
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterDAO.class);

    @Inject
    public CounterDAO(ConnectionProvider connectionProvider) {
        conn = connectionProvider.getConnetion();
    }

    public int get() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_COUNTER_STMT);
            rs.next();
            return rs.getInt("Idx");
        } catch (SQLException e) {
            LOGGER.error("exception while fetching data from DB: {}", e);
            return 0;
        }
    }

    public void update(int newIdx) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(UPDATE_COUNTER_STMT);
            stmt.setInt(1, newIdx);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("exception while updating counter {}", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOGGER.error("exception while closing statement {}", e);
                }
            }
        }
    }

    public void reset() {
        update(0);
    }

}
