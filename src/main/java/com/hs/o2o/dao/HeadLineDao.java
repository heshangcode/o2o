package com.hs.o2o.dao;

import com.hs.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * author heshang.ink
 */
public interface HeadLineDao {
	/**
	 * 根据传入的查询条件(头条名查询头条)
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
