package model;

import model.Path;
import model.Grid;
import control.Direction;
import model.End;

public class Cell {
	private Path path;
	private End end;
	private Grid grid;
	
	public Cell(Grid grid) {
		this.grid = grid;
	}
	
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public End getEnd() {
		return end;
	}

	public void setEnd(End end) {
		this.end = end;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	

	public Path startPathAtCell() {
		//3 cas : case avec un path, end sans path, case vide
		if (path != null) {
			//on cut le path à partir de cette cellule (donc cette cellule perd son path)
			path.clearFrom(this);
			//et on rajoute à nouveau son path
			this.path = path;
		}
		else {
			if (end != null) {
				//si on clique sur des extrémités
				path = end.clearPathOfTag();
				if (path == null) {
					path = new Path(this);
				}
			}
		}
		return path;	
	}
	
	public Cell getNextCell(Direction dir) {
		return grid.getNeighbourCell(this, dir);
	}
	

	//nécessairement une cell avec un end
	public Cell getOtherEndCell() {
		return end.getOtherEnd().getCell();
	}

	public Cell getFinishingCell() {
		if (path != null){
			return path.getFinishingCell();
		}
		System.out.println("Pas de path");
		return null;
	}
	public void tagComplete() {
		path.tagComplete();
	}
	
	public boolean isAllowedFrom(Cell cellBefore) {
		if (end == null) {
			return true;
		} else {
			if (this == cellBefore.getFinishingCell()) {
				cellBefore.tagComplete();
				return true;
			}
		}
		return false;
	}
	

	
}
