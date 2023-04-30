package com.employeedirectory.rest.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private String imageName;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ExceptionUtility exceptionUtility;


	@GetMapping("/employees")
	public List<Employee> listAll() {

		return employeeService.findAll();

	}

	@GetMapping("/employees/find/pageNum={pageNum}&sortField={sortField}&sortDir={sortDir}&keyword={keyword}")
	public List<Employee> findEmployees(@PathVariable int pageNum, @PathVariable String sortField, @PathVariable String sortDir,
										@PathVariable String keyword) {

		Page<Employee> page = employeeService.findPage(pageNum, sortField, sortDir, keyword);

		List<Employee> employees = page.getContent();

		return employees;
	}

	@GetMapping("/employees/search/pageNum={pageNum}&keyword={keyword}")
	public List<Employee> findEmployees(@PathVariable int pageNum, @PathVariable String keyword) {

		Page<Employee> page = employeeService.findPage(pageNum, keyword);

		List<Employee> employees = page.getContent();

		return employees;

	}

	@GetMapping("/employees/get/employeeId={employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {

		exceptionUtility.employeeNotFound(employeeId);

		Employee employee = employeeService.findById(employeeId);

		return employee;

	}

	@PostMapping("/employees/save")
	public ResponseEntity<String> save(@RequestBody Employee employee) {

		List<Employee> employees = employeeService.findAll();

		for (Employee emp : employees) {
			if (employee.getEmail().equals(emp.getEmail())) {
				return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
			}
		}

		employeeService.save(employee);

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}


	@PostMapping("/employees/uploadImage/employeeId={employeeId}")
	public ResponseEntity<String> saveEmployee(@PathVariable int employeeId, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

		exceptionUtility.employeeNotFound(employeeId);

		String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));


		Employee employee = employeeService.findById(employeeId);

		// when updating an employee, but not doing anything to the image, leave the previously saved image as the profile picture

		if (!fileName.isEmpty()) {
			employee.setImage(fileName);
		} else {
			employee.setImage(imageName);
		}

		employeeService.save(employee);

		String uploadDir = "./employee-images/" + employeeId;

		Path uploadPath = Paths.get(uploadDir);


		if (!fileName.isEmpty()) {

			// checks to see if the path is a directory. If it is, it removes its contents

			if (Files.isDirectory(uploadPath)) {
				FileUtils.cleanDirectory(new File(uploadDir));
			} else {

				// otherwise, deletes the empty file and creates a directory

				Files.deleteIfExists(uploadPath);
				FileUtils.forceMkdir(new File(uploadDir));
			}


			InputStream inputStream = multipartFile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);


			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);

	}


	@PutMapping("/employees/update/employeeId={employeeId}")
	public ResponseEntity<String> update(@PathVariable int employeeId, @RequestBody Employee employee) {

		exceptionUtility.employeeNotFound(employeeId);

		Employee newEmployee = employeeService.findById(employeeId);

		newEmployee.setFirstName(employee.getFirstName());
		newEmployee.setLastName(employee.getLastName());
		newEmployee.setEmail(employee.getEmail());

		employeeService.save(newEmployee);

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}


	@DeleteMapping("/employees/delete/employeeId={employeeId}")
	public ResponseEntity<String> delete(@PathVariable int employeeId) throws IOException {

		exceptionUtility.employeeNotFound(employeeId);

		employeeService.delete(employeeId);

		// Your full path to the image folder
		String filePath = "C:\\your\\full\\path\\employee-directory-rest\\employee-images\\" + employeeId;

		Path path = Paths.get(filePath);

		if (Files.isDirectory(path)) {
			FileUtils.deleteDirectory(new File(filePath));
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);

	}


}
