package com.dnk.smart.door.entity;

import com.dnk.smart.door.entity.dict.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class User extends Times {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	private String name;

	@Convert(converter = Gender.GenderConverter.class)
	private Gender gender = Gender.MALE;

}
