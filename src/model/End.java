package model;

public class End {
	
	private Tag tag;
	private Cell cell; 

	public End(Cell cell,Tag tag) {
		this.cell = cell; 
		this.tag = tag; 
		 
	}
	
	public End getOtherEnd() {
		return tag.getOtherEnd(this);
	}
	
	public Path clearPathOfTag(){
		return tag.clearPath(); 
	
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public void clearPath() {
		cell.setPath(null);
	}


}