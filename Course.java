package Client;

public class Course extends Object {
	private int row;
	private int column;
	private String name = " ";
	private String location = " ";
	private String lecturer = " ";

	public Course(int row, int column, String n, String l, String t) {
		this.row = row;
		this.column = column;
		this.name = n;
		this.location = l;
		this.lecturer = t;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int r) {
		this.row = r;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int c) {
		this.column = c;
	}
}

