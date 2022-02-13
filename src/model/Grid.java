package model;

import control.Direction;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe grille, g�rant l'ensemble des cellules et communiquant avec le controller
 * pour initier un path � partir d'une cellule cliqu�e. La grille doit �galement 
 * avoir en m�moire l'�tat des tags (complet ou non), afin de savoir si elle est 
 * � son tour dans l'�tat complet ou non.
 * @author No� Bopp
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
		//Initialisation de la Map stockant les �tat des tags (donc initialisation des ends et des tags)
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
	 * M�thode appel�e par le controller, initiant ou continuant un path � partir de la cell cliqu�e,
	 * si cela est possible. Renvoit le path s�lectionn� (et null si il n'y a pas de path 
	 * associ� � la case cliqu�e)
	 * @param i entier indiquant le num�ro de ligne de la case cliqu�e
	 * @param j entier indiquant le num�ro de colonne de la case cliqu�e
	 * @return Path Le path (ancien ou nouveau) associ� � la cellule cliqu�e 
	 */
	public Path startPath(int i, int j) {
		Cell selectedCell = cells[i][j];
		Path path = selectedCell.startPathAtCell();
		return path;
	}
	

	/**
	 * M�thode appel�e par la cell ou par la grille 
	 * pour connaitre sa position (ou la position d'une certaine cell)
	 * en coordonn�e i, j au sein de la grille
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
	 * M�thode appel�e par une cell pour obtenir la cell voisine
	 * dans la direction indiqu�e dans les r�gles du jeu.
	 * Il y a deux raisons pour lesquelles la m�thode renvoit null, 
	 * indiquant que l'on ne peut pas �tendre le path de la cell � la cell voisine esp�r�e :
	 * <ul>
	 * 	<li> La cell n'est pas dans la grille (out of bounds)</li>
	 *  <li> La cell est une end qui ne permet pas de finir le path,
	 *  c'est � dire que ce n'est pas l'end du tag correspondant au path
	 *  qui n'est pas la premi�re cell du path</li>
	 *  </ul>
	 * @param cell la celulle � partir de laquelle on cherche la case voisine
	 * @param direction direction dans laquelle on d�sire trouver la case voisine
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
		//v�rification de l'int�grit� g�om�trique (dans la grille)
		if (0<=i && i<nbLines && 0<=j && j<nbCols) {
			//v�rification de l'int�grit� s�mantique (pas une end "interdite")
			Cell nextCell = cells[i][j];
			nextCell.changePathTo(cell);
			return nextCell;
		}
		return null;
	}
	
	
	/**
	 * M�thode appel�e par le controller retournant les directions d'un path associ� � une cell,
	 * � partir de la position en coordonn�es i, j de cette cell
	 * @param pos un tableau d'entiers de taille deux [ligne, colonne]
	 * @return La liste des directions du path associ�.
	 */
	public Direction[] getDirections(int[] pos) {
		return getCellAt(pos[0], pos[1]).getDirectionsOfPath();
	}
	
	/**
	 * M�thode appel�e par le controller pour
	 * savoir si la grille est compl�te
	 * @return true si la grille est compl�te, false sinon
	 */
	public boolean isComplete() {
		return !tagIsComplete.containsValue(false);
	}
	
	
	/**
	 * M�thode appel�e par la grille r�cup�rant l'objet cell
	 * � la position sur la grille indiqu�e
	 * @param i le num�ro de ligne de la cell
	 * @param j le num�ro de colonne de la cell
	 * @return Une instance de la classe Cell
	 */
	public Cell getCellAt(int i, int j) {
		return cells[i][j];
	}

	/**
	 * M�thode retournant la position sur la grille
	 * de la premi�re extr�mit� du path associ� 
	 * � la cell qui est � la position pr�cis�e.
	 * @param pos La position de la cell
	 * @return Un tableau de deux entiers [ligne, colonne]
	 */
	public int[] getPosFirstEnd(int[] pos){
		return getCellAt(pos[0], pos[1]).getPosFirstEnd();
	}
	
	/**
	 * M�thode appel�e par une cellule pour indiquer au tag associ� qu'il passe dans l'�tat complet.
	 * Un tag est complet si son path l'est, c'est � dire si il y a un path et que celui ci 
	 * joint deux extr�mit�s. Une fois complet, on ne peut modifier le path que en le r�initilisant 
	 * (en cliquant sur une end de ce path) ou en le coupant avec un autre path
	 * @param tag le tag consid�r�
	 * @param bool true si le tag est complet, false sinon
	 */
	public void setTagIsComplete(Tag tag, boolean bool) {
		tagIsComplete.put(tag, bool);
	}
	
	/**
	 * M�thode appel�e par un tag afin de connaitre son �tat 
	 * @param tag le tag consid�r�
	 * @return bool true si le tag est complet, false sinon
	 */
	public boolean getTagIsComplete(Tag tag) {
		return tagIsComplete.get(tag);
	}

	/**
	 * M�thode appel�e par le controller
	 * pour connaitre le nombre de colonnes
	 * @return int 
	 */
	public int getNbColumns() {
		return nbCols;
	}
	
	/**
	 * M�thode appel�e par le controller
	 * pour connaitre le nombre de lignes
	 * @return int 
	 */
	public int getNbLines() {
		return nbLines;
	}

}
