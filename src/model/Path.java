import java.util.ArrayList;


public class Path {
	private Tag tag;	
	
	/*On utilise des ArrayList (modifiables) pour les cases et directions utilisées
	 * Pour un jeu un peu plus poussé, qui permette de revenir en arrière,
	 * il faudrait un modèle de casesOccupees et directions plus complexe.
	 */
	
	private ArrayList<Cell> occupiedCells;
	
	//Constructeur
	public Path(Cell firstCell){	
		this.occupiedCells = new ArrayList<Cell>();
		occupiedCells.add(firstCell);
		this.tag = firstCell.getEnd().getTag();
	}
	
	//On regarde si la ligne est finie pour quantifier l'avancement dans le niveau 
	public boolean isComplete() {
		int size = occupiedCells.size();
		boolean boolean1 = occupiedCells.get(0) == tag.getEnds()[0] && occupiedCells.get(size) == tag.getEnds()[1];
		boolean boolean2 = occupiedCells.get(0) == tag.getEnds()[1] && occupiedCells.get(size) == tag.getEnds()[0];
		return boolean1 || boolean2; 
		
	}
	
	
	//pour répondre à la demande du contrôleur.
	public boolean MoveDir(Direction direction) {
		//Si la ligne n'est pas finie, la case cherche sa voisine dans la direction demandée
			Cell currentCell = occupiedCells.get(occupiedCells.size()-1);
			Grid grid = occupiedCells.get(0).getGrid();
			Cell neighbourCell = grid.getNeighbour(currentCell,direction);
			
			//cell dans grid
			if (neighbourCell != null) { 
				boolean isAdded = neighbourCell.addToPath(this);
				//cell pas end d'un autre path ou celui du début
				if (isAdded) {
					addCell(neighbourCell);
					return true;
				}
				
					
					
					
					
		
				
		
		}
			
	
	
	
	
	
	//Gestion des cases occupées et directions utilisées
	public void addCell(Cell newCell) {
		this.occupiedCells.add(newCell);
	}
	
	//"Nettoyage" de la ligne, si on la reprend de 0
	public void clearPath() {
		for (Cell cell : occupiedCells) {
			//On signale aux cases qu'on les vide
			cell.setPath(null);
		}
		occupiedCells.clear();	
	}
	
	public void cutPath(Cell collisionCell ) {
	//ne pas appeler avec un first end
		int numberOfCollision = 0; 
		for (Cell cell : occupiedCells) {
			if (collisionCell ==  occupiedCells.get(numberOfCollision)) {
				break; 
			}
			numberOfCollision ++; 
		}
		for (Cell emptyCell : new ArrayList<Cell>(occupiedCells.subList(numberOfCollision, occupiedCells.size()))) {
			//On signale aux cells qu'on les vide
			emptyCell.setPath(null);
		}
		this.occupiedCells = new ArrayList<Cell>(occupiedCells.subList(0, numberOfCollision-1)); 
	}
		
	

	public ArrayList<Cell> getoccupiedCells() {
		return occupiedCells;
	}

	public Cell getFutureLastCell() {
		if (occupiedCells.get(0) == tag.getEnds()[0]) {
			return tag.getEnds()[1];				
		};
		return tag.getEnds()[0];
	}
	public Cell getLastCell() {
		return occupiedCells.get(occupiedCells).size()-1); 
	}
	
	public Cell getFirstCell() {
		return occupiedCells.get(0);
	}
	
	public void setStart(Cell cell) {
		this.clearPath(); 
		occupiedCells.set(0, cell);
		}
				
	
	}
	