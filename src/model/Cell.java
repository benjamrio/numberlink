package model;

import control.Direction;


/**
 * @author Noe Bopp
 * @author Benjamin Rio
 */
public class Cell {
	private Path path;
	private End end;
	private Grid grid;
	
	public Cell(Grid grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Méthode appelée par une cell pour connaitre le
	 * path d'une autre cell (utile lors des collisions)
	 * @return le path
	 */
	public Path getPath() {
		return path;
	}

	
	/**
	 * Méthode appelée par la grille pour initier ou continuer
	 * un path à partir de la cell
	 * @return le path de la cell (après initialisation ou "continuation")
	 */
	public Path startPathAtCell() {
		//3 cas : case avec un path, end, case vide
		if (end != null) {
			end.clearPathOfTag();
			//si on clique sur des extrémités
			if (path == null ) {
				path = new Path(this);
			}
		}
		if (path != null) {
			Path pathTemp = path;
			//on cut le path à partir de cette cellule (donc cette cellule perd son path)
			path.clearFrom(this);
			//et on rajoute à nouveau son path
			pathTemp.addCell(this);
		}
		return path;	
	}
	
	
	
	/**
	 * Méthode appelée par le path lorsqu'il 
	 * cherche à s'étendre pour trouver la cell
	 * indiquée dans cette direction.
	 * @param dir Direction
	 * @return null si on ne peut pas accéder à une cell légitime, la cell espérée sinon
	 */
	public Cell getNextCell(Direction dir) {
		return grid.getNeighbourCell(this, dir);
	}
	

	
	/**
	 * Méthode appelée par le path sur une cellule comportant
	 * une end afin de connaitre l'autre end du tag
	 * @return end
	 */
	public Cell getOtherEndCell() {
		return end.getOtherEnd().getCell();
	}

	
	/**
	 * Méthode appelée par une cell afin d'obtenir
	 * la cell qui permet de finir le path (correspondant au end
	 * qui n'est pas celui du début du path)
	 * @return cell, null si le path est vide
	 */
	public Cell getFinishingCell() {
		if (path != null){
			return path.getFinishingCell();
		}
		return null;
	}
	
	
	/**
	 * Méthode appelé par un path afin de 
	 * connaitre l'état du path (complet ou non)
	 * de cette cellule
	 * @return true si la cell a un path, et si le tag associé à celui est complet; false sinon
	 */
	public boolean isTagComplete() {
		return path!=null && path.getIsComplete();
	}

	/**
	 * Méthode appelée par une cellule pour changer le path de la cell
	 * à celui de la cell en paramètre, si cela est possible
	 * Cela n'est pas possible uniquement si la cell est un end qui n'est pas
	 * celui qui permet de finir le tag du path de la cell en argument
	 * @param cellBefore cell qui a le path que la cell à qui on applique la méthode devrait rejoindre
	 */
	public void changePathTo(Cell cellBefore) {
		Path futurePath = cellBefore.getPath();
		if (end == null) {
			if (path != null) {path.clearFrom(this);}
			futurePath.addCell(this);
		} else {
			if (this == cellBefore.getFinishingCell()) {
				futurePath.setTagIsComplete(true);
				futurePath.addCell(this);
			}
		}
	}

	/**
	 * Méthode appelée par Path afin
	 * de connaitre la position de la celulle
	 * @return
	 */
	public int[] getPos(){
		return grid.getPosition(this);
	}
	
	
	/**
	 * Méthode appelée par Path pour accéder 
	 * à la classe End de la cell
	 * @return end End
	 */
	public End getEnd() {
		return end;
	}
	
	
	/**
	 * Méthode appelée par la grille afin de
	 * récupérer une liste de directions permettant
	 * de dessiner le path à partir de la première celulle
	 * dans l'ihm
	 * @return
	 */
	public Direction[] getDirectionsOfPath() {
		if (path == null) {return null;}
		return path.getDirections();
	}

	
	/**
	 * Méthode appelée par la grid pour 
	 * initialiser correctement les cells
	 * @param end End
	 */
	public void setEnd(End end) {
		this.end = end;
	}
	
	/** Méthode appelée par Path et par End
	 * afin de modifier le path de la cell
	 * @param path Path
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/** 
	 * Méthode appelée par Grid afin
	 * de connaitre la position de la première cell
	 * du path (première end)
	 * @return 
	 */
	public int[] getPosFirstEnd() {
		if (path != null) {
			return grid.getPosition(path.getFirstCell());
		}
		return null;
	}
}
