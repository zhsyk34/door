package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UnitDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@Repository
public class UnitDaoImpl extends CommonDaoImpl<Unit, Long> implements UnitDao {
    @Override
    public List<Unit> findList(Long buildId, String name, Page page, Sort sort) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Unit> query = builder.createQuery(Unit.class);
        Root<Unit> unit = query.from(Unit.class);

        Join<Build, Unit> build = unit.join("build");
        Path<Build> path = build.get("id");
        if (ValidateKit.valid(buildId)) {
            System.err.println(">>>>>>>>>>>join");
            System.out.println(buildId);
            query.where(builder.equal(path, buildId));
        }
        if (ValidateKit.notEmpty(name)) {
            query.where(builder.like(unit.get("name"), "%" + name + "%"));
        }
        System.out.println(query.getSelection());
        super.order(query, builder, unit, sort);

        return super.page(manager.createQuery(query), page).getResultList();
    }

    @Override
    public long count(Long buildId, String name) {
        return 0;
    }

    @Override
    public List<Map> findMap(Long buildId, String name, Page page, Sort sort) {
        return null;
    }
}
