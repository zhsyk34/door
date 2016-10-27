package com.dnk.smart.door.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class House {
	private Long id;
	private String name;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	private Unit unit;
}
