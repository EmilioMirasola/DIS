import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Gui extends Application {
	Button btnLogin, btnOpret, btnscene2;
	Label lblbrugernavn, lblPassword, lblBesked;
	Label lblscene2, lblInfoBruger;
	GridPane pane1, pane2;
	Scene scene1, scene2;
	Stage thestage;
	Connection connection;
	private PasswordField password = new PasswordField();
	private static TextField userName = new TextField();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-V4KATAQU:1433;instanceName=SQLEXPRESS;databaseName=password_db;user=sa;password=3emerfili1743;");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


		thestage = primaryStage;

		btnLogin = new Button("Log in");
		btnOpret = new Button("Opret");
		btnscene2 = new Button("Tilbage til log in");
		btnLogin.setOnAction(e -> {
			try {
				ButtonClicked(e);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		});
		btnOpret.setOnAction(e -> {
			try {
				ButtonClicked(e);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		});
		btnscene2.setOnAction(e -> {
			try {
				ButtonClicked(e);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		});
		lblbrugernavn = new Label("Navn");
		lblPassword = new Label("Password");
		lblBesked = new Label("Hello World");

		lblscene2 = new Label("Du er nu logget ind");
		lblInfoBruger = new Label("Bruger info");

		pane1 = new GridPane();
		pane2 = new GridPane();
		pane1.setVgap(10);
		pane2.setVgap(10);

		pane1.setStyle("-fx-background-color: yellow;-fx-padding: 10px;");
		pane2.setStyle("-fx-background-color: lightgreen;-fx-padding: 10px;");

		pane1.setPadding(new Insets(5));
		pane1.setHgap(10);
		pane1.setVgap(10);

		pane1.add(lblbrugernavn, 0, 0);
		pane1.add(userName, 0, 1, 2, 1);
		pane1.add(lblPassword, 0, 2);
		pane1.add(password, 0, 3, 2, 1);
		pane1.add(btnLogin, 0, 4);
		pane1.add(btnOpret, 1, 4);
		pane1.add(lblBesked, 0, 5);

		pane2.setPadding(new Insets(5));
		pane2.setHgap(10);
		pane2.setVgap(10);

		pane2.add(lblInfoBruger, 0, 0);
		pane2.add(btnscene2, 0, 1);

		scene1 = new Scene(pane1, 200, 200);
		scene2 = new Scene(pane2, 200, 200);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene1);
		primaryStage.show();
	}

	public void ButtonClicked(ActionEvent e) throws SQLException {
		if (e.getSource() == btnLogin) {

			String brugernavnText = userName.getText();
			String pw = password.getText();

			String SQL = "select brugerNavn,password from Bruger "
					+ "where brugerNavn=" + String.format("'%s'", brugernavnText);


			PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String hashedPassword = getHashedPassword(pw);
				if (resultSet.getString(1).equals(brugernavnText) && resultSet.getString(2).equals(hashedPassword)) {
					thestage.setScene(scene2);
				}
			}

		} else if (e.getSource() == btnOpret) {
			String SQL = "INSERT INTO Bruger(brugernavn,password) "
					+ "VALUES(?,?)";

			String brugernavnText = userName.getText();
			String pw = password.getText();

			String hashedPassword = getHashedPassword(pw);
			if (pw != null) {
				PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				System.out.println("Oprettet " + hashedPassword);
				lblBesked.setText("");
				preparedStatement.setString(1, brugernavnText);
				preparedStatement.setString(2, hashedPassword);
				preparedStatement.execute();
				password.clear();
				userName.clear();
				password.clear();
				userName.clear();
				thestage.setScene(scene1);
			}
		}
	}

	private String getHashedPassword(String pw) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(pw.getBytes());

			byte[] bytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
		}
		return null;
	}
}