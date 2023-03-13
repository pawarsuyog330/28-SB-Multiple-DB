package com.ashokit.multidb.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.multidb.mysql.entity.EmployeeEntity;
import com.ashokit.multidb.mysql.repository.EmployeeEntityRepository;
import com.ashokit.multidb.oracle.entity.DepartmentEntity;
import com.ashokit.multidb.oracle.repository.DepartmentEntityRepository;

@RestController
@RequestMapping("/api")
public class MultiDBApi {

	@Autowired
	EmployeeEntityRepository empRepo;
	
	@Autowired
	DepartmentEntityRepository deptRepo;
	
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeEntity>> getEmployees()
	{
		List<EmployeeEntity> lstEmployees=empRepo.findAll();
		return ResponseEntity.ok(lstEmployees);
	}
	
	@GetMapping("/departments")
	public ResponseEntity<List<DepartmentEntity>> getDepartments()
	{
		List<DepartmentEntity> lstDepartments=deptRepo.findAll();
		return ResponseEntity.ok(lstDepartments);
	}
	
	@PostMapping("/add/employee")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeEntity empEntity, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ResponseEntity<String>("Invalid JSON input", HttpStatus.BAD_REQUEST);
		}
		else
		{
			if(!empRepo.existsById(empEntity.getEmpno()))
			{
				empRepo.save(empEntity);
				return new ResponseEntity<String>("Employee is added to the database", HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("Employee is already exists in the database", HttpStatus.OK);
			}
		}
	}
	
	@PostMapping("/add/department")
	public ResponseEntity<String> addDepartment(@Valid @RequestBody DepartmentEntity deptEntity, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ResponseEntity<String>("Invalid JSON input", HttpStatus.BAD_REQUEST);
		}
		else
		{
			if(!deptRepo.existsById(deptEntity.getDeptno()))
			{
				deptRepo.save(deptEntity);
				return new ResponseEntity<String>("Department is added to the database", HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("Department is already exists in the database", HttpStatus.OK);
			}
		}
	}
}
