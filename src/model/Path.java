package model;

import java.util.ArrayList;

import control.Direction;


public class Path {
	private Tag tag;	
	
	/*On utilise des ArrayList (modifiables) pour les cases et directions utilis�es
	 * Pour un jeu un peu plus pouss�, qui permette de revenir en arri�re,
	 * il faudrait un mod�le de casesOccupees et directions plus complexe.
	 */
	
	private ArrayList<Cell> occupiedCells;
	
	//Constructeur
	public Path(Cell firstCell){	
		this.occupiedCells = new ArrayList<Cell>();
		occupiedCells.add(firstCell);
		this.tag = firstCell.getEnd().getTag();
	}
	
	//On regarde si la ligne est finie pour quantifier l'avancement dans le niveau 
	public boolean isComplete() {
		int size = occupiedCells.size();
		boolean boolean1 = occupiedCells.get(0) == tag.getEnds()[0] && occupiedCells.get(size) == tag.getEnds()[1];
		boolean boolean2 = occupiedCells.get(0) == tag.getEnds()[1] && occupiedCells.get(size) == tag.getEnds()[0];
		return boolean1 || boolean2; 
		
	}
	
	
	//pour r�pondre � la demande du contr�leur.
	public boolean moveDir(Direction dir) {
		Cell currentCell = occupiedCells.get(occupiedCells.size()-1);
		Cell nextCell = currentCell.getNextCell(dir);
				
		if (nextCell == null) {
			return false;
		} else {
			occupiedCells.add(nextCell);
			return true;
		}
	}
		
	public Cell getFinishingCell() {
		Cell firstCell = occupiedCells.get(0); //n�cessairement un end
		return firstCell.getOtherEndCell();
	}
		
	
	//Gestion des cases occup�es et directions utilis�es
	public void addCell(Cell newCell) {
		this.occupiedCells.add(newCell);
	}
	

	//"Nettoyage" de la ligne, si on la reprend de 0
	public void clear() {
		for (Cell cell : occupiedCells) {
			//On signale aux cases qu'on les vide
			cell.setPath(null);
		}
		occupiedCells.clear();	
	}
	
	
	
	//coupe le path � partir d'une cell (incluse)
	public void clearFrom(Cell cell) {
		//compte le nombre de cells strictement avant la cellule � partir de laquelle on veut clear
		int nbCellsBefore = 0;
		for (Cell otherCell : occupiedCells) {
			if (otherCell == cell) {
				break;
			}
			nbCellsBefore ++;
		}
		
		//puis on informe les cells de la suite du path qu'elles n'ont plus de path
		for (Cell cellToEmpty : new ArrayList<Cell>(occupiedCells.subList(nbCellsBefore, occupiedCells.size()))){
			cellToEmpty.setPath(null);
		}
		
		//et on modifie la liste de cellules du path
		this.occupiedCells = new ArrayList<Cell>(occupiedCells.subList(0, nbCellsBefore));
	}
	
	public void tagComplete() {
		tag.setIsComplete(true);
	}
	public ArrayList<Cell> getoccupiedCells() {
		return occupiedCells;
	}

	public Cell getFutureLastCell() {
		if (occupiedCells.get(0) == tag.getEnds()[0]) {
			return tag.getEnds()[1];				
		};
		return tag.getEnds()[0];
	}
	
	
	public Cell getLastCell() {
		return occupiedCells.get(occupiedCells.size()-1); 
	}
	
	public Cell getFirstCell() {
		return occupiedCells.get(0);
	}
	
	public void setStart(Cell cell) {
		this.clear(); 
		occupiedCells.set(0, cell);
		}

	public Tag getTag() {
		return tag;
	}
				
	
}
	