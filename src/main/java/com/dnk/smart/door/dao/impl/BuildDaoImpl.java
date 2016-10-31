package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.BuildDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BuildDaoImpl extends CommonDaoImpl<Build, Long> implements BuildDao {
    @Override
    public Build find(String name) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Build> query = builder.createQuery(Build.class);
        query.where(builder.equal(query.from(Build.class).get("name"), name));

        return manager.createQuery(query).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Build> findList(String name, Page page, Sort sort) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Build> query = builder.createQuery(Build.class);
        Root<Build> root = query.from(Build.class);
        query.where(builder.like(root.get("name"), "%" + name + "%"));
        super.order(query, builder, root, sort);

        return super.page(manager.createQuery(query), page).getResultList();
    }

    @Override
    public long count(String name) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(builder.literal(1)));
        query.where(builder.like(query.from(Build.class).get("name"), "%" + name + "%"));

        return manager.createQuery(query).getSingleResult();
    }
}
