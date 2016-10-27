package com.dnk.smart.door.entity;

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
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	//	@Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private LocalDateTime createTime = LocalDateTime.now();
	//	@Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private LocalDateTime updateTime = LocalDateTime.now();

	@OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
//	@Expose
	private Set<Build> builds;
}
