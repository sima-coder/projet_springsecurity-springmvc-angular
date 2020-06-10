package org.issh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import org.issh.dao.ProduitRepository;
import org.issh.entities.Produit;

@SpringBootApplication
public class Tp4CatMvcApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Tp4CatMvcApplication.class, args);
		
		ProduitRepository produit = ctx.getBean(ProduitRepository.class);
		
		produit.save(new Produit("LX 4352",670,90));
		produit.save(new Produit("Ord HP 432",800,50));
		produit.save(new Produit("Imp Epson",450,12));
		produit.save(new Produit("Imp HP 5400",100,10));
		
		//List<Produit> produits = produit.findAll();
		//for(Produit p : produits) {
		//	System.out.println(p.getDesignation());
		//}
		
		produit.findAll().forEach(p->System.out.println(p.getDesignation()));

	}

}
