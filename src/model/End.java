package model;

/**
 * Classe End, extrémité au sein d'une cellule qui doit être relié à une
 * autre extrémité, du même tag. 
 * @author Noé Bopp
 * @author Benjamin Rio
 */
public class End {
	
	private Tag tag;
	private Cell cell; 

	public End(Cell cell,Tag tag) {
		this.cell = cell; 
		this.tag = tag; 
	}
	
	/**
	 * Méthode appelée par une instance de cell
	 * avec un attribut end non nul, afin d'obtenir
	 * l'autre end du tag, permettant de connaitre la
	 * cell d'arrivée.
	 * @return end End
	 */
	public End getOtherEnd() {
		return tag.getOtherEnd(this);
	}
	
	
	/**
	 * Méthode appelée par Cell afin de vider
	 * le path du tag. 
	 * @return path Path le path de this
	 */
	public Path clearPathOfTag(){
		return tag.clearPath(); 
	
	}
	
	
	/** Méthode appelée par tag
	 * afin de vider l'extremité this de son path
	 */
	public void clearPath() {
		cell.setPath(null);
	}
	
	
	/** Méthode appeler par Tag et par Cell
	 * afin de faire correspondre un end
	 * à la cell qui le contient
	 * @return Cell cell
	 */
	public Cell getCell() {
		return cell;
	}
	
	
	/**
	 * Appelée directement dans le constructeur
	 * du path, afin de connaitre le tag du path
	 * @return Tag tag  
	 */
	public Tag getTag() {
		return tag;
	}


}