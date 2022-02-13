package model;

import java.util.ArrayList;

import control.Direction;

/**
 * @author No� Bopp
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
	 * M�thode r�pondant � la demande du controlleur.
	 * Cherche � �tendre le path dans la direction, retournant
	 * si le tag de ce path est complet apr�s ajout de la case 
	 * suivante selon la direction donn�e (si elle existe)
	 * @param dir une Direction
	 * @return vrai si le tag associ� est complet, faux sinon
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
	 * M�thode retournant la cellule qui permet de finir le path
	 * l'extr�mit� du tag associ� au path qui n'est pas l'extr�mit�
	 * qui commence la path
	 * @return
	 */
	public Cell getFinishingCell() {
		Cell firstCell = occupiedCells.get(0);
		return firstCell.getOtherEndCell();
	}
		
	
	/**
	 * M�thode ajoutant une cell au path
	 * @param newCell la cell � ajouter
	 */
	public void addCell(Cell newCell) {
		newCell.setPath(this);
		this.occupiedCells.add(newCell);
	}
	

	/**
	 * M�thode vidant l'entieret� du path
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
	 * M�thode qui vide le path � partir d'une cell
	 * du path. 
	 * @param cell
	 */
	public void clearFrom(Cell cell) {
		//compte le nombre de cells strictement avant la cell � partir de laquelle le path doit etre vid�
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
	 * M�thode indiquant au tag qu'il est complet
	 * @param bool
	 */
	public void setTagIsComplete(boolean bool) {
		tag.setIsComplete(bool);
	}
		
	
	/**
	 * M�thode r�cup�rant la premi�re cell
	 * du path
	 * @return
	 */
	public Cell getFirstCell() {
		return occupiedCells.get(0);
	}
	
	
	/**
	 * M�thode appel�e par le controller pour obtenir la liste des directions
	 * � suivre pour tracer le chemin � partir de la position de la cell
	 * constituant le d�but du path.
	 * @param pos La position sur la grille d'une cell.
	 * @return La liste des directions du path associ�.
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
	 * M�thode appel�e par une celulle afin
	 * de savoir si son path est complet
	 * @return true si le tag associ� au path associ� � la cell est complet, false sinon
	 */
	public boolean getIsComplete() {
		return tag.getIsComplete();
	}
	
}
	