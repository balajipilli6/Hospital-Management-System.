package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem 
{
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String password="admin";
	private static final String username="root";
	private static Connection connection;


	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection,scanner);
			Doctor doctor =new Doctor(connection);
			while(true)
			{
				System.out.println("Hospital Management System ");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. View Appointment");
				System.out.println("6. Exit");
				System.out.println("Enter your Choice: ");
				System.out.println();
				int choice =scanner.nextInt();

				switch(choice)
				{
				case 1:
					//add Patient
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					//view Patient
					patient.viewPateints();
					System.out.println();
					break;
				case 3:
					//view Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointment
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
					break;
				case 5:
					patient.viewAppointments();
					System.out.println();
					break;
				case 6:
					return;
				default:
					System.out.println("Enter valid Choice!!!");
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	public static void bookAppointment(Patient patient,Doctor doctor,Connection connection, Scanner scanner)
	{
		System.out.println("Enter Patient id: ");
		int patientId=scanner.nextInt();
		System.out.println("Enter Doctor id");
		int doctorId=scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD): ");
		String appointmentDate=scanner.next();
		
		if(patient.getPateintById(patientId) && doctor.getDoctorById(doctorId))
		{
			if(checkedDoctorAvailability(doctorId,appointmentDate,connection)) {
				String appointmentQuery ="Insert into appointments(patient_id, doctor_id, appointmen_date) values(?,?,?)";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
				preparedStatement.setInt(1, patientId);
				preparedStatement.setInt(2,doctorId);
				preparedStatement.setString(3, appointmentDate);
				int rowsAffected=preparedStatement.executeUpdate();
				if(rowsAffected>0)
				{
					System.out.println("Appointment Booked");
				}
				else
				{
					System.out.println("Either doctor or patient doesn't exist!!!");
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
			}else
			{
				System.out.println("Doctor not availables on this Date!!");
			}
		}else
		{
			System.out.println("Either doctor or patient doesn't exist!!");
		}

	}
	public static boolean checkedDoctorAvailability(int doctorId, String appointmentdate, Connection connection)
	{
		String query="select count(*) from appointments where doctor_id=? and appointmen_date=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,doctorId);
			preparedStatement.setString(2, appointmentdate);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				int count=resultSet.getInt(1);
				if(count==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	}

