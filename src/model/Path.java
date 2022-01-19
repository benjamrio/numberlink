package flow.model;
import java.util.ArrayList;


public class Path {
	private Tag tag;	
	
	/*On utilise des ArrayList (modifiables) pour les cases et directions utilis�es
	 * Pour un jeu un peu plus pouss�, qui permette de revenir en arri�re,
	 * il faudrait un mod�le de casesOccupees et directions plus complexe.
	 */
	
	private ArrayList<Cell> occupiedCells;
	
	
	
	

	
	
	//Constructeur
	public Path(Cell firstCell){
		
		
		this.occupiedCells = new ArrayList<Cell>();
		occupiedCells.add(firstCell);
		this.tag = firstCell.getEnd().getTag();
	}
	
	//On regarde si la ligne est finie pour quantifier l'avancement dans le niveau 
	public boolean isEnd(Cell nextCell) {
		if (tag.extrimity1 != occupiedCells[0] ) {
			return nextCell == tag.end1;
		}
		if (tag.extrimity2 != occupiedCells[0] ) {
			return nextCell == tag.end2;
		}
	
	}
	
	
	//M�thode principale, pour r�pondre � la demande du contr�leur.
	public void addCellToPath(Direction direction) {
		//Si la ligne n'est pas finie, la case cherche sa voisine dans la direction demand�e
			Cell currentCell = occupiedCells[occupiedCells.size()-1]
			Grid grid = occupiedCells[0].getGrid()
			Cell neighbourCell = grid.getNeighbour(currentCell,direction)
			
			
			if (neighbourCell != null) {
				if (neighbourCell.getEnd() != null || ==neighbourCell)
					
					
					
					
					if (neighbourCell == null) {
				//Cas o� l'on va terminer la ligne
				if (voisine.aUneExtremite() && voisine.getExtremite()==extremiteArrivee){
					this.extremiteCourante=voisine;
					addDirection(direction);
					addCase(voisine);				
				}
				
				//Cas g�n�ral o� on prolonge la ligne sur une case vide
				else if (voisine.estLibre()){
					addDirection(direction);
					addCase(voisine);
					this.extremiteCourante=voisine;
					//On informe la case qu'elle devient occup�e
					voisine.setLigne(this);
				}
				}
			}
		
	
	
	//Gestion des cases occup�es et directions utilis�es
	public void addCase(Case nouvelleCase) {
		this.occupiedCells.add(nouvelleCase);
	}
	
	public 

	//"Nettoyage" de la ligne, si on la reprend de 0
	public void clearPath() {
		for (Cell emptyCell : occupiedCells) {
			//On signale aux cases qu'on les vide
			emptyCell.setPath(null);
		}
		occupiedCells.clear();	
		
	}
	
	public void cutPath(Cell collisionCell ) {
		int numberOfCollision = 0; 
		for (Cell cell : occupiedCells) {
			if (collision Cell ==  cellOcuppied[numberOfCollision])
				break; 
			numberOfCollision = numbeOfCollision + 1; 
		}
		for (Cell emptyCell : occupiedCells.subList(numberOfCollision,occupiedCells[occupiedCells.size()])) {
			//On signale aux cells qu'on les vide
			emptyCell.setPath(null);
		}
		this.occupiedCells = occupiedCells.subList(0,numberOfCollision - 1); 
	}
		
	

	public ArrayList<Cell> getoccupiedCells() {
		return occupiedCells;
	}

	public Cell getFutureLastCell() {
		return occupiedCells[0]; 
	}
	
	public Cell getFirstCell() {
		return occupiedCells[0];
	}
	
	public void setEnd1(Cell cell)
		this.clearPath(); 
		this.occupiedCells[0] = cell;
				
	

	
	

	

		
	
	
