package model;

import java.util.ArrayList;

import control.Direction;

/**
 * @author Noé Bopp
 * @author Benjamin Rio
 */
public class Path {
	private Tag tag;	
	private ArrayList<Cell> occupiedCells;
	

	public Path(Cell firstCell){	
		this.occupiedCells = new ArrayList<Cell>();
		occupiedCells.add(firstCell);
		this.tag = firstCell.getEnd().getTag();
	}
	

	/**
	 * Méthode répondant à la demande du controlleur.
	 * Cherche à étendre le path dans la direction, retournant
	 * si le tag de ce path est complet après ajout de la case 
	 * suivante selon la direction donnée (si elle existe)
	 * @param dir une Direction
	 * @return vrai si le tag associé est complet, faux sinon
	 */
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
		

	/**
	 * Méthode retournant la cellule qui permet de finir le path
	 * l'extrémité du tag associé au path qui n'est pas l'extrémité
	 * qui commence la path
	 * @return
	 */
	public Cell getFinishingCell() {
		Cell firstCell = occupiedCells.get(0);
		return firstCell.getOtherEndCell();
	}
		
	
	/**
	 * Méthode ajoutant une cell au path
	 * @param newCell la cell à ajouter
	 */
	public void addCell(Cell newCell) {
		newCell.setPath(this);
		this.occupiedCells.add(newCell);
	}
	

	/**
	 * Méthode vidant l'entiereté du path
	 */
	public void clear() {
		for (Cell cell : occupiedCells) {
			//On signale aux cases qu'on les vide
			cell.setPath(null);
		}
		occupiedCells.clear();	
		tag.setIsComplete(false);
	}
	
	
	
	
	/**
	 * Méthode qui vide le path à partir d'une cell
	 * du path. 
	 * @param cell
	 */
	public void clearFrom(Cell cell) {
		//compte le nombre de cells strictement avant la cell à partir de laquelle le path doit etre vidé
		int nbCellsBefore = 0;
		for (Cell otherCell : occupiedCells) {
			if (otherCell == cell) {
				break;
			}	
			nbCellsBefore ++;
		}
		
		//puis informe les cells de la suite du path qu'elles n'ont plus de path
		for (Cell cellToEmpty : new ArrayList<Cell>(occupiedCells.subList(nbCellsBefore, occupiedCells.size()))){
			cellToEmpty.setPath(null);
		}
		
		//et enfin, modifie la liste de cellules du path
		this.occupiedCells = new ArrayList<Cell>(occupiedCells.subList(0, nbCellsBefore));
		setTagIsComplete(false);
	}
	
	
	/**
	 * Méthode indiquant au tag qu'il est complet
	 * @param bool
	 */
	public void setTagIsComplete(boolean bool) {
		tag.setIsComplete(bool);
	}
		
	
	/**
	 * Méthode récupérant la première cell
	 * du path
	 * @return
	 */
	public Cell getFirstCell() {
		return occupiedCells.get(0);
	}
	
	
	/**
	 * Méthode appelée par le controller pour obtenir la liste des directions
	 * à suivre pour tracer le chemin à partir de la position de la cell
	 * constituant le début du path.
	 * @param pos La position sur la grille d'une cell.
	 * @return La liste des directions du path associé.
	 */
	public Direction[] getDirections() {
		if (occupiedCells.size() == 0) {return null;}
		Direction[] directions = new Direction[occupiedCells.size() - 1];
		for (int k = 0; k < occupiedCells.size() -  1; k++) {
			int j0 = occupiedCells.get(k).getPos()[1];
			int i0 = occupiedCells.get(k).getPos()[0];
			int j1 = occupiedCells.get(k+1).getPos()[1];
			int i1 = occupiedCells.get(k+1).getPos()[0];
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
	
	/**
	 * Méthode appelée par une celulle afin
	 * de savoir si son path est complet
	 * @return true si le tag associé au path associé à la cell est complet, false sinon
	 */
	public boolean getIsComplete() {
		return tag.getIsComplete();
	}
	
}
	