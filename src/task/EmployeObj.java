package task;

public class EmployeObj {
	private int employeId;
	private String name;
	private long mobile;
	private String email;
	private String department;
	
	public void setEmployeId(int employeId) {
		this.employeId = employeId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public void setEmail(String email) {
		this.email =email;
	}
	public void setDepatment(String department) {
		this.department =department;
	}
	public int getEmployeId() {
		return this.employeId;
	}
	public String getName() {
		return this.name;
	}
	public long getMobile() {
		return this.mobile;
	}
	public String getEmail() {
		return this.email;
	}
	public String getDepartment() {
		return this.department;
	}
}
