package org.issh.web;

import java.util.Optional;

import javax.validation.Valid;

import org.issh.dao.ProduitRepository;
import org.issh.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitDao;
	
								/*Liste des produits sans pagination*/
	//@RequestMapping(value="/afficherProduits")
	//public String afficherProduits(Model model) {
		//List<Produit> produits = produitDao.findAll();
		//model.addAttribute("produits",produits);
		//return "listeProduits";
	//}
	
	
								/*Utiliser la pagination*/
		@RequestMapping(value="/afficherProduits")
		//public String afficherProduits(Model model) {
		//public String afficherProduits(Model model,int page, int size) {
		public String afficherProduits(Model model, @RequestParam(name="page", defaultValue="0")int p, 
				                                    @RequestParam(name="size", defaultValue="5")int s,
				                                    @RequestParam(name="motCle", defaultValue="")String mc) {
			
			//Page<Produit> pageProduits = produitDao.findAll(new PageRequest(0,5));
			//Page<Produit> pageProduits = produitDao.findAll(new PageRequest(page,size));//A récupérer à partir de la barre d'adresses (request.getParameter(""))
			Page<Produit> pageProduits = produitDao.findByDesignation("%"+mc+"%", new PageRequest(p,s));//A récupérer à partir de la barre d'adresses sinon les valeurs par défaut qui seront prises en considération
			
			model.addAttribute("produits",pageProduits.getContent());//stocker la liste de produits dans le model sous forme des pages
			
			int[] pages = new int[pageProduits.getTotalPages()];
			
			model.addAttribute("pages",pages);//stocker le tableau dont la taille égale au nombre total de pages dans le model
			model.addAttribute("size",s);
			model.addAttribute("pageCourante",p);
			model.addAttribute("motCle",mc);
			
			return "listeProduits";//forward
		}
		
		
		@RequestMapping(value="/supprimerProduits", method=RequestMethod.GET)
		public String supprimerProduits(Long id, int page, int size, String motCle) {
			produitDao.deleteById(id);
			return "redirect:/afficherProduits?page="+page+"&size="+size+"&motCle="+motCle;//redirection
		}
		
		@RequestMapping(value="/saisirProduit", method=RequestMethod.GET)
		public String saisirProduit(Model model) {
			model.addAttribute("produit",new Produit());
			return "formulaireProduit";//forward
		}
		
		@RequestMapping(value="/modifierProduit", method=RequestMethod.GET)
		public String modifierProduit(Model model,Long id) {
			Optional<Produit> prod = produitDao.findById(id);
			Produit p = prod.get();
			model.addAttribute("produit",p);
			return "formulaireModificationProduit";//forward
		}
		
		@RequestMapping(value="/ajouterProduit", method=RequestMethod.POST)
		public String ajouterProduit(Model model, @Valid Produit produit, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) 
				return "formulaireProduit";
			produitDao.save(produit);
			return "confirmationAjoutProduit";//forward
		}
		
		
		//Définir l'action par défaut
		@RequestMapping(value="/", method=RequestMethod.GET)
		public String accueil() {
			return "redirect:/afficherProduits";
		}
		
		@RequestMapping(value="/403", method=RequestMethod.GET)
		public String accessDenied() {
			return "403";
		}
}
