/**   
* 文件名称: WebWeiHuServiceImpl.java<br/>
* 版本号: V1.0<br/>   
* 创建人: alex<br/>  
* 创建时间 : 2016-12-3 上午10:52:20<br/>
*/  
package com.mh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.dao.WebWeihuDao;
import com.mh.entity.WebWeiHu;
import com.mh.service.WebWeiHuService;

/** 
 * 类描述: TODO<br/>
 * 创建人: TODO alex<br/>
 * 创建时间: 2016-12-3 上午10:52:20<br/>
 */
@Service
public class WebWeiHuServiceImpl implements WebWeiHuService{

	
	@Autowired
	private WebWeihuDao webWeihuDao;
	
	

	/***
	 * 根据平台表示查询维护信息
	 * 方法描述: TODO</br> 
	 * @param flatName
	 * @return  
	 * WebWeiHu
	 */
	public WebWeiHu getWebWeiHuByFlatName(String flatName){
		return this.webWeihuDao.getWebWeiHuByFlatName(flatName);
	}
	
}
