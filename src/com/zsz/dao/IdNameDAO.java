package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.IdNameDTO;

public class IdNameDAO
{
	//类别名，名字
	public long addIdName(String typeName, String name)
	{
		try
		{
			return JDBCUtils.executeInsertAndGetLastInsertId("insert into t_idnames(TypeName,Name,IsDeleted) values(?,?,0)", typeName,name);
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
			
		}
	}
	public IdNameDTO getById(long id)
	{
		
		try
		{
			ResultSet rs =  JDBCUtils.executeQuery("select * from t_idnames where id=? and isDeleted=0", id);
			if(rs.next())
			{ 
		
				return toDTO(rs);
				
			}
			return null;
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}
	
	public IdNameDTO toDTO(ResultSet rs) throws SQLException
	{

		IdNameDTO dto = new IdNameDTO();
		dto.setId(rs.getLong("id"));
		dto.setName(rs.getString("name"));
		dto.setTypeName(rs.getString("typeName"));
		
		return dto;
		
	}
	
	//获取类别下的IdName（比如所有的民族）
	public IdNameDTO[] getAll(String typeName)
	{
		ResultSet rs=null;
		List<IdNameDTO> list = new ArrayList<>();
		try
		{
			 rs =  JDBCUtils.executeQuery("select * from t_idnames where typeName=? and isDeleted=0", typeName);
			while(rs.next())
			{ 

				list.add(toDTO(rs));
					
			}
			return list.toArray(new IdNameDTO[list.size()]);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
		
	}

}
