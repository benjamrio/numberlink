package model;
import java.util.ArrayList;
import control.Controller;
import java.util.List;

public class Tag {
	private int number;
	private Path path;
	private End end1;
	private End end2;
	private boolean isComplete;
	private Grid grid;
	
	
	public  Cell[] getEnds() {
		Cell[] Ends = new Cell[2];
		Ends[0] = end1.getCell();
		Ends[1] = end2.getCell(); 
		return Ends; 
	}
	
	public End getOtherEnd(End end) {
		return (end1 == end ? end2 : end1);
	}
	
	public Path getPath() {
		return path;
	}

	public void setIsComplete(boolean bool) {
		grid.setTagIsComplete(this, bool);
	}
	
	public boolean getIsComplete() {
		return grid.getTagIsComplete(this);
	}
	public Path clearPath() {
		//supprime le contenu du path et actualise le tag.
		if(path != null) {
			path.clear();
		}
		end1.clearPath();
		end2.clearPath();
		isComplete = false;
		return path;
	}
	
	public void setEnd1(End end) {
		end1=end;
	}
	
	public void setEnd2(End end) {
		end2=end;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
	
	
