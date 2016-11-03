package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.vo.UnitVO;

import javax.persistence.Tuple;
import java.util.Collection;
import java.util.List;

public interface UnitDao extends CommonDao<Unit, Long> {

	List<Unit> findList(Long buildId, String name);

	List<Unit> findList(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort);

	long count(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName);

	List<Tuple> findTuple(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort);

	List<UnitVO> findVOList(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort);

	List<UnitVO> findVOList2(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort);

}
