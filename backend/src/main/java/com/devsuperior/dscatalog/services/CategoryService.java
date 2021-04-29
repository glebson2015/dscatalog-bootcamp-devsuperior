package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

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
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
		
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		
		try {
		Category entity = repository.getOne(id);//Método getOne não toca no BD - Instancia um Objeto provisório e só ao salvar que de fato acessará o BD para atualizar
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+ id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		} 
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
