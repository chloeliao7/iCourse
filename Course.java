package version1;

public class Course extends Object{
	private int row;
	private int column;
	String content;
	private String name;
	private String location;
	private String note;
	private String lecturer;
	

	public Course(int row, int column, String content) {
		this.row=row;
		this.column=column;
		this.content=content;
	}

	public int getColumn() {
		return column;
	}
	public void setContent(String content){
		this.content=content;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
