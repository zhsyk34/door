package com.dnk.smart.door.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Build {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createTime = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime updateTime = LocalDateTime.now();

	@OneToMany(mappedBy = "build", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Set<Unit> units;

	@OneToMany(mappedBy = "build", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<UserBuild> userBuilds;
}
