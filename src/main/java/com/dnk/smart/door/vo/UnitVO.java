package com.dnk.smart.door.vo;

import com.dnk.smart.door.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitVO {

	private Long id;

	private String name;

	private LocalDateTime createTime = LocalDateTime.now();

	private LocalDateTime updateTime = LocalDateTime.now();

	private Long buildId;

	private String buildName;

	public UnitVO(Unit unit, Long buildId, String buildName) {
		this.id = unit.getId();
		this.name = unit.getName();
		this.createTime = unit.getCreateTime();
		this.updateTime = unit.getUpdateTime();
		this.buildId = buildId;
		this.buildName = buildName;
	}
}
