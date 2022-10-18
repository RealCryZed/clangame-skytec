package clangame;

import clangame.config.JdbcConnection;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            Connection con = JdbcConnection.getConnection();

            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("src/main/java/resources/data.sql"));
            sr.runScript(reader);

            String query = "insert into clans values(1, 'Clan1', 50)";
            String query2 = "SELECT id, name, gold FROM clans";

            Statement statement = con.createStatement();
            statement.execute(query);

            ResultSet rs = statement.executeQuery(query2);

            while (rs.next()) {
                System.out.println(rs.getLong("id") + ", " + rs.getString("name") + ", " + rs.getInt("gold"));
            }

            con.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
