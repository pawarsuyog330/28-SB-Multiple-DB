package com.ashokit.multidb.mysql.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EMP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

	@Id
	@NotNull
	private Integer empno;
	
	@NotNull
	private String ename;
	
	@NotNull
	private Double sal;
	
	@NotNull
	private Integer deptno;
}
