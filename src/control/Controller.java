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
		this.grid = new Grid(nbLines, nbCols);
	}
	
	
	//selection de la case pour initier un path avec la souris, et affecte le current path au path sélectioné si il y en a un
	public void selectCell(int line, int col) {
		this.currentPath = grid.startPath(line, col);
	}


	public boolean action(Direction dir) {
		if (currentPath != null) {
			System.out.println("Flèche" + dir.name());
			System.out.println("Ligne courante : " + currentPath.getTag());
			return currentPath.moveDir(dir);
		}
		return false;
	}
	//si jamais l'action est vraie, l'état du modèle a changé. Sinon aucune modif à effectuer
}
