import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLINJECTION {
	public static void main(String[] args) {

		try {
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Indtast brugernavn");
			String s1 = inLine.readLine();

			System.out.println("Indtast password");
			String s2 = inLine.readLine();

			Connection minConnection;
			minConnection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-V4KATAQU:1433;instanceName=SQLEXPRESS;databaseName=SQL_injection;user=sa;password=3emerfili1743;");

			Statement stmt = minConnection.createStatement();
			String SQL = "select * from brugere where brugerid = '" +
					s1 + "' and passw = '" + s2 + "'";

			System.out.println("SQL = " + SQL);

			ResultSet res = stmt.executeQuery(SQL);

			if (res.next()) {
				System.out.print("Velkommen du er nu logget ind");
			} else
				System.out.print("Ukorrekt logon");


			if (stmt != null)
				stmt.close();
			if (minConnection != null)
				minConnection.close();
		} catch (Exception e) {
			System.out.print("fejl:  " + e.getMessage());
		}
	}
}
