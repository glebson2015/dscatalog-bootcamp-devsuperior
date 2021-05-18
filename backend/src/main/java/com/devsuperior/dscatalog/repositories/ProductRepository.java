package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;

//Camada de Acesso a Dados
//Com a anotation @Repository os objetos passam a ser gerenciados pelo Spring
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
