package task;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcTask {
	private String url ;
	private String userName ;
	private String password ;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void createTable() throws SQLException {
		String sql = "create table Employee (EMPLOYEE_ID INT PRIMARY KEY , "
				+ "NAME VARCHAR(255), MOBILE VARCHAR(10), EMAIL VARCHAR(255), DEPARTMENT VARCHAR(255))";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			try(Statement statement = connection.createStatement()){
				statement.execute(sql);
			}
		}
	}
	public void insertData(EmployeObj employe) throws SQLException {
		String sql = "insert into Employee values(?,?,?,?,?)";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employe.getEmployeId());
			statement.setString(2, employe.getName());
			statement.setLong(3, employe.getMobile());
			statement.setString(4, employe.getEmail());
			statement.setString(5,employe.getDepartment());
			statement.execute();
		}
		
	}
	public Map<Integer,EmployeObj> getEmployeeDetails(String name) throws SQLException {
		String sql = "select * from Employee where  NAME = ?" ;
		Map<Integer,EmployeObj>  employee = null;
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,name);
			
			try(ResultSet resultSet = statement.executeQuery()){
				employee = getDependentMap(resultSet);
			}
		}
		return employee;
	}
	
	public void updateDept(int emplyeId,String department) throws SQLException {
		String sql = "update Employee set DEPARTMENT = ? where EMPLOYEE_ID = ?";
		
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,department);
			statement.setInt(2, emplyeId);
			
			statement.execute();
		    }
		}
	
	public Map<Integer,EmployeObj> getEmplyees(int count) throws SQLException {
		
		Map<Integer,EmployeObj>  employees = null;
		String sql = "select * from Employee limit ? ";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, count);
			try(ResultSet resultSet = statement.executeQuery()){
			    
			    employees = getDependentMap(resultSet);
			}

		}
		
		return employees;
		
	}
public Map<Integer,EmployeObj> getEmplyeeInOrder(int count) throws SQLException {
		
		Map<Integer,EmployeObj>  employees = null;
		String sql = "select * from Employee order by EMPLOYEE_ID DESC limit ? ";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, count);
			try(ResultSet resultSet = statement.executeQuery()){
			    
			    employees = getDependentMap(resultSet);
			}

		}
		
		return employees;
		
	}
	public void DeleteDetails(int employeId) throws SQLException {
		String sql = "delete from Employee where EMPLOYEE_ID = ?";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeId);
			statement.execute();
		}
		
	}
	public void createDependentTable() throws SQLException {
		String sql = "create table dependent (EMPLOYEE_ID INT ,NAME VARCHAR(255), AGE INT, "
				+ "RELATIONSHIP VARCHAR(255),FOREIGN KEY(EMPLOYEE_ID) REFERENCES Employee(EMPLOYEE_ID) ON DELETE CASCADE)";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			try(Statement statement = connection.createStatement()){
				 statement.execute(sql);
			}
		}
		
	}
	
	public void addDependentDetail(DependentObj dependent) throws SQLException {
		String sql = "insert into dependent values(?,?,?,?)";
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, dependent.getEmployeId());
			statement.setString(2, dependent.getName());
			statement.setInt(3, dependent.getAge());
			statement.setString(4, dependent.getRelationship());
		
			statement.execute();
		}
	}
	public Map<String, List<DependentObj>> getdependentDetail(int employeId) throws SQLException {
		String sql = "select Employee.Name, Employee.EMPLOYEE_ID, dependent.NAME,"
				+ " dependent.AGE, dependent.RELATIONSHIP from dependent join Employee"
				+ " on dependent.EMPLOYEE_ID = Employee.EMPLOYEE_ID where Employee.EMPLOYEE_ID = ?";
		
		Map<String,List<DependentObj>> dependent = null;
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeId);
			ResultSet resultSet = statement.executeQuery();
		    dependent = setInMap(resultSet);
		}
		
	   return dependent;
		
	}
	
	public Map<String, List<DependentObj>> getDependentForNEmp(int count) throws SQLException{
		String sql =  "select Employee.Name, Employee.EMPLOYEE_ID, dependent.NAME, dependent.AGE, "
				+ "dependent.RELATIONSHIP from Employee join dependent on dependent.EMPLOYEE_ID = Employee.EMPLOYEE_ID"
				+ " order by Employee.Name limit ?";

		
		Map<String,List<DependentObj>> dependent = null;
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,count);
			ResultSet resultSet = statement.executeQuery();
		    dependent = setInMap(resultSet);
		}
		
	   return dependent;
		
	}
	
	private Map<String,List<DependentObj>> setInMap(ResultSet resultSet) throws SQLException{
		
		
		Map<String,List<DependentObj>> dependMap = new HashMap<String,List<DependentObj>>();
		
		DependentObj dependent = new DependentObj();
		
		while(resultSet.next()) {
		   
            String name = resultSet.getString(1);
			dependent.setEmployeId(resultSet.getInt(2)); 
			dependent.setName(resultSet.getString(3));
			dependent.setAge(resultSet.getInt(4));
			dependent.setRelationship(resultSet.getString(5));
			
			if(!dependMap.containsKey(name)) {
				List<DependentObj> dependentList = new ArrayList<DependentObj>();
            	dependentList.add(dependent);
            	dependMap.put(name, dependentList);
			}
			else {
				dependMap.get(name).add(dependent);
              }
		  }
		return dependMap;
	}
	private Map<Integer,EmployeObj> getDependentMap(ResultSet resultSet) throws SQLException {
		Map<Integer,EmployeObj> employeeMap = new HashMap<Integer,EmployeObj>();
		EmployeObj employee = new EmployeObj();
		   while(resultSet.next()) {
			employee.setEmployeId(resultSet.getInt(1)); 
			employee.setName(resultSet.getString(2));
			employee.setMobile(resultSet.getLong(3));
			employee.setEmail(resultSet.getString(4));
			employee.setDepatment(resultSet.getString(5));
	        employeeMap.put(resultSet.getInt(1), employee);
	      
		   }
		return employeeMap;
	}
	
}
