package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.IdNameDTO;
import com.zsz.dto.RegionDTO;

public class RegionDAO
{
	public RegionDTO toDTO(ResultSet rs) throws SQLException
	{
		RegionDTO dto = new RegionDTO();
		dto.setId(rs.getLong("Id"));
		dto.setName(rs.getString("name"));
		dto.setCityId(rs.getLong("CityId"));
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		return dto;
	}
	public RegionDTO getById(long id)
	{
		try
		{
			ResultSet rs =  JDBCUtils.executeQuery("select * from t_regions where id=? and isDeleted=0", id);
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
	public RegionDTO[] getAll(long cityId)//获取城市下的区域
	{
		ResultSet rs=null;
		List<RegionDTO> list = new ArrayList<>();
		try
		{
			 rs =  JDBCUtils.executeQuery("select * from t_regions where cityId=? and isDeleted=0", cityId);
			while(rs.next())
			{ 

				list.add(toDTO(rs));
					
			}
			return list.toArray(new RegionDTO[list.size()]);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}

}
