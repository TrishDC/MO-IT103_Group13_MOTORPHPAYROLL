
package MotorPH_GUI;

import com.opencsv.*;
import java.io.*;
import java.util.*;

public class LoginCredentialsGenerator {
    private static final String EMPLOYEE_CSV = "src/MotorPH_CSVFILES/Emp_Details.csv";
    private static final String LOGIN_CSV = "src/MotorPH_CSVFILES/Login_Credentials.csv";
    
    public static void generateLoginCredentials() {
        try {
            List<String[]> employees = EmployeeUtil.readCSV(EMPLOYEE_CSV);
            List<String[]> loginData = new ArrayList<>();
            
            // Add header
            loginData.add(new String[]{"Username", "Password", "EmployeeID", "FullName"});
            
            // Skip header row and process each employee
            for (int i = 1; i < employees.size(); i++) {
                String[] employee = employees.get(i);
                if (employee.length >= 3 && !employee[0].trim().isEmpty()) {
                    String employeeId = employee[0].trim();
                    String firstName = employee[2].trim();
                    String lastName = employee[1].trim();
                    
                    if (!firstName.isEmpty() && !lastName.isEmpty()) {
                        // Generate username: first letter of first name + last name (lowercase)
                        String username = (firstName.charAt(0) + lastName).toLowerCase().replace(" ", "");
                        
                        // Generate password: last name + employee ID
                        String password = lastName.replace(" ", "") + employeeId;
                        
                        // Full name for reference
                        String fullName = firstName + " " + lastName;
                        
                        loginData.add(new String[]{username, password, employeeId, fullName});
                    }
                }
            }
            
            // Write to CSV
            EmployeeUtil.writeCSV(LOGIN_CSV, loginData, false);
            System.out.println("Login credentials generated successfully!");
            
        } catch (IOException e) {
            System.err.println("Error generating login credentials: " + e.getMessage());
        }
    }
    
    public static boolean validateLogin(String username, String password) {
        try {
            List<String[]> loginData = EmployeeUtil.readCSV(LOGIN_CSV);
            
            // Skip header and check each credential
            for (int i = 1; i < loginData.size(); i++) {
                String[] credential = loginData.get(i);
                if (credential.length >= 2) {
                    String storedUsername = credential[0].trim();
                    String storedPassword = credential[1].trim();
                    
                    if (storedUsername.equals(username.trim()) && storedPassword.equals(password.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error validating login: " + e.getMessage());
        }
        
        return false;
    }
    
    public static String getEmployeeIdByLogin(String username, String password) {
        try {
            List<String[]> loginData = EmployeeUtil.readCSV(LOGIN_CSV);
            
            // Skip header and check each credential
            for (int i = 1; i < loginData.size(); i++) {
                String[] credential = loginData.get(i);
                if (credential.length >= 3) {
                    String storedUsername = credential[0].trim();
                    String storedPassword = credential[1].trim();
                    
                    if (storedUsername.equals(username.trim()) && storedPassword.equals(password.trim())) {
                        return credential[2].trim(); // Return employee ID
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting employee ID: " + e.getMessage());
        }
        
        return null;
    }
}
