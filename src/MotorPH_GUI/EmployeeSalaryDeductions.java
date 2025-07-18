package MotorPH_GUI;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.*;

public class EmployeeSalaryDeductions {
    private static final String CSV_FILE = "src/MotorPH_CSVFiles/Emp_Details.csv";

    public static void main(String[] args) {
        String csvFile = CSV_FILE;

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();

            // Skip header (first row)
            for (int i = 1; i < records.size(); i++) {
                String[] employeeData = records.get(i);
                Employee employee = createEmployee(employeeData);

                if (employee != null) {
                    employee.calculateDeductions();
                    employee.calculateWeeklySalary(40); // 40 hours per week
                    employee.displayResults();
                    System.out.println(); // Add blank line between employees
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static Employee findEmployeeByNumber(String employeeNumber) {
        String csvFile = CSV_FILE;

        // Check if file exists
        java.io.File file = new java.io.File(csvFile);
        if (!file.exists()) {
            System.err.println("Employee CSV file not found: " + csvFile);
            return null;
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();

            // Skip header (first row)
            for (int i = 1; i < records.size(); i++) {
                String[] employeeData = records.get(i);
                if (employeeData.length > 0 && employeeData[0].trim().equals(employeeNumber.trim())) {
                    return createEmployee(employeeData);
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return null;
    }

    private static Employee createEmployee(String[] data) {
        if (data.length < 19) {
            System.err.println("Insufficient employee data");
            return null;
        }

        try {
            String employeeNumber = data[0];
            String lastName = data[1];
            String firstName = data[2];
            String birthday = data[3];
            double basicSalary = parseDouble(data[4]);
            double hourlyRate = parseDouble(data[5]);
            String address = data[6];
            String phoneNumber = data[7];
            String sss = data[8];
            String philHealth = data[9];
            String tin = data[10];
            String pagIbig = data[11];
            String status = data[12];
            String position = data[13];
            String supervisor = data[14];
            double riceSubsidy = parseDouble(data[15]);
            double phoneAllowance = parseDouble(data[16]);
            double clothingAllowance = parseDouble(data[17]);
            double grossSemiMonthly = parseDouble(data[18]);

            return new Employee(employeeNumber, lastName, firstName, birthday, basicSalary,
                    hourlyRate, address, phoneNumber, sss, philHealth, tin, pagIbig,
                    status, position, supervisor, riceSubsidy, phoneAllowance,
                    clothingAllowance, grossSemiMonthly);

        } catch (NumberFormatException e) {
            System.err.println("Error parsing employee data: " + e.getMessage());
            return null;
        }
    }

    private static double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}

class Employee {
    private final String employeeNumber;
    private final String lastName;
    private final String firstName;
    private final String birthday;
    private final double basicSalary;
    private final double hourlyRate;
    private final String address;
    private final String phoneNumber;
    private final String sss;
    private final String philHealth;
    private final String tin;
    private final String pagIbig;
    private final String status;
    private final String position;
    private final String supervisor;
    private final double riceSubsidy;
    private final double phoneAllowance;
    private final double clothingAllowance;
    private final double grossSemiMonthly;

    // Deduction fields
    private double sssDeduction;
    private double philHealthDeduction;
    private double pagIbigDeduction;
    private double totalDeductions;
    private double weeklySalary;

    public Employee(String employeeNumber, String lastName, String firstName, String birthday,
                   double basicSalary, double hourlyRate, String address, String phoneNumber,
                   String sss, String philHealth, String tin, String pagIbig, String status,
                   String position, String supervisor, double riceSubsidy, double phoneAllowance,
                   double clothingAllowance, double grossSemiMonthly) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.basicSalary = basicSalary;
        this.hourlyRate = hourlyRate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sss = sss;
        this.philHealth = philHealth;
        this.tin = tin;
        this.pagIbig = pagIbig;
        this.status = status;
        this.position = position;
        this.supervisor = supervisor;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthly = grossSemiMonthly;
    }

    public void calculateDeductions() {
        // SSS Deduction calculation based on salary bracket
        if (basicSalary <= 3250) sssDeduction = 135;
        else if (basicSalary <= 3750) sssDeduction = 157.5;
        else if (basicSalary <= 4250) sssDeduction = 180;
        else if (basicSalary <= 4750) sssDeduction = 202.5;
        else if (basicSalary <= 5250) sssDeduction = 225;
        else if (basicSalary <= 5750) sssDeduction = 247.5;
        else if (basicSalary <= 6250) sssDeduction = 270;
        else if (basicSalary <= 6750) sssDeduction = 292.5;
        else if (basicSalary <= 7250) sssDeduction = 315;
        else if (basicSalary <= 7750) sssDeduction = 337.5;
        else if (basicSalary <= 8250) sssDeduction = 360;
        else if (basicSalary <= 8750) sssDeduction = 382.5;
        else if (basicSalary <= 9250) sssDeduction = 405;
        else if (basicSalary <= 9750) sssDeduction = 427.5;
        else if (basicSalary <= 10250) sssDeduction = 450;
        else if (basicSalary <= 10750) sssDeduction = 472.5;
        else if (basicSalary <= 11250) sssDeduction = 495;
        else if (basicSalary <= 11750) sssDeduction = 517.5;
        else if (basicSalary <= 12250) sssDeduction = 540;
        else if (basicSalary <= 12750) sssDeduction = 562.5;
        else if (basicSalary <= 13250) sssDeduction = 585;
        else if (basicSalary <= 13750) sssDeduction = 607.5;
        else if (basicSalary <= 14250) sssDeduction = 630;
        else if (basicSalary <= 14750) sssDeduction = 652.5;
        else if (basicSalary <= 15250) sssDeduction = 675;
        else if (basicSalary <= 15750) sssDeduction = 697.5;
        else if (basicSalary <= 16250) sssDeduction = 720;
        else if (basicSalary <= 16750) sssDeduction = 742.5;
        else if (basicSalary <= 17250) sssDeduction = 765;
        else if (basicSalary <= 17750) sssDeduction = 787.5;
        else if (basicSalary <= 18250) sssDeduction = 810;
        else if (basicSalary <= 18750) sssDeduction = 832.5;
        else if (basicSalary <= 19250) sssDeduction = 855;
        else if (basicSalary <= 19750) sssDeduction = 877.5;
        else sssDeduction = 900; // Maximum SSS contribution

        // PhilHealth Deduction (1.5% of basic salary)
        philHealthDeduction = basicSalary * 0.015;

        // Pag-IBIG Deduction (1% of basic salary, max 100)
        pagIbigDeduction = Math.min(basicSalary * 0.01, 100);

        totalDeductions = sssDeduction + philHealthDeduction + pagIbigDeduction;
    }

    public void calculateWeeklySalary(int hoursWorked) {
        weeklySalary = (hourlyRate * hoursWorked) - (totalDeductions / 4);
    }

    public void displayResults() {
        System.out.println("Employee: " + firstName + " " + lastName);
        System.out.println("Employee Number: " + employeeNumber);
        System.out.println("Position: " + position);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Hourly Rate: " + hourlyRate);
        System.out.println("SSS Deduction: " + sssDeduction);
        System.out.println("PhilHealth Deduction: " + String.format("%.2f", philHealthDeduction));
        System.out.println("Pag-IBIG Deduction: " + String.format("%.2f", pagIbigDeduction));
        System.out.println("Total Deductions: " + String.format("%.2f", totalDeductions));
        System.out.println("Weekly Salary (40 hours): " + String.format("%.2f", weeklySalary));
    }

    // Getters
    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public double getBasicSalary() { return basicSalary; }
    public double getSssDeduction() { return sssDeduction; }
    public double getPhilHealthDeduction() { return philHealthDeduction; }
    public double getPagIbigDeduction() { return pagIbigDeduction; }
    public double getTotalDeductions() { return totalDeductions; }
    public double getWeeklySalary() { return weeklySalary; }
}