package control;


import model.Grid;
import model.Path;
import model.Tag;
import java.util.Map;
import java.util.HashMap;

public class Controller implements IController{
	
	private Grid grid;
	private Path currentPath;
	private int[][][] endsPositions;
	
	
	public Controller(int nbLines, int nbCols, int[][][] endsPositions) {
		//sauvegarde les tags et les positions de leur ends respectives dans un hashmap,
		//qui sera stockée par la grille.
		Map<Tag, int[][]> tagEndsPos = new HashMap<Tag, int[][]>();
		for (int[][] endPairPos : endsPositions) {
			Tag tag = new Tag();
			tagEndsPos.put(tag, endPairPos);
		}

		this.grid = new Grid(nbLines, nbCols, tagEndsPos);
		this.endsPositions = endsPositions;
	}

	
	@Override
	public boolean clickCell(int line, int col) {
		Path futurPath = grid.startPath(line, col);
		this.currentPath = futurPath;
		return false;
	}

	@Override
	public boolean action(Direction dir) {
		if (currentPath != null) {
			currentPath.moveDir(dir);
			return grid.isComplete();
		}
		return false;
	}

	@Override
	public int[][][] getEndsPositions(){
		return endsPositions;
	}
	

	@Override
	public int getNbTags() {
		// TODO Auto-generated method stub
		return endsPositions.length;
	}

	@Override
	public Direction[] getDirections(int[] pos) {
		return (grid.getDirections(pos));
	}


	@Override
	public int getNbLines() {
		return grid.getNbLines();
	}


	@Override
	public int getNbColumns() {
		return grid.getNbColumns();
	}
	
	@Override
	public int[] getPosFirstEnd(int[] pos){
		return grid.getPosFirstEnd(pos);
	}
}
