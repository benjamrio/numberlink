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
	 * M�thode appel�e par une cell pour connaitre le
	 * path d'une autre cell (utile lors des collisions)
	 * @return le path
	 */
	public Path getPath() {
		return path;
	}

	
	/**
	 * M�thode appel�e par la grille pour initier ou continuer
	 * un path � partir de la cell
	 * @return le path de la cell (apr�s initialisation ou "continuation")
	 */
	public Path startPathAtCell() {
		//3 cas : case avec un path, end, case vide
		if (end != null) {
			end.clearPathOfTag();
			//si on clique sur des extr�mit�s
			if (path == null ) {
				path = new Path(this);
			}
		}
		if (path != null) {
			Path pathTemp = path;
			//on cut le path � partir de cette cellule (donc cette cellule perd son path)
			path.clearFrom(this);
			//et on rajoute � nouveau son path
			pathTemp.addCell(this);
		}
		return path;	
	}
	
	
	
	/**
	 * M�thode appel�e par le path lorsqu'il 
	 * cherche � s'�tendre pour trouver la cell
	 * indiqu�e dans cette direction.
	 * @param dir Direction
	 * @return null si on ne peut pas acc�der � une cell l�gitime, la cell esp�r�e sinon
	 */
	public Cell getNextCell(Direction dir) {
		return grid.getNeighbourCell(this, dir);
	}
	

	
	/**
	 * M�thode appel�e par le path sur une cellule comportant
	 * une end afin de connaitre l'autre end du tag
	 * @return end
	 */
	public Cell getOtherEndCell() {
		return end.getOtherEnd().getCell();
	}

	
	/**
	 * M�thode appel�e par une cell afin d'obtenir
	 * la cell qui permet de finir le path (correspondant au end
	 * qui n'est pas celui du d�but du path)
	 * @return cell, null si le path est vide
	 */
	public Cell getFinishingCell() {
		if (path != null){
			return path.getFinishingCell();
		}
		return null;
	}
	
	
	/**
	 * M�thode appel� par un path afin de 
	 * connaitre l'�tat du path (complet ou non)
	 * de cette cellule
	 * @return true si la cell a un path, et si le tag associ� � celui est complet; false sinon
	 */
	public boolean isTagComplete() {
		return path!=null && path.getIsComplete();
	}

	/**
	 * M�thode appel�e par une cellule pour changer le path de la cell
	 * � celui de la cell en param�tre, si cela est possible
	 * Cela n'est pas possible uniquement si la cell est un end qui n'est pas
	 * celui qui permet de finir le tag du path de la cell en argument
	 * @param cellBefore cell qui a le path que la cell � qui on applique la m�thode devrait rejoindre
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
	 * M�thode appel�e par Path afin
	 * de connaitre la position de la celulle
	 * @return
	 */
	public int[] getPos(){
		return grid.getPosition(this);
	}
	
	
	/**
	 * M�thode appel�e par Path pour acc�der 
	 * � la classe End de la cell
	 * @return end End
	 */
	public End getEnd() {
		return end;
	}
	
	
	/**
	 * M�thode appel�e par la grille afin de
	 * r�cup�rer une liste de directions permettant
	 * de dessiner le path � partir de la premi�re celulle
	 * dans l'ihm
	 * @return
	 */
	public Direction[] getDirectionsOfPath() {
		if (path == null) {return null;}
		return path.getDirections();
	}

	
	/**
	 * M�thode appel�e par la grid pour 
	 * initialiser correctement les cells
	 * @param end End
	 */
	public void setEnd(End end) {
		this.end = end;
	}
	
	/** M�thode appel�e par Path et par End
	 * afin de modifier le path de la cell
	 * @param path Path
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/** 
	 * M�thode appel�e par Grid afin
	 * de connaitre la position de la premi�re cell
	 * du path (premi�re end)
	 * @return 
	 */
	public int[] getPosFirstEnd() {
		if (path != null) {
			return grid.getPosition(path.getFirstCell());
		}
		return null;
	}
}
