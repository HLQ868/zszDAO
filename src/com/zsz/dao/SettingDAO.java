package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.SettingDTO;

public class SettingDAO
{
	public void setValue(String name, String value)//设置配置项name的值为value
	{
		try
		{
			JDBCUtils.executeUpdate("update t_settings set value=? where name =?", value,name);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}
	public String getValue(String name)//获取配置项name的值
	{
		ResultSet rs=null;
		try
		{
			rs = JDBCUtils.executeQuery("select * from t_settings where name=?", name);
			if(rs.next())
			{
				return rs.getString("value");
			}
			return null;
			
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	public SettingDTO[] getAll()
	{
		List<SettingDTO> list = new ArrayList<>();
		ResultSet rs=null;
		try
		{
			rs = JDBCUtils.executeQuery("select * from t_settings");
		while(rs.next())
		{
			SettingDTO dto = new SettingDTO();
			dto.setId(rs.getLong("id"));
			dto.setName(rs.getString("name"));
			dto.setTypeName(rs.getString("value"));
			list.add(dto);
		}
			return list.toArray(new SettingDTO[list.size()]);
			
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
		
		
	}

}
