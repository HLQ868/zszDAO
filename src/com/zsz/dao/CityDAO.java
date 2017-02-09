package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.CityDTO;
import com.zsz.dto.RegionDTO;



public class CityDAO
{
	//t_cities
	public CityDTO toDTO(ResultSet rs) throws SQLException
	{
		CityDTO dto = new CityDTO();
		dto.setId(rs.getLong("Id"));
		dto.setName(rs.getString("name"));
		
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		return dto;
	}
	
	public CityDTO getById(long id)
	{
		try
		{
			ResultSet rs =  JDBCUtils.executeQuery("select * from t_cities where id=? and isDeleted=0", id);
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
	
	public CityDTO[] getAll()//获取城市下的区域
	{
		ResultSet rs=null;
		List<CityDTO> list = new ArrayList<>();
		try
		{
			 rs =  JDBCUtils.executeQuery("select * from t_cities where isDeleted=0");
			while(rs.next())
			{ 

				list.add(toDTO(rs));
					
			}
			return list.toArray(new CityDTO[list.size()]);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}

}
