package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Project;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.Sort;

import java.time.LocalDate;
import java.util.List;

public interface ProjectDao extends CommonDao<Project, Long> {

	Project find(String name);

	List<Project> findList(String name, LocalDate begin, LocalDate end, Page page, Sort sort);

	long count(String name, LocalDate begin, LocalDate end);

}
