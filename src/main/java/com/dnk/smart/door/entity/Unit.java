package com.dnk.smart.door.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class Unit {
	private Long id;
	private String name;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	private House house;
	private Set<House> houses;
}
