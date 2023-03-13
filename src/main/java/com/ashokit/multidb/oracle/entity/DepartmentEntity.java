package com.ashokit.multidb.oracle.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="DEPT")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {
	
	@Id
	private Integer deptno;
	
	private String dname;
	
	private String loc;
}
