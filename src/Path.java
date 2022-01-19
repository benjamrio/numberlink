
public class Path {
	/* liste de cells, avec un tag, et deux ends (qui sont fans la liste de cells) */
	private Cell[] cells;
	private Tag tag;
	private Controller controller;
	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}
