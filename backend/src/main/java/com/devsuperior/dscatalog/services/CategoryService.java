package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

//Camada de Serviço
//A anotation @Service regitra a classe como um componente que participará do sistema de injeção de dependencias automático do Spring
//Ou seja, o Spring irá gerencia o Service
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository; //Dependencia
	
	public List<Category> findAll(){
		return repository.findAll();
	}

}
