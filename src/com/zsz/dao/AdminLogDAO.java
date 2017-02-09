package com.zsz.dao;

import java.sql.SQLException;

import com.zsz.dao.utils.JDBCUtils;
import org.junit.*;
public class AdminLogDAO
{

	public void addnew(long adminUserId, String message)
	{
		try
		{
			JDBCUtils.executeUpdate("insert into t_adminlogs(AdminUserId,CreateDateTime,Message) values(?,now(),?)", adminUserId,message);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}
	@Test
	public void test()
	{
		addnew(15,"123test");
	}

}
