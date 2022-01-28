package model;
import model.Cell;
import control.Controller;
import control.Direction;
public class Grid {
	private int nbLines;
	private int nbCols;
	private Cell[][] cells;
	private Controller controller;
	
	
	public Grid(int nbLines, int nbCols) {
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		int i, j;
		for (i=0; i < nbLines; i++) {
			for (j=0; j < nbCols; j++) {
				this.cells[i][j] = new Cell(this);
			}
		}
	}
	
	
	//commence ou continue un path à partir du click, si cela est possible
	//retourne le current path si c'est possible (selection d'un end ou click sur une case avec un path)
	public Path startPath(int i, int j) {
		//check si i et j sont bien dans les dims de la grille
		Cell selectedCell = cells[i][j];
		Path path = selectedCell.startPathAtCell();
		return path;
	}
	
	//cherche la cellule dans la grille
	public int[] getPosition(Cell cell) {
		for (int i = 0; i < nbLines; i++ ){
			for (int j = 0; j<nbCols; j++) {
				if (cells[i][j] == cell) {
					return new int[] {i,j};
				}
			}
		}
		throw new RuntimeException("Cell not in grid");
	}
	
	
	public Cell getNeighbourCell(Cell cell, Direction direction) {
		int[] coord = getPosition(cell);
		int i = coord[0], j = coord[1];
		switch(direction) {
			case UP : i=i-1; break;
			case DOWN : i=i+1; break;
			case RIGHT : j=j+1; break;
			case LEFT : j=j-1; break;
			default : break;
		}
		Cell nextCell = cells[i][j];
		//vérification de l'intégrité géométrique (dans la grille)
		if (0<=i && i<nbLines && 0<=j && j<nbCols) {
			//vérification de l'intégrité sémantique (pas une end "interdite")
			if (nextCell.isAllowedFrom(cell)) {
				return nextCell;
			} 
		}
		return null;
	}
	
	
	
	public Cell getCellAt(int i, int j) {
		return cells[i][j];
	}

	public int getNbLines() {
		return nbLines;
	}
	
	public void setNbLines(int nbLines) {
		this.nbLines = nbLines;
	}

	public int getNbCols() {
		return nbCols;
	}

	public void setNbCols(int nbCols) {
		this.nbCols = nbCols;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	
}
