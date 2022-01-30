package model;

import java.util.ArrayList;

import control.Direction;

public class Path {
	private Tag tag;	
	
	/*On utilise des ArrayList (modifiables) pour les cases et directions utilisées
	 * Pour un jeu un peu plus poussé, qui permette de revenir en arrière,
	 * il faudrait un modèle de casesOccupees et directions plus complexe.
	 */
	
	private ArrayList<Cell> occupiedCells;
	
	//Constructeur
	public Path(Cell firstCell){	
		this.occupiedCells = new ArrayList<Cell>();
		occupiedCells.add(firstCell);
		this.tag = firstCell.getEnd().getTag();
	}
	/*
	//On regarde si la ligne est finie pour quantifier l'avancement dans le niveau 
	public boolean isComplete() {
		int size = occupiedCells.size();
		boolean boolean1 = occupiedCells.get(0) == tag.getEnds()[0] && occupiedCells.get(size) == tag.getEnds()[1];
		boolean boolean2 = occupiedCells.get(0) == tag.getEnds()[1] && occupiedCells.get(size) == tag.getEnds()[0];
		return boolean1 || boolean2; 
		
	}
*/	
	
	//pour répondre à la demande du contrôleur.
	public boolean moveDir(Direction dir) {
		if (!tag.getIsComplete()){
			Cell currentCell = occupiedCells.get(occupiedCells.size()-1);
			Cell nextCell = currentCell.getNextCell(dir);
			if (nextCell != null) {
				return nextCell.isTagComplete();
			}
		}
		return false;
	}
		

	public Cell getFinishingCell() {
		//trouver la cell qui permet de finir le path
		Cell firstCell = occupiedCells.get(0); //nécessairement un end
		return firstCell.getOtherEndCell();
	}
		
	
	public void addCell(Cell newCell) {
		newCell.setPath(this);
		this.occupiedCells.add(newCell);
	}
	

	//Clear l'entiéreré du path
	public void clear() {
		for (Cell cell : occupiedCells) {
			//On signale aux cases qu'on les vide
			cell.setPath(null);
		}
		occupiedCells.clear();	
		tag.setIsComplete(false);
	}
	
	
	
	//coupe le path à partir d'une cell (incluse)
	public void clearFrom(Cell cell) {
		//compte le nombre de cells strictement avant la cellule à partir de laquelle on veut clear
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
		tag.setIsComplete(false);
	}
	
	
	public void tagIsComplete(boolean bool) {
		tag.setIsComplete(bool);
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
	
	public Direction[] getDirections() {
		//transforme la liste de cells du path en une liste de direction
		if (occupiedCells.size() == 0) {return null;}
		Direction[] directions = new Direction[occupiedCells.size() - 1];
		System.out.println("Occupied cells: "+ occupiedCells);
		for (int k = 0; k < occupiedCells.size() -  1; k++) {
			int j0 = occupiedCells.get(k).getPos()[1];
			int i0 = occupiedCells.get(k).getPos()[0];
			int j1 = occupiedCells.get(k+1).getPos()[1];
			int i1 = occupiedCells.get(k+1).getPos()[0];
			// Compliqué de faire un case ici, on trouve cela plus simple de faire des if. 
			if (j1-j0 == 1) {
				directions[k] = Direction.RIGHT;
			}
			if (j1-j0 == -1) {
				directions[k] = Direction.LEFT; 	
			}
			if (i1-i0 == -1) {
				directions[k] = Direction.UP;
			}
			if (i1-i0 == 1) {
				directions[k] = Direction.DOWN; 
			}
		}
		return directions;	
		
	}
	
	public boolean getIsComplete() {
		return tag.getIsComplete();
	}
	
}
	