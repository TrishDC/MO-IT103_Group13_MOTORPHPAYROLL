import java.io.*;
import java.time.*;
import java.time.format.*;

public class EmployeeDetails {
    public static void main(String[] args) {
        String csvFile = "EMPD.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] employeeData = line.split(csvSplitBy);
                Employee employee = createEmployee(employeeData);
                
                if (employee != null) {
                    employee.calculateDeductions();
                    employee.calculateWeeklySalary(40); // 40 hours per week
                    employee.displayResults();
                    System.out.println(); // Add blank line between employees
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static Employee createEmployee(String[] data) {
        try {
            String employeeId = data[0].trim();
            String lastName = data[1].trim();
            String firstName = data[2].trim();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthday = LocalDate.parse(data[3].trim(), formatter);
            
            // Remove quotes and commas from salary
            String salaryStr = data[4].replace("\"", "").replace(",", "");
            double basicSalary = Double.parseDouble(salaryStr);
            
            double hourlyRate = Double.parseDouble(data[5].trim());

            return new Employee(employeeId, firstName, lastName, birthday, basicSalary, hourlyRate);
        } catch (Exception e) {
            System.err.println("Error creating employee from data: " + String.join(",", data));
            return null;
        }
    }

    static class AttendanceRecord {

        public AttendanceRecord() {
        }
    }
}

class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private double basicSalary;
    private double hourlyRate;
    private double pagIbigContribution;
    private double philHealthContribution;
    private double sss;
    private double totalDeductions;
    private double taxableIncome;
    private double withholdingTax;
    private double netPay;
    private double weeklySalary;

    public Employee(String employeeId, String firstName, String lastName, 
                   LocalDate birthday, double basicSalary, double hourlyRate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.basicSalary = basicSalary;
        this.hourlyRate = hourlyRate;
    }

    public void calculateDeductions() {
        calculatePagIbig();
        calculatePhilHealth();
        calculateSSS();
        calculateTotalDeductions();
        calculateTaxableIncome();
        calculateWithholdingTax();
        calculateNetPay();
    }

    private void calculatePagIbig() {
        if (basicSalary >= 1000 && basicSalary < 1500) {
            pagIbigContribution = basicSalary * 0.01;
        } else if (basicSalary >= 1500) {
            pagIbigContribution = basicSalary * 0.02;
        }
        pagIbigContribution = Math.min(pagIbigContribution, 100);
    }

    private void calculatePhilHealth() {
        if (basicSalary >= 10000 && basicSalary <= 60000) {
            philHealthContribution = basicSalary * 0.03;
        }
    }
        
            private void calculateSSS() {
                if (basicSalary < 3250) {
                    sss = 135.00;
                } else if (basicSalary >= 3250 && basicSalary <= 3749.99) {
                    sss = 157.50;
                } else if (basicSalary >= 3750 && basicSalary <= 4249.99) {
                    sss = 180.00;     
                } else if (basicSalary >= 4250 && basicSalary <= 4749.99) {
                    sss = 202.50;
                } else if (basicSalary >= 4750 && basicSalary <= 5249.99) {
                    sss = 225.00;
                } else if (basicSalary >= 5250 && basicSalary <= 5749.99) {
                    sss = 247.50;
                } else if (basicSalary >= 5750 && basicSalary <= 6249.99) {
                    sss = 270.00;
                } else if (basicSalary >= 6250 && basicSalary <= 6749.99) {
                    sss = 292.50;
                } else if (basicSalary >= 6750 && basicSalary <= 7249.99) {
                    sss = 315.00;
                } else if (basicSalary >= 7250 && basicSalary <= 7749.99) {
                    sss = 337.50;
                } else if (basicSalary >= 7750 && basicSalary <= 8249.99) {
                    sss = 360.00;
                } else if (basicSalary >= 8250 && basicSalary <= 8749.99) {
                    sss = 382.50;
                } else if (basicSalary >= 8750 && basicSalary <= 9249.99) {
                    sss = 405.00;
                } else if (basicSalary >= 9250 && basicSalary <= 9749.99) {
                    sss = 427.50;
                } else if (basicSalary >= 9750 && basicSalary <= 10249.99) {
                    sss = 450.00;
                } else if (basicSalary >= 10250 && basicSalary <= 10749.99) {
                    sss = 472.50;
                } else if (basicSalary >= 10750 && basicSalary <= 11249.99) {
                    sss = 495.00;
                } else if (basicSalary >= 11250 && basicSalary <= 11749.99) {
                    sss = 517.50;
                } else if (basicSalary >= 11750 && basicSalary <= 12249.99) {
                    sss = 540.00;
                } else if (basicSalary >= 12250 && basicSalary <= 12749.99) {
                    sss = 562.50;
                } else if (basicSalary >= 12750 && basicSalary <= 13249.99) {
                    sss = 585.00;
                } else if (basicSalary >= 13250 && basicSalary <= 13749.99) {
                    sss = 607.50;
                } else if (basicSalary >= 13750 && basicSalary <= 14249.99) {
                    sss = 630.00;
                } else if (basicSalary >= 14250 && basicSalary <= 14749.99) {
                    sss = 652.50;
                } else if (basicSalary >= 14750 && basicSalary <= 15249.99) {
                    sss = 675.00;
                } else if (basicSalary >= 15250 && basicSalary <= 15749.99) {
                    sss = 697.50;
                } else if (basicSalary >= 15750 && basicSalary <= 16249.99) {
                    sss = 720.00;
                } else if (basicSalary >= 16250 && basicSalary <= 16749.99) {
                    sss = 742.50;
                } else if (basicSalary >= 16750 && basicSalary <= 17249.99) {
                    sss = 765.00;
                } else if (basicSalary >= 17250 && basicSalary <= 17749.99) {
                    sss = 787.50;
                } else if (basicSalary >= 17750 && basicSalary <= 18249.99) {
                    sss = 810.00;
                } else if (basicSalary >= 18250 && basicSalary <= 18749.99) {
                    sss = 832.50;
                } else if (basicSalary >= 18750 && basicSalary <= 19249.99) {
                    sss = 855.00;
                } else if (basicSalary >= 19250 && basicSalary <= 19749.99) {
                    sss = 877.50;
                } else if (basicSalary >= 19750 && basicSalary <= 20249.99) {
                    sss = 900.00;
                } else if (basicSalary >= 20250 && basicSalary <= 20749.99) {
                    sss = 922.50;
                } else if (basicSalary >= 20750 && basicSalary <= 21249.99) {
                    sss = 945.00;
                } else if (basicSalary >= 21250 && basicSalary <= 21749.99) {
                    sss = 967.50;
                } else if (basicSalary >= 21750 && basicSalary <= 22249.99) {
                    sss = 990.00;
                } else if (basicSalary >= 22250 && basicSalary <= 22749.99) {
                    sss = 1012.50;
                } else if (basicSalary >= 22750 && basicSalary <= 23249.99) {
                    sss = 1035.00;
                } else if (basicSalary >= 23250 && basicSalary <= 23749.99) {
                    sss = 1057.50;
                } else if (basicSalary >= 23750 && basicSalary <= 24249.99) {
                    sss = 1080.00;
                } else if (basicSalary >= 24250 && basicSalary <= 24749.99) {
                    sss = 1102.50;
                } else if (basicSalary >= 24750) {
                    sss = 1125.00; 
                }
            }

            private void calculateTotalDeductions() {
                totalDeductions = pagIbigContribution + philHealthContribution + sss;
            }
        
            private void calculateTaxableIncome() {
                taxableIncome = basicSalary - totalDeductions;
            }
        
            private void calculateWithholdingTax() {
                if (taxableIncome < 20833) {
                    withholdingTax = 0;
                } else if (taxableIncome < 33333) {
                    withholdingTax = (taxableIncome - 20833) * 0.20;
                } else if (taxableIncome < 66667) {
                    withholdingTax = 2500 + (taxableIncome - 33333) * 0.25;
                } else if (taxableIncome < 166667) {
                    withholdingTax = 10833 + (taxableIncome - 66667) * 0.30;
                } else if (taxableIncome < 666667) {
                    withholdingTax = 40833.33 + (taxableIncome - 166667) * 0.32;
                } else {
                    withholdingTax = 200833.33 + (taxableIncome - 666667) * 0.35;
                }
            }
        



            
            private void calculateNetPay() {
                netPay = taxableIncome - withholdingTax;
            }
        
            public void calculateWeeklySalary(double hoursWorked) {
                weeklySalary = hourlyRate * hoursWorked;
            }
        
            public void displayResults() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                System.out.println("\n========== Employee Details ==========");
                System.out.printf("ID: %s%n", employeeId);
                System.out.printf("Name: %s %s%n", firstName, lastName);
                System.out.printf("Birthday: %s%n", birthday.format(formatter));
                System.out.printf("Hourly Rate: ₱%,.2f%n", hourlyRate);
                System.out.printf("Weekly Salary: ₱%,.2f%n", weeklySalary);
                System.out.printf("Basic Monthly Salary: ₱%,.2f%n", basicSalary);
                System.out.println("\nDeductions:");
                System.out.printf("Pag-IBIG Contribution: ₱%,.2f%n", pagIbigContribution);
                System.out.printf("PhilHealth Contribution: ₱%,.2f%n", philHealthContribution);
                System.out.printf("SSS Contribution: ₱%,.2f%n", sss);
                System.out.printf("Total Deductions: ₱%,.2f%n", totalDeductions);
                System.out.printf("Taxable Income: ₱%,.2f%n", taxableIncome);
                System.out.printf("Withholding Tax: ₱%,.2f%n", withholdingTax);
                System.out.printf("Net Pay: ₱%,.2f%n", netPay);
                System.out.println("===================================");
            }
        }

