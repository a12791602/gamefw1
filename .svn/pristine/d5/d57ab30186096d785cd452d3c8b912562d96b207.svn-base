package com.mh.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mh.commons.orm.BaseDao;
import com.mh.entity.WebGdElectronic;
import com.mh.entity.WebNewNtElectronic;
import com.mh.entity.WebTtgElectronic;
@SuppressWarnings("all")
@Repository
public class WebTtgElectronicDao extends BaseDao {
	
	public void insertElectronic(Collection<WebTtgElectronic> entities){
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}
	
	public void insertGdElectronic(Collection<WebGdElectronic> entities){
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}
	
	public List<WebTtgElectronic> getTtgList(){
		String hql = " from WebTtgElectronic where 1 = 1";
		return this.getHibernateTemplate().find(hql);
	}
	
	public WebTtgElectronic findElectronic(String gameName){
		String hql = " from WebTtgElectronic where eleGameEnname = ? and status = 1";
		List<WebTtgElectronic> list = this.getHibernateTemplate().find(hql,new Object[]{gameName});
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void updateGdPic(String picName,int id){
		String sql = "update t_web_gd_electronic set ele_game_pic = '" + picName + "' where id = " + id;
		this.getJdbcTemplate().execute(sql);
	}
	
	public List<WebGdElectronic> getGdList(){
		String hql = " from WebGdElectronic where 1 = 1";
		return this.getHibernateTemplate().find(hql);
	}
	
	public void insertNntElectronic(Collection<WebNewNtElectronic> entities){
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}
}
