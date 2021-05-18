package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//Camada de Serviço
//A anotation @Service regitra a classe como um componente que participará do sistema de injeção de dependencias automático do Spring
//Ou seja, o Spring irá gerencia o Service
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository; //Dependencia
	
	@Transactional(readOnly = true) // Não bloqueia o BD apenas por fazer uma leitura // Inserido para transações de leitura
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		//Lista de Categoria
		Page<Product> list =  repository.findAll(pageRequest);
		
		return list.map(x -> new ProductDTO(x));
		//Expressão LAMBDA - Conversão da Lista de Categoria para CategoriaDTO
		//return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		//Conversão da Lista de Categoria para CategoriaDTO - A expressão a cima substitui o for abaixo
		/*List<ProductDTO> listDto = new ArrayList<>();		
		for(Product cat : list) {
			listDto.add(new ProductDTO(cat));
		}
		return listDto;*/
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());//Retorna a entidade e a lista de categorias
		
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try {
		Product entity = repository.getOne(id);//Método getOne não toca no BD - Instancia um Objeto provisório e só ao salvar que de fato acessará o BD para atualizar
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
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
