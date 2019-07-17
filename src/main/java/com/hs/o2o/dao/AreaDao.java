package com.hs.o2o.dao;

import com.hs.o2o.entity.Area;

import java.util.List;

/**
 * author heshang.ink
 */
public interface AreaDao {
	/**
	 * 列出区域列表
	 * @return
	 */
	List<Area> queryArea();
}
