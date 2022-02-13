package model;

/**
 * Classe Tag qui regroupe les ends qui doivent être reliés entre eux,
 * dans un sens où dans un autre. Les deux ends jouent un rôle symétriques.
 * @author Noé Bopp
 * @author Benjamin Rio
 */
public class Tag {
	private Path path;
	private End end1;
	private End end2;
	private Grid grid;
	
	
	public Cell[] getEnds() {
		Cell[] Ends = new Cell[2];
		Ends[0] = end1.getCell();
		Ends[1] = end2.getCell(); 
		return Ends; 
	}
	
	
	/**
	 *  Méthode appelée par End afin de connaitre l'autre
	 *  end du même tag
	 * @param end End : un end du tag
	 * @return end End : l'autre de ce tag
	 */
	public End getOtherEnd(End end) {
		return (end1 == end ? end2 : end1);
	}
	
	
	/** Méthode appelée par le path afin
	 * de changer l'état du path (complet ou non)
	 * @param bool boolean, true si l'état est complet, false sinon
	 */
	public void setIsComplete(boolean bool) {
		grid.setTagIsComplete(this, bool);
	}
	
	/**
	 * Méthode appelée par Path afin de connaitre
	 * l'état du path
	 * @return bool boolean, true si l'état est complet, false sinon
	 */
	public boolean getIsComplete() {
		return grid.getTagIsComplete(this);
	}
	
	/**
	 * Méthode appelée par End qui vide un path,
	 * et permet entre autre que les ends du même tag
	 * vivent dans le même path
	 * @return le path associé au tag
	 */
	public Path clearPath() {
		//supprime le contenu du path et actualise le tag.
		if(path != null) {
			path.clear();
		}
		end1.clearPath();
		end2.clearPath();
		return path;
	}
	
	
	/**
	 * Méthode appelée par le constructeur de Grid
	 * afin d'initialiser le tag. Il n'y a aucune relation d'ordre entre End1 et End2
	 * @param end End
	 */
	public void setEnd1(End end) {
		end1=end;
	}
	
	/**
	 * Méthode appelée par le constructeur de Grid
	 * afin d'initialiser le tag. Il n'y a aucune relation d'ordre entre End1 et End2
	 * @param end End
	 */
	public void setEnd2(End end) {
		end2=end;
	}
	
	
	/**
	 * Méthode appelée par le constructeur de Grid
	 * afin d'initaliser le tag.
	 * @param grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
	
	
