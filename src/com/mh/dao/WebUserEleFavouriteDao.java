package com.mh.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mh.commons.orm.BaseDao;
import com.mh.entity.TWebUserEleFavourite;
@Repository
public class WebUserEleFavouriteDao extends BaseDao {
	
	public int getFavouriteCount(TWebUserEleFavourite eleFavourite){
		String sql = "select count(1) from t_web_user_ele_favourite where user_name = ? and flat = ? and game_code = ? and client = ?";
		return getJdbcTemplate().queryForInt(sql, new Object[]{eleFavourite.getUserName(),eleFavourite.getFlat(),eleFavourite.getGameCode(),eleFavourite.getClient()});
	}
	
	public int cancelEleFavourite(TWebUserEleFavourite eleFavourite){
		String sql = "delete from t_web_user_ele_favourite where user_name = ? and flat = ? and game_code = ? and client = ?";
		return getJdbcTemplate().update(sql, new Object[]{eleFavourite.getUserName(),eleFavourite.getFlat(),eleFavourite.getGameCode(),eleFavourite.getClient()});
	}
	
	public List<TWebUserEleFavourite> getFavouriteByUser(TWebUserEleFavourite eleFavourite){
		String hql = " from TWebUserEleFavourite where userName = ? and flat = ? and client = ?";
		List<TWebUserEleFavourite> favouritList = getHibernateTemplate().find(hql, new Object[]{eleFavourite.getUserName(),eleFavourite.getFlat(),eleFavourite.getClient()});
		return favouritList;
	}
}
