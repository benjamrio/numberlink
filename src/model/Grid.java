package model;
import model.Cell;
import model.Tag;
import model.End;
import control.Controller;
import control.Direction;
import java.util.Map;
import java.util.HashMap;
public class Grid {
	private int nbLines;
	private int nbCols;
	private Cell[][] cells;
	private Controller controller;
	private Map<Tag, Boolean> tagIsComplete = new HashMap<Tag, Boolean>();
	
	
	public Grid(int nbLines, int nbCols, Map<Tag, int[][]> tagEndsPos) {
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		int i, j;
		cells = new Cell[nbLines][nbCols];
		for (i=0; i < nbLines; i++) {
			for (j=0; j < nbCols; j++) {
				cells[i][j] = new Cell(this);
			}
		}
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

		System.out.println("Grid is initialized!");
		 
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
			nextCell.changePathTo(cell);
			return nextCell;
		}
		return null;
	}
	
	
	public Direction[] getDirections(int[] pos) {
		return getCellAt(pos[0], pos[1]).getDirectionsOfPath();
	}
	
	public boolean isComplete() {
		return !tagIsComplete.containsValue(false);
	}
	
	public Cell getCellAt(int i, int j) {
		return cells[i][j];
	}

	public int getNbLines() {
		return nbLines;
	}
	
	public int getNbCols() {
		return nbCols;
	}


	public Map<Tag, Boolean> getTagIsComplete() {
		return tagIsComplete;
	}	
	
	public int[] getPosFirstEnd(int[] pos){
		return getCellAt(pos[0], pos[1]).getPosFirstEnd();
	}
	
	public void setTagIsComplete(Tag tag, boolean bool) {
		tagIsComplete.put(tag, bool);
	}
	
	public boolean getTagIsComplete(Tag tag) {
		return tagIsComplete.get(tag);
	}
}
