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
			System.out.println("Path temp = " +path);
			//on cut le path à partir de cette cellule (donc cette cellule perd son path)
			path.clearFrom(this);
			System.out.println("Après clea, path = "+path);
			//et on rajoute à nouveau son path
			pathTemp.addCell(this);
			System.out.println("apres ajout final = "+ path);
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
	
	public boolean isTagComplete() {
		return path!=null && path.getIsComplete();
	}

	//méthode permettant de changer le path d'une cell au path d'une cell en paramètre, retournant si cela est possible
	//cela est impossible uniquement si c'est un end qui n'est pas le end qui permet de finir le tag
	public void changePathTo(Cell cellBefore) {
		Path futurePath = cellBefore.getPath();
		if (end == null) {
			if (path != null) {path.clearFrom(this);}
			futurePath.addCell(this);
		} else {
			if (this == cellBefore.getFinishingCell()) {
				futurePath.tagIsComplete(true);
				futurePath.addCell(this);
			}
		}
	}

	public int[] getPos(){
		return grid.getPosition(this);
	}
	
	public End getEnd() {
		return end;
	}
	
	public Direction[] getDirectionsOfPath() {
		if (path == null) {return null;}
		return path.getDirections();
	}

	public void setEnd(End end) {
		this.end = end;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public int[] getPosFirstEnd() {
		if (path != null) {
			return grid.getPosition(path.getFirstCell());
		}
		return null;
	}
}
