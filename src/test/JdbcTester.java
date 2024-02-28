package test;

import task.JdbcTask;

import java.sql.SQLException;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import task.DependentObj;
import task.EmployeObj;

public class JdbcTester {
	public static void main(String... args) {
		JdbcTask jdbc = new JdbcTask();
		Logger logger = Logger.getLogger("myLogger");
		int condition = 1;
		try(Scanner myScanner = new Scanner(System.in)){
			do	{
				logger.log(Level.INFO,"choose any one ");
				condition =  myScanner.nextInt();
				//logger.log(Level.INFO,"Enter the url ");
				String url = "jdbc:mysql://localhost:3306/incubationDB";
				
				//logger.log(Level.INFO,"Enter the username ");
				String userName = "root";
				
				//logger.log(Level.INFO, "Enter the pssword ");
				String pass = "";
				
				jdbc.setUrl(url);
				jdbc.setUserName(userName);
				jdbc.setPassword(pass);
				
				switch(condition) {
				case 1:
					
					
					EmployeObj employe = new EmployeObj();
					logger.log(Level.INFO, "Enter the value of employee id ");
					int employeeId = myScanner.nextInt();
					myScanner.nextLine();
					employe.setEmployeId(employeeId);
					
					logger.log(Level.INFO, "Enter the name ");
					String name = myScanner.nextLine();
					employe.setName(name);
					
					logger.log(Level.INFO, "Enter the mobile number ");
					long mobile = myScanner.nextLong();
					myScanner.nextLine();
					employe.setMobile(mobile);
					
					logger.log(Level.INFO, "Enter the email id ");
					String email= myScanner.nextLine();
					employe.setEmail(email);
					
					logger.log(Level.INFO, "Enter the department ");
					String dept = myScanner.nextLine();
					employe.setDepatment(dept);
					
					jdbc.insertData(employe);
					break;
				case 2:
				
					Map<Integer,EmployeObj> employees =  jdbc.getEmployeeDetails("jeffry");
					 System.out.println(employees.keySet()); 
						/*
						 * logger.log(Level.INFO,"The employee of the employee is "+employee.
						 * getEmployeId());
						 * logger.log(Level.INFO,"The name of the employee is "+employee.getName());
						 * logger.log(Level.INFO,"The mobile number of the employee is "+employee.
						 * getMobile());
						 * logger.log(Level.INFO,"The email of the employee is "+employee.getEmail());
						 * logger.log(Level.INFO,"The Department of the employee is "+employee.
						 * getDepartment());
						 */
					break;
				case 3:
					jdbc.updateDept(2, "IAM EMGINEERING");
					break;
				case 4:
					logger.log(Level.INFO, "Enter the number of values ");
					int count =  myScanner.nextInt();
					
					System.out.println(jdbc.getEmplyees(count));
					
					
					/*
					 * employee = employees.get(0); System.out.println(employees.size());
					 * logger.log(Level.INFO,"The employee of the employee is "+employee.
					 * getEmployeId());
					 * logger.log(Level.INFO,"The name of the employee is "+employee.getName());
					 * logger.log(Level.INFO,"The mobile number of the employee is "+employee.
					 * getMobile());
					 * logger.log(Level.INFO,"The email of the employee is "+employee.getEmail());
					 * logger.log(Level.INFO,"The Department of the employee is "+employee.
					 * getDepartment());
					 */
					break;
				case 5:
					logger.log(Level.INFO, "Enter the employee id to delete detail ");
					int employeId = myScanner.nextInt();
					myScanner.nextLine();	
					jdbc.DeleteDetails(employeId);
					break;
				case 6:
					jdbc.createDependentTable();
					break;
				case 7:
					DependentObj dependent = new DependentObj();
					logger.log(Level.INFO, "Enter the value of employee id ");
					employeeId = myScanner.nextInt();
					myScanner.nextLine();
					dependent.setEmployeId(employeeId);
					
					logger.log(Level.INFO, "Enter the name ");
					name = myScanner.nextLine();
					dependent.setName(name);
					
					logger.log(Level.INFO, "Enter the age ");
					int age = myScanner.nextInt();
					myScanner.nextLine();
					dependent.setAge(age);
					
					logger.log(Level.INFO, "Enter the relationship ");
					String relationship= myScanner.nextLine();
					dependent.setRelationship(relationship);
				    jdbc.addDependentDetail(dependent);
					break;
				case 8:
					System.out.println(jdbc.getdependentDetail(3));
					break;
				case 9:
					System.out.println(jdbc.getDependentForNEmp(7));
					break;
				case 10:
					System.out.println(jdbc.getEmplyeeInOrder(2));
					break;
				}

			}while(condition !=0);
				
		   }catch(SQLException e) {	
			e.getMessage();
			e.printStackTrace();
		}
		
		
	}
    
}
