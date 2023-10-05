
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ReservationSystem 
{

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/reservation_system";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Connect to the database
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            // Create the login form
            JFrame loginForm = new JFrame("Login");
            loginForm.setSize(300, 200);
            loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Add the username and password fields
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            loginForm.add(new JLabel("Username:"));
            loginForm.add(usernameField);

            loginForm.add(new JLabel("Password:"));
            loginForm.add(passwordField);

            // Add the login button
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    // Get the username and password from the fields
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Check if the username and password are correct
                    if (isValidLogin(username, password)) {
                        // Show the reservation form
                
                    } else {
                        // Show an error message
                        JOptionPane.showMessageDialog(loginForm, "Invalid username or password");
                    }
                }
            });

            loginForm.add(loginButton);

            // Show the login form
            loginForm.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidLogin(String username, String password) {
        try {
            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
             // Set the parameters for the SQL statement
        statement.setString(1, username);
        statement.setString(2, password);

        // Execute the SQL statement
        ResultSet resultSet = statement.executeQuery();

        // Check if a result was found
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}

