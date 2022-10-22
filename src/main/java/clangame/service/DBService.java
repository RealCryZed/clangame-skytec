package clangame.service;

import clangame.config.JdbcConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {

    public static void getAllClans() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM clans");

            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1) + ", clanName: "
                        + rs.getString(2) + ", gold: " + rs.getString(3));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getAllTaskTransactions() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM gold_from_task");

            while (rs.next()) {
                System.out.println("transactionId: " + rs.getInt(1) + ", taskId: "
                        + rs.getInt(2) + ", clanId: " + rs.getInt(3) + ", initialGold: "
                        + rs.getInt(4) + ", finalGold: " + rs.getInt(5) + ", addedGold: " + rs.getInt(6));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getAllDepositTransactions() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM gold_from_donation");

            while (rs.next()) {
                System.out.println("transactionId: " + rs.getInt(1) + ", userId: "
                        + rs.getInt(2) + ", clanId: " + rs.getInt(3) + ", initialGold: "
                        + rs.getInt(4) + ", finalGold: " + rs.getInt(5) + ", addedGold: " + rs.getInt(6));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
