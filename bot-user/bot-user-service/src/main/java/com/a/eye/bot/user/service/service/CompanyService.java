package com.a.eye.bot.user.service.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.eye.bot.user.service.dao.CompanyMapper;

/**
 * @Title: CompanyService.java
 * @author: pengysh
 * @date 2016年8月14日 上午12:53:16
 * @Description:公司
 */
@Service
@Transactional
public class CompanyService {

	private static Logger logger = LogManager.getLogger(CompanyService.class.getName());

	private CompanyMapper companyMapper;
	
	
}
