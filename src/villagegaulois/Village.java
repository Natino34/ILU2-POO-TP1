package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for(int i=0; i<nbEtal; i++) {
				Etal etal= new Etal();
				etals[i]=etal;
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = new Etal();
			etals[indiceEtal]=etal;
			etal.occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if(!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbProduitTrouver=0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbProduitTrouver++;
				}
			}
			Etal[] etalproduit = new Etal[nbProduitTrouver];
			nbProduitTrouver=0;
			for(int i=0; i<etals.length;i++)
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalproduit[nbProduitTrouver]=etals[i];
					nbProduitTrouver++;
				}
			return etalproduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) if(etals[i].getVendeur().equals(gaulois) && etals[i].isEtalOccupe()) return etals[i];
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide=0;
			String affichage="";
			for (int i = 0; i < etals.length; i++){
				if(etals[i].isEtalOccupe()) affichage = affichage + etals[i].afficherEtal();
				else nbEtalVide++;
			}
			if(nbEtalVide>0) affichage = affichage + "Il reste " + nbEtalVide + " �tals non utilis�s dans le march�.\n";
			return affichage;
		}
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit +".\n");
		int indiceEtal=marche.trouverEtalLibre();
		if(indiceEtal==-1) {
			chaine.append("Mais Malheureusement il n'y a plus d'�tal disponnible.");
		}
		else {
			marche.utiliserEtal( indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur "+ vendeur.getNom() + " vend des " + produit + " � l'�tal n�" + indiceEtal);
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = new Etal[10];
		etals=marche.trouverEtals(produit);
		if(etals.length==0)
		{
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au march�.");
		}
		else if (etals.length==1)
		{
			chaine.append("Seul le vendeur " + (etals[0].getVendeur()).getNom() +" vend des " + produit +" au march�.");
		}
		else
		{
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(int i=0; i<etals.length; i++) {
				chaine.append("- "+(etals[i].getVendeur()).getNom() +"\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
}