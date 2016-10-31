package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UserDao;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.UserBuild;
import com.dnk.smart.door.entity.dict.Gender;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User, Long> implements UserDao {
    @Override
    public User find(String name, String password) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.where(builder.equal(root.get("name"), name), builder.equal(root.get("password"), password)).select(root);
        return manager.createQuery(query).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<User> findList(String name, Gender gender, LocalDate begin, LocalDate end, Page page, Sort sort) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(condition(builder, root, name, gender, begin, end));
        super.order(query, builder, root, sort);

        return super.page(manager.createQuery(query), page).getResultList();
    }

    @Override
    public long count(String name, Gender gender, LocalDate begin, LocalDate end) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        query.select(builder.count(root));
        query.where(condition(builder, root, name, gender, begin, end));

        return manager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Long> findBuilds(Long id) {
        EntityManager manager = super.manager;
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UserBuild> root = query.from(UserBuild.class);
        root.join("user");
        query.select(root.get("build").get("id"));
        query.where(builder.equal(root.get("user").get("id"), id));
        return manager.createQuery(query).getResultList();
    }

    private Predicate[] condition(CriteriaBuilder builder, Root<User> root, String name, Gender gender, LocalDate begin, LocalDate end) {
        List<Predicate> predicates = new ArrayList<>();
        //SimpleExpression simpleExpression = Restrictions.like("name", name, MatchMode.ANYWHERE);
        if (name != null && !name.isEmpty()) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }
        if (gender != null) {
            predicates.add(builder.equal(root.get("gender"), gender));
        }
        Path<LocalDateTime> createTime = root.get("createTime");
        if (begin != null) {
            predicates.add(builder.greaterThanOrEqualTo(createTime, LocalDateTime.of(begin, LocalTime.MIN)));
        }
        if (end != null) {
            predicates.add(builder.lessThanOrEqualTo(createTime, LocalDateTime.of(end, LocalTime.MIN)));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
