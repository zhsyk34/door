package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.ProjectDao;
import com.dnk.smart.door.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoImpl extends CommonDaoImpl<Project, Long> implements ProjectDao {
}
