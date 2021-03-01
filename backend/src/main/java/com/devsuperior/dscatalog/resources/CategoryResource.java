package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value =  "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	//Objeto do Spring que encapsula uma Resposta Http
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	

}
