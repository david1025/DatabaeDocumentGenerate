package tj.david.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tj.david.entity.TableInfo;


public class JDBCUtils {
	
	String driver = "com.mysql.jdbc.Driver"; //驱动类
	String databaseName = "dmp"; //数据库名
    String url = "jdbc:mysql://10.3.5.196:3306/" + databaseName; //数据库连接地址
    String username = "root"; //数据库登录用户名
    String password = "root"; //数据库登录密码
	
	
	public Connection getConn() {
		
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	        
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public List<String> getAllTable(Connection connection) {
		List<String> list = new ArrayList<>();
		String sql = "show tables;";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
	            	
            	String tableName = new String(rs.getString("Tables_in_" + databaseName));
                list.add(tableName);
	        }
			pstmt.close();
			rs.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException = " + e.getMessage());
		}
		return list;
	}
	
	public List<TableInfo> getTableColumn(Connection connection, String tableName) {
		
		List<TableInfo> list = new ArrayList<TableInfo>();
		
		String sql = "SELECT column_name as name, column_comment as comment ,COLUMN_KEY as mk, COLUMN_TYPE as type "
				+ "FROM information_schema.columns WHERE table_schema = '"+ databaseName +"' AND table_name = '"+ tableName +"'";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
	            	
            	TableInfo info = new TableInfo();
            	info.setName(rs.getString("name"));
            	info.setComment(rs.getString("comment"));
            	info.setMk(rs.getString("mk"));
            	info.setType(rs.getString("type"));
                list.add(info);
	        }
			pstmt.close();
			rs.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException = " + e.getMessage());
		}
		
		return list;
	}
	

}
