package model;

import control.Direction;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe grille, gérant l'ensemble des cellules et communiquant avec le controller
 * pour initier un path à partir d'une cellule cliquée. La grille doit également 
 * avoir en mémoire l'état des tags (complet ou non), afin de savoir si elle est 
 * à son tour dans l'état complet ou non.
 * @author Noé Bopp
 * @author Benjamin Rio
 *
 */
public class Grid {
	private int nbLines;
	private int nbCols;
	private Cell[][] cells;
	private Map<Tag, Boolean> tagIsComplete = new HashMap<Tag, Boolean>();
	
	
	public Grid(int nbLines, int nbCols, Map<Tag, int[][]> tagEndsPos) {
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		int i, j;
		//Initialisation des cells de la grille.
		cells = new Cell[nbLines][nbCols];
		for (i=0; i < nbLines; i++) {
			for (j=0; j < nbCols; j++) {
				cells[i][j] = new Cell(this);
			}
		}
		//Initialisation de la Map stockant les état des tags (donc initialisation des ends et des tags)
		for (var entry : tagEndsPos.entrySet()) {
			int[][] endPair = entry.getValue();
			Tag tag = entry.getKey();
			tag.setGrid(this);
			for (i=0; i < 2; i++) {
				Cell cell = cells[endPair[i][0]][endPair[i][1]];
				End end = new End(cell, tag);
				cell.setEnd(end);
				if (i == 0) {tag.setEnd1(end);} else {tag.setEnd2(end);}
				tagIsComplete.put(tag, false);
			}
		}
	}
	

	/**
	 * Méthode appelée par le controller, initiant ou continuant un path à partir de la cell cliquée,
	 * si cela est possible. Renvoit le path sélectionné (et null si il n'y a pas de path 
	 * associé à la case cliquée)
	 * @param i entier indiquant le numéro de ligne de la case cliquée
	 * @param j entier indiquant le numéro de colonne de la case cliquée
	 * @return Path Le path (ancien ou nouveau) associé à la cellule cliquée 
	 */
	public Path startPath(int i, int j) {
		Cell selectedCell = cells[i][j];
		Path path = selectedCell.startPathAtCell();
		return path;
	}
	

	/**
	 * Méthode appelée par la cell ou par la grille 
	 * pour connaitre sa position (ou la position d'une certaine cell)
	 * en coordonnée i, j au sein de la grille
	 * @param cell
	 * @return Un tableau de deux entiers [ligne, colonne].
	 */
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
	
	
	/**
	 * Méthode appelée par une cell pour obtenir la cell voisine
	 * dans la direction indiquée dans les règles du jeu.
	 * Il y a deux raisons pour lesquelles la méthode renvoit null, 
	 * indiquant que l'on ne peut pas étendre le path de la cell à la cell voisine espérée :
	 * <ul>
	 * 	<li> La cell n'est pas dans la grille (out of bounds)</li>
	 *  <li> La cell est une end qui ne permet pas de finir le path,
	 *  c'est à dire que ce n'est pas l'end du tag correspondant au path
	 *  qui n'est pas la première cell du path</li>
	 *  </ul>
	 * @param cell la celulle à partir de laquelle on cherche la case voisine
	 * @param direction direction dans laquelle on désire trouver la case voisine
	 * @return la cell voisine si elle respecte les deux conditions, null sinon.
	 */
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
		//vérification de l'intégrité géométrique (dans la grille)
		if (0<=i && i<nbLines && 0<=j && j<nbCols) {
			//vérification de l'intégrité sémantique (pas une end "interdite")
			Cell nextCell = cells[i][j];
			nextCell.changePathTo(cell);
			return nextCell;
		}
		return null;
	}
	
	
	/**
	 * Méthode appelée par le controller retournant les directions d'un path associé à une cell,
	 * à partir de la position en coordonnées i, j de cette cell
	 * @param pos un tableau d'entiers de taille deux [ligne, colonne]
	 * @return La liste des directions du path associé.
	 */
	public Direction[] getDirections(int[] pos) {
		return getCellAt(pos[0], pos[1]).getDirectionsOfPath();
	}
	
	/**
	 * Méthode appelée par le controller pour
	 * savoir si la grille est complète
	 * @return true si la grille est complète, false sinon
	 */
	public boolean isComplete() {
		return !tagIsComplete.containsValue(false);
	}
	
	
	/**
	 * Méthode appelée par la grille récupérant l'objet cell
	 * à la position sur la grille indiquée
	 * @param i le numéro de ligne de la cell
	 * @param j le numéro de colonne de la cell
	 * @return Une instance de la classe Cell
	 */
	public Cell getCellAt(int i, int j) {
		return cells[i][j];
	}

	/**
	 * Méthode retournant la position sur la grille
	 * de la première extrémité du path associé 
	 * à la cell qui est à la position précisée.
	 * @param pos La position de la cell
	 * @return Un tableau de deux entiers [ligne, colonne]
	 */
	public int[] getPosFirstEnd(int[] pos){
		return getCellAt(pos[0], pos[1]).getPosFirstEnd();
	}
	
	/**
	 * Méthode appelée par une cellule pour indiquer au tag associé qu'il passe dans l'état complet.
	 * Un tag est complet si son path l'est, c'est à dire si il y a un path et que celui ci 
	 * joint deux extrémités. Une fois complet, on ne peut modifier le path que en le réinitilisant 
	 * (en cliquant sur une end de ce path) ou en le coupant avec un autre path
	 * @param tag le tag considéré
	 * @param bool true si le tag est complet, false sinon
	 */
	public void setTagIsComplete(Tag tag, boolean bool) {
		tagIsComplete.put(tag, bool);
	}
	
	/**
	 * Méthode appelée par un tag afin de connaitre son état 
	 * @param tag le tag considéré
	 * @return bool true si le tag est complet, false sinon
	 */
	public boolean getTagIsComplete(Tag tag) {
		return tagIsComplete.get(tag);
	}

	/**
	 * Méthode appelée par le controller
	 * pour connaitre le nombre de colonnes
	 * @return int 
	 */
	public int getNbColumns() {
		return nbCols;
	}
	
	/**
	 * Méthode appelée par le controller
	 * pour connaitre le nombre de lignes
	 * @return int 
	 */
	public int getNbLines() {
		return nbLines;
	}

}
