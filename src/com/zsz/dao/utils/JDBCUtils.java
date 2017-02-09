package com.zsz.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class JDBCUtils
{
	static
	{
		//dbcp2依赖于 commons-collections、commons-pool.jar、、commons-logging
		Properties prop = new Properties();
		try
		{
			prop.load(JDBCUtils.class.getResourceAsStream("/dbcp2.properties"));
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	private static DataSource dataSource;

	public static Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}

	public static void close(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
			}
		}
	}

	// 鍏抽棴statement
	public static void close(Statement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
			}
		}
	}

	// 鍏抽棴缁撴灉闆�
	public static void close(ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
			}
		}
	}

	// 鍏抽棴缁撴灉闆嗐�乻tatement銆佽繛鎺�
	public static void closeAll(ResultSet rs)
	{
		if (rs == null)
		{
			return;
		}
		try
		{
			Statement stmt = rs.getStatement();
			Connection conn = stmt.getConnection();
			close(rs);
			close(stmt);
			close(conn);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// 鎵цinsert銆乽pdate銆乨elete绛塻ql璇彞
	public static int executeUpdate(String sql, Object... parameters) throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			return executeUpdate(conn, sql, parameters);
		} finally
		{
			close(conn);
		}
	}

	// 鎵цinsert銆乽pdate銆乨elete绛塻ql璇彞
	public static int executeUpdate(Connection conn, String sql, Object... parameters) throws SQLException
	{
		PreparedStatement ps = null;
		try
		{
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++)
			{
				ps.setObject(i + 1, parameters[i]);
			}
			return ps.executeUpdate();
		} finally
		{
			close(ps);
		}
	}

	// 鎵ц鏌ヨ
	public static ResultSet executeQuery(String sql, Object... parameters) throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			return executeQuery(conn, sql, parameters);
		} catch (SQLException ex)
		{
			close(conn);
			throw ex;
		}
	}

	// 鎵ц鏌ヨ
	public static ResultSet executeQuery(Connection conn, String sql, Object... parameters) throws SQLException
	{
		PreparedStatement ps = null;
		try
		{
			ResultSet rs = null;
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++)
			{
				ps.setObject(i + 1, parameters[i]);
			}
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException ex)
		{
			close(ps);
			throw ex;
		}
	}

	public static Object executeScalar(String sql, Object... parameters) throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			return executeScalar(conn, sql, parameters);
		} finally
		{
			close(conn);
		}
	}

	public static Object executeScalar(Connection conn, String sql, Object... parameters) throws SQLException
	{
		ResultSet rs = null;
		try
		{
			rs = executeQuery(conn, sql, parameters);
			if (rs.next())
			{
				return rs.getObject(1);
			} else
			{
				return null;
			}
		} finally
		{
			close(rs);
		}
	}

	public static long queryLastInsertId(Connection conn) throws SQLException
	{
		Number id = (Number) executeScalar(conn, "SELECT LAST_INSERT_ID()");
		return id.longValue();
	}

	public static long executeInsertAndGetLastInsertId(String sql, Object... parameters) throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			executeUpdate(conn, sql, parameters);
			return queryLastInsertId(conn);
		} finally
		{
			close(conn);
		}
	}

	public static long executeInsertAndGetLastInsertId(Connection conn, String sql, Object... parameters)
			throws SQLException
	{
		executeUpdate(conn, sql, parameters);
		return queryLastInsertId(conn);
	}

	// 鍥炴粴
	public static void rollback(Connection conn)
	{
		try
		{
			conn.rollback();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException
	{
//		Object obj  = executeScalar("insert into T_Cities(Name) values('aaa');select @@identity");
		
		executeUpdate("insert into T_Cities(Name,IsDeleted) values('aaa',0)");
//		System.out.println(obj);
	}
}
