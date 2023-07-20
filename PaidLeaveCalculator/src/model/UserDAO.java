package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Database;
/*
 * 
 * Program kullanıcısının kontrol edilmesini sağlayan fonksiyonu içeren
 * Data Access Object class'ı
 * 
 */
public class UserDAO {
    public static boolean checkUser(String username, String password) throws ClassNotFoundException, SQLException {
        String selectStmt = "SELECT * FROM dbo.Tab_Kullanici WHERE username = ? AND password = ?";
        try (ResultSet rs = Database.dbExecuteQuery(selectStmt, username, password)) {
            return rs.next();
        }
    }
}
