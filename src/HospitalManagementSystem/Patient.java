package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient 
{

	private Connection connection;

	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner)
	{
		this.connection= connection;
		this.scanner=scanner;
	}
	public void addPatient()
	{
		System.out.println("Enter Patient name: ");
		String name=scanner.next();
		System.out.println("Enter Patient Age: ");
		int age =scanner.nextInt();
		System.out.println("Enter Patient Gender: ");
		String gender=scanner.next();
		try {
			String query="Insert into patients(name,age,gender) values(?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows=preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient added Successfully!!");
			}else
			{
				System.out.println("Failed to add Patient!!");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void viewPateints()
	{
		String query="select * from patients";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
		ResultSet resultSet=preparedStatement.executeQuery();
		 System.out.println("Pateints :");
		 System.out.println("+------------+----------------+-------+---------+");
		 System.out.println("| patient Id | Name           | Age   | Gender  |");
		 System.out.println("+------------+----------------+-------+---------+");
		while(resultSet.next())
		{
			int id =resultSet.getInt("id");
			String name=resultSet.getString("name");
			int age=resultSet.getInt("age");
			String gender=resultSet.getString("gender");
			System.out.println("     "+id+"          "+name+"       "+age+"    "+gender);
		}
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	public boolean getPateintById(int id) {
		String query="Select * from patients where id=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next())
		{
			return true;
		}
		else {
			return false;
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public  void viewAppointments()
	{
		String query="select * from appointments";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
		ResultSet resultSet=preparedStatement.executeQuery();
		 System.out.println("appointments :");
		 System.out.println("+------------+----------------+-------------+-------------------+");
		 System.out.println("|     Id     | PATIENT_ID     | DOCTOR_ID   | APPOINTMENT_DATE  |");
		 System.out.println("+------------+----------------+-------------+-------------------+");
		while(resultSet.next())
		{
			int id =resultSet.getInt("id");
			int patientId=resultSet.getInt("PATIENT_ID");
			int doctorId=resultSet.getInt("DOCTOR_ID");
			String appointmentDate=resultSet.getString("appointmen_date");
			System.out.println("        "+id+"          "+patientId+"                 "+doctorId+"           "+appointmentDate);
		}
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
	}
}
