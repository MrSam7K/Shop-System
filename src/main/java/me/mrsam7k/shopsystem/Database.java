package me.mrsam7k.shopsystem;

import java.sql.*;

public class Database {

    public final Main plugin;
    public static Connection connection = null;

    public Database(Main plugin, Connection connection) {
        this.plugin = plugin;
        this.connection = connection;
    }



    public static void init() {
        try {
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS playerUpgrades(uuid varchar(255) primary key, upgrade varchar(255), tier int)");
            create.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/databaseDB";
            String user = "root";
            String pass = "netcluster";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static PlayerStats findPlayerStatsByUUID(String uuid, String upgrade) throws SQLException {

        uuid = uuid.replace("-", "") + "_" + upgrade.replace(" ", "_").toLowerCase();

        PreparedStatement statement = connection
                .prepareStatement("SELECT * FROM playerUpgrades WHERE uuid = ?");
        statement.setString(1, uuid);
        ResultSet rs = statement.executeQuery();

        if(rs.next()) {
            PlayerStats playerStats = new PlayerStats(uuid + "_" + upgrade.replace(" ", "_").toLowerCase(), rs.getInt("tier"));
            statement.close();
            return playerStats;
        }
        statement.close();
        return null;
    }

    public static void createPlayerStats(PlayerStats playerStats) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO playerUpgrades(uuid, tier) VALUES (?, ?)");
        preparedStatement.setString(1, playerStats.getPlayerUUID());
        preparedStatement.setInt(2, playerStats.getTier());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updatePlayerStats(PlayerStats playerStats, String upgrade) throws SQLException {
        String uuid = playerStats.getPlayerUUID().split("_")[0].replace("-", "") + "_" + upgrade.replace(" ", "_").toLowerCase();

        PreparedStatement preparedStatement = this.plugin.getDatabase().getConnection()
                .prepareStatement("UPDATE playerUpgrades SET tier = ? WHERE uuid = ?");
        preparedStatement.setInt(1, playerStats.getTier());
        preparedStatement.setString(2, uuid);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}
