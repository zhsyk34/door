package com.dnk.smart.door.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//@Embeddable
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public class Times {
	@Column(updatable = false)
	private LocalDateTime createTime = LocalDateTime.now();

	private LocalDateTime updateTime = LocalDateTime.now();
}
