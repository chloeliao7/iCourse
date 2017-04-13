package setting;
import java.sql.Date;

import version2.Course;

public class account {
	String user_name;
	String pin;////////////////may also use a sql package type!!!!!!!!
	private String email;
	public Course[][] courses;////注意更改course。class所在的包
	Date set_Date;///有sql专门包！！！！！！！！！！！！！！！！！！！！！！！！
	private int id;
	
	public account(String name,String pin){
		user_name=name;
		this.pin=pin;
		courses=new Course[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				courses[i][j] = new Course(i, j," ");
			}
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
