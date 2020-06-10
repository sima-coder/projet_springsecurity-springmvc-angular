package org.issh.dao;

import org.issh.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produit,Long>{
	
	@Query("SELECT p FROM Produit p WHERE p.designation LIKE :x")
	public Page<Produit> findByDesignation(@Param("x")String mc,Pageable pageable);

}
