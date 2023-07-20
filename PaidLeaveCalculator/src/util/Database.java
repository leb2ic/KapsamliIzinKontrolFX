package util;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Database {
	private static final String DB_URL = "jdbc:sqlserver://hostname:port;databaseName=*****;encrypt=true;trustServerCertificate=true";
	private static final String DB_user = "****";
	private static final String DB_pass = "****";
    private static  Connection connection;
    public static void dbConnect() throws SQLException {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_user, DB_pass);
        } catch (SQLException e) {
            throw new RuntimeException("Connection Failed!", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
            	connection.close();
            }
        } catch (Exception e){
           throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt, Object... params) throws SQLException {
        ResultSet resultSet = null;
        CachedRowSet crs = null;
        PreparedStatement stmt = null;
        
        
        try {
        	dbConnect();
            stmt = connection.prepareStatement(queryStmt);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            boolean hasResultSet = stmt.execute();
            if (hasResultSet) {
                resultSet = stmt.getResultSet();
                crs = RowSetProvider.newFactory().createCachedRowSet();
                crs.populate(resultSet);
            } 	
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            //close();
        }
        //Return CachedRowSet
        return crs;
    }
    

    public static void dbExecuteUpdate(String sqlStmt, Object... params) throws SQLException {
    	PreparedStatement stmt = null;
        try {
        	dbConnect();
        	stmt = connection.prepareStatement(sqlStmt);       	
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            System.out.println("Update statement: " + sqlStmt + "\n");
            stmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            //close();
        }
    }
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate2(String sqlStmt, Object... params) throws SQLException, ClassNotFoundException {
    	PreparedStatement stmt = null;
        try {
        	dbConnect();
        	stmt = connection.prepareStatement(sqlStmt);        	
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            System.out.println("Update statement: " + sqlStmt + "\n");
            stmt.execute();
        }
        catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            //close();
        }
    }
}
