package task;

public class DependentObj {
	private int employeId;
	private String name;
	private int age;
	private String relationship;
	
	public void setEmployeId(int employeId) {
		this.employeId = employeId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setRelationship(String relationship) {
		this.relationship =relationship;
	}
	public int getEmployeId() {
		return this.employeId;
	}
	public String getName() {
		return this.name;
	}
	public int getAge() {
		return this.age;
	}
	public String getRelationship() {
		return this.relationship;
	}

}
