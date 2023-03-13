package com.ashokit.multidb.oracle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashokit.multidb.oracle.entity.DepartmentEntity;

@Repository
public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, Integer> {

}
