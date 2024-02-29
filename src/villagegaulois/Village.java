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
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = new Etal();
			etal.occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) if(!etals[i].isEtalOccupe()) return i;
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalproduit = new Etal[10];
			int nbProduitTrouver=0;
			for (int i = 0; i < etals.length && nbProduitTrouver<10; i++) if(etals[i].contientProduit(produit)) etalproduit[i]=etals[i];
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
			if(nbEtalVide>0) affichage = affichage + "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
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
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}