
public class Tag {
	private String name;
	private Path path;
	private End end1, end2; 
	

	public End getEnd1() {
		return end1;
	}

	public void setEnd1(End end1) {
		this.end1 = end1;
	}

	public End getEnd2() {
		return end2;
	}

	public void setEnd2(End end2) {
		this.end2 = end2;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
