package control;

import java.util.ArrayList;

import model.Cell;
import model.Grid;
import model.Path;
import model.Tag;


public class Controller{
	
	private Grid grid;
	private Path currentPath;
	private int nbLines = 5, nbCols = 5;
	
	public Controller(){
		this.myGrid = new Grid(nbLines, nbCols);
	}
	
	public int[] selectCell(int line, int col) {
		grid.startPath(line, col);
		
		currentPath = clickedCell.getPath();
		currentPath.clearPath();
		
		
		else currentPath = null;
		
		System.out.println(ligneCourante);
		return new int[] {ligne, colonne};
	}

	@Override
	public boolean action(Direction direction) {
		if (currentPath != null) {
			System.out.println("flèche" + direction.name());
			System.out.println("Ligne courante : " + currentPath.getTag());
			currentPath.addCellToPath(direction);
		}
		
		//Le booléen res sert à évaluer si le tableau est complet ou non
		int nbLignesFinies = 0;
		for (Couleur couleur : Couleur.class.getEnumConstants()) {
			if (couleur.getLigne().estFinie()) ++nbLignesFinies;
		};
		if(nbLignesFinies==5) {
			return true;
		}
		return false;
	}

	@Override
	public int getNbLignes() {
		return this.monPlateau.getNbLignes();
	}

	@Override
	public int getNbColonnes() {
		return this.monPlateau.getNbColonnes();
	}

	@Override
	public int[] getPositionExtremiteDepart(Couleur couleur) {
		return couleur.getLigne().getExtremiteDepart().getMaPositionExtremite();
	}

	@Override
	public int[] getPositionExtremiteArrivee(Couleur couleur) {
		return couleur.getLigne().getExtremiteArrivee().getMaPositionExtremite();
	}

	@Override
	public ArrayList<Direction> getDirections(Couleur couleur) {
		return couleur.getDirections();
	}
}
