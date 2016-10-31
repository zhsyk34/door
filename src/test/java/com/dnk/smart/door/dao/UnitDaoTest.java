package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import org.junit.Test;

import java.util.List;

public class UnitDaoTest extends CommonDaoTest {
    @Test
    public void findList() throws Exception {
        List<Unit> list = unitDao.findList();
        list.forEach(super::print);
    }

    @Test
    public void findList2() throws Exception {
        List<Unit> list = unitDao.findList(2L, "u", Page.of(2, 5), Sort.of("name", Sort.Rule.DESC));
        list.forEach(super::print);
    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void findMap() throws Exception {

    }

}