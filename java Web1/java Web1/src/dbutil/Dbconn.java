package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconn {
	
	private Connection conn;
	/*public  Connection getConnection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/123?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai","root","yy20021220");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到服务！！");
			e.printStackTrace();
		}
		return conn;
	}*/
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/duane?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "1234567890");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到服务！！");
			throw new SQLException("数据库驱动未找到", e); // 抛出 SQLException 异常通知调用者
		}
		return conn;
	}


	public void closeAll(Connection conn,Statement stat,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(stat!=null){
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						if(conn!=null){
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
}
