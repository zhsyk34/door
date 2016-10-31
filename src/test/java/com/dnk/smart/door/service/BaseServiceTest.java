package com.dnk.smart.door.service;

import com.dnk.smart.door.dao.CommonDaoTest;
import org.junit.Test;

import javax.annotation.Resource;

public class BaseServiceTest extends CommonDaoTest {

    @Resource
    private BaseService baseService;

    @Test
    public void init() throws Exception {
        baseService.init();
    }

    @Test
    public void init2() throws Exception {
        baseService.init2();
    }

}