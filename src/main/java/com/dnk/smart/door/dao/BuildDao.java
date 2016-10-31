package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;

import java.util.List;

public interface BuildDao extends CommonDao<Build, Long> {

    Build find(String name);

    List<Build> findList(String name, Page page, Sort sort);

    long count(String name);
}
