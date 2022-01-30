package control;

import java.util.ArrayList;

import model.Cell;
import model.Grid;
import model.Path;
import model.Tag;
import java.util.Map;
import java.util.HashMap;

public class Controller implements IController{
	
	private Grid grid;
	private Path currentPath;
	private int nbLines = 5; 
	private int nbCols = 5;
	private int[][][] endsPosList;
	
	public Controller(int nbLines, int nbCols, int[][][] endsPosList) {
		//sauvegarde les tags et les positions de leur ends respectives dans un hasmap
		Map<Tag, int[][]> tagEndsPos = new HashMap<Tag, int[][]>();
		for (int[][] endPairPos : endsPosList) {
			Tag tag = new Tag();
			tagEndsPos.put(tag, endPairPos);
		}
		this.grid = new Grid(nbLines, nbCols, tagEndsPos);
		this.endsPosList = endsPosList;
	}

	//gérer si c'est complet : 
	//--> checker tous les tag et leur demander si ils sont complet, à chaque fois 
	
	//selection de la case pour initier un path avec la souris, et affecte le current path au path sélectioné si il y en a un
	@Override
	public boolean clickCell(int line, int col) {
		Path futurPath = grid.startPath(line, col);
		this.currentPath = futurPath;
		return false;
	}


	//est vraie, l'état du modèle a changé. Sinon aucune modif à effectuer
	@Override
	public boolean action(Direction dir) {
		if (currentPath != null) {
			System.out.println("Flèche" + dir.name());
			System.out.println("Ligne courante : " + currentPath.getTag());
			currentPath.moveDir(dir);
			System.out.println("Tag Completions : " + grid.getTagIsComplete().values());
			return grid.isComplete();
		}
		return false;
	}

	@Override
	public int[][][] getEndsPosList(){
		return endsPosList;
	}
	

	@Override
	public int getNbTags() {
		// TODO Auto-generated method stub
		return endsPosList.length;
	}

	@Override
	public Direction[] getDirections(int[] pos) {
		return (grid.getDirections(pos));
	}


	@Override
	public int[][][] getEndsPos() {
		return endsPosList;
	}


	@Override
	public int getNbLines() {
		return nbLines;
	}


	@Override
	public int getNbColumns() {
		return nbCols;
	}
	
	public int[] getPosFirstEnd(int[] pos){
		return grid.getPosFirstEnd(pos);
	}
}
