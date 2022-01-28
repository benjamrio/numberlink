package model;
import java.util.ArrayList;
import java.util.List;

public class Tag {
	private String name;
	private Path path;
	private End end1, end2;
	private boolean isComplete;
	
	public  Cell[] getEnds() {
		Cell[] Ends = new Cell[2];
		Ends[0] = end1.getCell();
		Ends[1] = end2.getCell(); 
		return Ends; 
	}
	
	public End getOtherEnd(End end) {
		return (end1 == end ? end2 : end1);
	}
	
	
	public End getEnd1() {
		return end1;
	}

	public void setEnd1(End end1) {
		this.end1 = end1;
	}

	public End getEnd2() {
		return end2;
	}

	public void setEnd2(End end2) {
		this.end2 = end2;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setIsComplete(boolean bool) {
		this.isComplete = bool;
	}

	public Path clearPath() {
		if(path != null) {
			path.clear(); 
		}
		return path;
	}
}
	
	
