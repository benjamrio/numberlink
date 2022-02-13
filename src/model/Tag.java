package model;

/**
 * Classe Tag qui regroupe les ends qui doivent �tre reli�s entre eux,
 * dans un sens o� dans un autre. Les deux ends jouent un r�le sym�triques.
 * @author No� Bopp
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
	 *  M�thode appel�e par End afin de connaitre l'autre
	 *  end du m�me tag
	 * @param end End : un end du tag
	 * @return end End : l'autre de ce tag
	 */
	public End getOtherEnd(End end) {
		return (end1 == end ? end2 : end1);
	}
	
	
	/** M�thode appel�e par le path afin
	 * de changer l'�tat du path (complet ou non)
	 * @param bool boolean, true si l'�tat est complet, false sinon
	 */
	public void setIsComplete(boolean bool) {
		grid.setTagIsComplete(this, bool);
	}
	
	/**
	 * M�thode appel�e par Path afin de connaitre
	 * l'�tat du path
	 * @return bool boolean, true si l'�tat est complet, false sinon
	 */
	public boolean getIsComplete() {
		return grid.getTagIsComplete(this);
	}
	
	/**
	 * M�thode appel�e par End qui vide un path,
	 * et permet entre autre que les ends du m�me tag
	 * vivent dans le m�me path
	 * @return le path associ� au tag
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
	 * M�thode appel�e par le constructeur de Grid
	 * afin d'initialiser le tag. Il n'y a aucune relation d'ordre entre End1 et End2
	 * @param end End
	 */
	public void setEnd1(End end) {
		end1=end;
	}
	
	/**
	 * M�thode appel�e par le constructeur de Grid
	 * afin d'initialiser le tag. Il n'y a aucune relation d'ordre entre End1 et End2
	 * @param end End
	 */
	public void setEnd2(End end) {
		end2=end;
	}
	
	
	/**
	 * M�thode appel�e par le constructeur de Grid
	 * afin d'initaliser le tag.
	 * @param grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
	
	
