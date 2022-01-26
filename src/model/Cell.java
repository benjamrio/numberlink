import model.Path;
import model.Grid;
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
	
	
}

	public boolean addToPath(Path path) {
		Cell lastCell = path.getLastCell(); 
		if (lastCell.getEnd() == null ) {
			return true;
		}
		else if ()
	
	}
