package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.CommunityDTO;
import com.zsz.dto.RegionDTO;

public class CommunityDAO
{
	public CommunityDTO toDTO(ResultSet rs) throws SQLException
	{
		CommunityDTO dto = new CommunityDTO();
		dto.setId(rs.getLong("Id"));
		dto.setName(rs.getString("name"));
		dto.setRegionId(rs.getLong("RegionId"));
		dto.setBuiltYear( (Integer)rs.getObject("BuiltYear"));
		dto.setLocation(rs.getString("Location"));
		dto.setTraffic(rs.getString("Traffic"));
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		return dto;
	}
	public CommunityDTO[] getByRegionId(long regionId)
	{
		ResultSet rs=null;
		List<CommunityDTO> list = new ArrayList<>();
		try
		{
			 rs =  JDBCUtils.executeQuery("select * from t_communities where regionId=? and isDeleted=0", regionId);
			while(rs.next())
			{ 

				list.add(toDTO(rs));
					
			}
			return list.toArray(new CommunityDTO[list.size()]);
		} catch (SQLException e)
		{
			
			throw new RuntimeException(e);
		}
	}
}
