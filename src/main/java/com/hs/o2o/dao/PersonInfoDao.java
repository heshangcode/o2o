package com.hs.o2o.dao;

import com.hs.o2o.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;
/**
 * author heshang.ink
 */
public interface PersonInfoDao {
	/**
	 * 登录使用，验证数据库是否有此账号
	 * @param account
	 * @param password
	 * @return
	 */
	PersonInfo getPersonInfo(@Param("account") String account, @Param("password") String password);
}
