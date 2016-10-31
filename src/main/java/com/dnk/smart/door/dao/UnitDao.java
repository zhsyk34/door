package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;

import java.util.List;
import java.util.Map;

public interface UnitDao extends CommonDao<Unit, Long> {

    List<Unit> findList(Long buildId, String name, Page page, Sort sort);

    long count(Long buildId, String name);

    List<Map> findMap(Long buildId, String name, Page page, Sort sort);
}
