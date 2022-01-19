package model;
import model.Cell;

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
	
	public void startPath(int i, int j) {
		Cell selectedCell = cells[i][j];
		End selectedEnd = selectedCell.getEnd();
		
		//Si on a bien sélectionné une case correspondant à une end
		if (selectedEnd != null) {
			Path selectedPath = selectedEnd.getTag().getPath();

			//Si le end clické correspond à une tag avec un path déjà instantisé
			if (selectedPath != null) {
				selectedPath.setStart(selectedEnd);
			}
			else {
				new Path(selectedCell);
			}
		} 
		else {
			Path path = selectedCell.getPath();
			
			//si la cell cliqué correspond à un path, on abandonne les cells "futures"
			if {path != null} {
				path.cutAt(selectedCell);
			}
		}
	}
	
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
		if (0<=i && i<nbLines && 0<=j && j<nbCols) {
			return cells[i][j];
		}
		else {
			return null;
		}
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
