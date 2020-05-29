package dbUtils;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.Objects;

public class DbConnection {
    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "dbpassword";
    private static final String MYSQLCONN = "jdbc:mysql://localhost/login";
    private static final String DBFILEPATH = "db/notepadDB.db";
    private static final String SQLITECONN = "jdbc:sqlite:"+ DBFILEPATH; // jdbc:sqlite:sciezkaDoBazyPrawdopodobnie

    public static Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            File file = new File(DBFILEPATH);
            if(!file.exists())
            {
                file = new File("db");
                if(!file.exists()) {
                    boolean isDirectoryCreated = file.mkdir();
                }

                Connection conn = DriverManager.getConnection(SQLITECONN);

                createTables();
                return conn;
            }
            return DriverManager.getConnection(SQLITECONN);
        }
        catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static boolean createTables() {
        try {

            String sql = "CREATE TABLE if not exists files (\n" +
                    "    Nr              INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Name            VARCHAR (200),\n" +
                    "    Text            TEXT)\n";

            PreparedStatement ps = Objects.requireNonNull(getConnection()).prepareStatement(sql);
            ps.executeUpdate();

            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 1', 'Jakiś opis 1')");
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 2', 'Jakiś opis 2')");
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 3', 'Jakiś opis 3')");
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 4', 'Jakiś opis 4')");
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 5', 'Jakiś opis 5')");
            statement.executeUpdate("INSERT INTO files(Name, Text) VALUES('Zakładka 6', 'Jakiś opis 6')");

            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
}
