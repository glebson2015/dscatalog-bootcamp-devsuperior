package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

//Camada de Serviço
//A anotation @Service regitra a classe como um componente que participará do sistema de injeção de dependencias automático do Spring
//Ou seja, o Spring irá gerencia o Service
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository; //Dependencia
	
	@Transactional(readOnly = true) // Não bloqueia o BD apenas por fazer uma leitura // Inserido para transações de leitura
	public List<CategoryDTO> findAll(){
		//Lista de Categoria
		List<Category> list =  repository.findAll();
		
		
		//Expressão LAMBDA - Conversão da Lista de Categoria para CategoriaDTO
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		//Conversão da Lista de Categoria para CategoriaDTO - A expressão a cima substitui o for abaixo
		/*List<CategoryDTO> listDto = new ArrayList<>();		
		for(Category cat : list) {
			listDto.add(new CategoryDTO(cat));
		}
		return listDto;*/
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
		
	}

}
