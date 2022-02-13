package model;

/**
 * Classe End, extr�mit� au sein d'une cellule qui doit �tre reli� � une
 * autre extr�mit�, du m�me tag. 
 * @author No� Bopp
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
	 * M�thode appel�e par une instance de cell
	 * avec un attribut end non nul, afin d'obtenir
	 * l'autre end du tag, permettant de connaitre la
	 * cell d'arriv�e.
	 * @return end End
	 */
	public End getOtherEnd() {
		return tag.getOtherEnd(this);
	}
	
	
	/**
	 * M�thode appel�e par Cell afin de vider
	 * le path du tag. 
	 * @return path Path le path de this
	 */
	public Path clearPathOfTag(){
		return tag.clearPath(); 
	
	}
	
	
	/** M�thode appel�e par tag
	 * afin de vider l'extremit� this de son path
	 */
	public void clearPath() {
		cell.setPath(null);
	}
	
	
	/** M�thode appeler par Tag et par Cell
	 * afin de faire correspondre un end
	 * � la cell qui le contient
	 * @return Cell cell
	 */
	public Cell getCell() {
		return cell;
	}
	
	
	/**
	 * Appel�e directement dans le constructeur
	 * du path, afin de connaitre le tag du path
	 * @return Tag tag  
	 */
	public Tag getTag() {
		return tag;
	}


}