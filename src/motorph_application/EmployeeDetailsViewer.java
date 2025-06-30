package MotorPHGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class EmployeeDetailsViewer extends JFrame {
    private String employeeId;
    private String[] employeeData;

    // Employee details components
    private JTextField empNumField, lastNameField, firstNameField, birthdayField;
    private JTextField basicSalaryField, hourlyRateField, addressField, phoneField;
    private JTextField sssField, philhealthField, tinField, pagibigField;
    private JTextField statusField, positionField, supervisorField;
    private JTextField riceSubsidyField, phoneAllowanceField, clothingAllowanceField, grossSemiMonthlyField;

    // Salary computation components
    private JComboBox<String> monthComboBox;
    private JButton computeButton;
    private JTextArea salaryResultArea;

    public EmployeeDetailsViewer(String employeeId) {
        this.employeeId = employeeId;
        loadEmployeeData();
        initComponents();
        populateEmployeeDetails();
    }

    private void loadEmployeeData() {
        employeeData = EmployeeUtil.getEmployeeById("src/MotorPH_CSVFiles/Emp_Details.csv", employeeId);
        if (employeeData == null) {
            JOptionPane.showMessageDialog(this, 
                "Employee not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void initComponents() {
        setTitle("Employee Details - ID: " + employeeId);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Employee Details Tab
        JPanel detailsPanel = createEmployeeDetailsPanel();
        tabbedPane.addTab("Employee Details", detailsPanel);

        // Salary Computation Tab
        JPanel salaryPanel = createSalaryComputationPanel();
        tabbedPane.addTab("Salary Computation", salaryPanel);

        add(tabbedPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createEmployeeDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize text fields
        empNumField = new JTextField(20);
        empNumField.setEditable(false);
        lastNameField = new JTextField(20);
        lastNameField.setEditable(false);
        firstNameField = new JTextField(20);
        firstNameField.setEditable(false);
        birthdayField = new JTextField(20);
        birthdayField.setEditable(false);
        basicSalaryField = new JTextField(20);
        basicSalaryField.setEditable(false);
        hourlyRateField = new JTextField(20);
        hourlyRateField.setEditable(false);
        addressField = new JTextField(20);
        addressField.setEditable(false);
        phoneField = new JTextField(20);
        phoneField.setEditable(false);
        sssField = new JTextField(20);
        sssField.setEditable(false);
        philhealthField = new JTextField(20);
        philhealthField.setEditable(false);
        tinField = new JTextField(20);
        tinField.setEditable(false);
        pagibigField = new JTextField(20);
        pagibigField.setEditable(false);
        statusField = new JTextField(20);
        statusField.setEditable(false);
        positionField = new JTextField(20);
        positionField.setEditable(false);
        supervisorField = new JTextField(20);
        supervisorField.setEditable(false);
        riceSubsidyField = new JTextField(20);
        riceSubsidyField.setEditable(false);
        phoneAllowanceField = new JTextField(20);
        phoneAllowanceField.setEditable(false);
        clothingAllowanceField = new JTextField(20);
        clothingAllowanceField.setEditable(false);
        grossSemiMonthlyField = new JTextField(20);
        grossSemiMonthlyField.setEditable(false);

        // Add components to panel
        int row = 0;
        addLabelAndField(panel, gbc, "Employee #:", empNumField, row++);
        addLabelAndField(panel, gbc, "Last Name:", lastNameField, row++);
        addLabelAndField(panel, gbc, "First Name:", firstNameField, row++);
        addLabelAndField(panel, gbc, "Birthday:", birthdayField, row++);
        addLabelAndField(panel, gbc, "Basic Salary:", basicSalaryField, row++);
        addLabelAndField(panel, gbc, "Hourly Rate:", hourlyRateField, row++);
        addLabelAndField(panel, gbc, "Address:", addressField, row++);
        addLabelAndField(panel, gbc, "Phone Number:", phoneField, row++);
        addLabelAndField(panel, gbc, "SSS #:", sssField, row++);
        addLabelAndField(panel, gbc, "PhilHealth #:", philhealthField, row++);
        addLabelAndField(panel, gbc, "TIN #:", tinField, row++);
        addLabelAndField(panel, gbc, "Pag-ibig #:", pagibigField, row++);
        addLabelAndField(panel, gbc, "Status:", statusField, row++);
        addLabelAndField(panel, gbc, "Position:", positionField, row++);
        addLabelAndField(panel, gbc, "Supervisor:", supervisorField, row++);
        addLabelAndField(panel, gbc, "Rice Subsidy:", riceSubsidyField, row++);
        addLabelAndField(panel, gbc, "Phone Allowance:", phoneAllowanceField, row++);
        addLabelAndField(panel, gbc, "Clothing Allowance:", clothingAllowanceField, row++);
        addLabelAndField(panel, gbc, "Gross Semi-monthly Rate:", grossSemiMonthlyField, row++);

        return panel;
    }

    private JPanel createSalaryComputationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel for month selection
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Month:"));

        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        monthComboBox = new JComboBox<>(months);
        topPanel.add(monthComboBox);

        computeButton = new JButton("Compute Salary");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });
        topPanel.add(computeButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center panel for results
        salaryResultArea = new JTextArea(20, 50);
        salaryResultArea.setEditable(false);
        salaryResultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(salaryResultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void populateEmployeeDetails() {
        if (employeeData != null && employeeData.length >= 19) {
            empNumField.setText(employeeData[0]);
            lastNameField.setText(employeeData[1]);
            firstNameField.setText(employeeData[2]);
            birthdayField.setText(employeeData[3]);
            basicSalaryField.setText(employeeData[4]);
            hourlyRateField.setText(employeeData[5]);
            addressField.setText(employeeData[6]);
            phoneField.setText(employeeData[7]);
            sssField.setText(employeeData[8]);
            philhealthField.setText(employeeData[9]);
            tinField.setText(employeeData[10]);
            pagibigField.setText(employeeData[11]);
            statusField.setText(employeeData[12]);
            positionField.setText(employeeData[13]);
            supervisorField.setText(employeeData[14]);
            riceSubsidyField.setText(employeeData[15]);
            phoneAllowanceField.setText(employeeData[16]);
            clothingAllowanceField.setText(employeeData[17]);
            grossSemiMonthlyField.setText(employeeData[18]);
        }
    }

    private void computeSalary() {
        try {
            String selectedMonth = (String) monthComboBox.getSelectedItem();

            // Use the Employee class from EmployeeSalaryDeductions for accurate calculations
            Employee employee = EmployeeSalaryDeductions.findEmployeeById(employeeId);
            if (employee == null) {
                JOptionPane.showMessageDialog(this,
                        "Could not load employee data for salary calculation",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate deductions using the proper logic
            employee.calculateDeductions();
            employee.calculateWeeklySalary(40); // Assuming 40 hours per week

            // Parse additional allowances from employee data
            double riceSubsidy = parseAmount(employeeData[15]);
            double phoneAllowance = parseAmount(employeeData[16]);
            double clothingAllowance = parseAmount(employeeData[17]);

            // Calculate total earnings including allowances
            double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
            double adjustedGross = employee.getBasicSalary() + totalAllowances;

            // Recalculate deductions with allowances included
            double adjustedSss = calculateSSS(adjustedGross);
            double adjustedPhilHealth = calculatePhilHealth(adjustedGross);
            double adjustedPagIbig = calculatePagIbig(adjustedGross);

            double totalDeductionsWithAllowances = adjustedSss + adjustedPhilHealth + adjustedPagIbig;
            double taxableIncomeWithAllowances = adjustedGross - totalDeductionsWithAllowances;
            double withholdingTaxWithAllowances = calculateWithholdingTax(taxableIncomeWithAllowances);
            double netPayWithAllowances = taxableIncomeWithAllowances - withholdingTaxWithAllowances;

            DecimalFormat df = new DecimalFormat("#,##0.00");

            StringBuilder result = new StringBuilder();
            result.append("=".repeat(60)).append("\n");
            result.append("SALARY COMPUTATION FOR ").append(selectedMonth.toUpperCase()).append("\n");
            result.append("=".repeat(60)).append("\n\n");

            result.append("EMPLOYEE INFORMATION:\n");
            result.append("-".repeat(40)).append("\n");
            result.append("Employee #: ").append(employee.getEmployeeId()).append("\n");
            result.append("Name: ").append(employee.getLastName()).append(", ").append(employee.getFirstName()).append("\n");
            result.append("Position: ").append(employeeData[13]).append("\n");
            result.append("Status: ").append(employeeData[12]).append("\n");
            result.append("Birthday: ").append(employee.getBirthday().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))).append("\n\n");

            result.append("EARNINGS:\n");
            result.append("-".repeat(40)).append("\n");
            result.append("Basic Monthly Salary: ₱").append(df.format(employee.getBasicSalary())).append("\n");
            result.append("Hourly Rate: ₱").append(df.format(employee.getHourlyRate())).append("\n");
            result.append("Weekly Salary (40 hrs): ₱").append(df.format(employee.getWeeklySalary())).append("\n");
            result.append("Rice Subsidy: ₱").append(df.format(riceSubsidy)).append("\n");
            result.append("Phone Allowance: ₱").append(df.format(phoneAllowance)).append("\n");
            result.append("Clothing Allowance: ₱").append(df.format(clothingAllowance)).append("\n");
            result.append("TOTAL GROSS: ₱").append(df.format(adjustedGross)).append("\n\n");

            result.append("DEDUCTIONS:\n");
            result.append("-".repeat(40)).append("\n");
            result.append("SSS Contribution: ₱").append(df.format(adjustedSss)).append("\n");
            result.append("PhilHealth Contribution: ₱").append(df.format(adjustedPhilHealth)).append("\n");
            result.append("Pag-ibig Contribution: ₱").append(df.format(adjustedPagIbig)).append("\n");
            result.append("Withholding Tax: ₱").append(df.format(withholdingTaxWithAllowances)).append("\n");
            result.append("TOTAL DEDUCTIONS: ₱").append(df.format(totalDeductionsWithAllowances + withholdingTaxWithAllowances)).append("\n\n");

            result.append("SUMMARY:\n");
            result.append("-".repeat(40)).append("\n");
            result.append("Taxable Income: ₱").append(df.format(taxableIncomeWithAllowances)).append("\n");
            result.append("NET PAY: ₱").append(df.format(netPayWithAllowances)).append("\n");
            result.append("=".repeat(60)).append("\n");

            salaryResultArea.setText(result.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error computing salary: " + e.getMessage(),
                    "Computation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return 0.0;
        }
        // Remove commas and other formatting
        String cleanAmount = amountStr.replaceAll("[,₱]", "").trim();
        try {
            return Double.parseDouble(cleanAmount);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double calculateSSS(double basicSalary) {
        if (basicSalary < 3250) return 135.00;
        else if (basicSalary <= 3749.99) return 157.50;
        else if (basicSalary <= 4249.99) return 180.00;
        else if (basicSalary <= 4749.99) return 202.50;
        else if (basicSalary <= 5249.99) return 225.00;
        else if (basicSalary <= 5749.99) return 247.50;
        else if (basicSalary <= 6249.99) return 270.00;
        else if (basicSalary <= 6749.99) return 292.50;
        else if (basicSalary <= 7249.99) return 315.00;
        else if (basicSalary <= 7749.99) return 337.50;
        else if (basicSalary <= 8249.99) return 360.00;
        else if (basicSalary <= 8749.99) return 382.50;
        else if (basicSalary <= 9249.99) return 405.00;
        else if (basicSalary <= 9749.99) return 427.50;
        else if (basicSalary <= 10249.99) return 450.00;
        else if (basicSalary <= 10749.99) return 472.50;
        else if (basicSalary <= 11249.99) return 495.00;
        else if (basicSalary <= 11749.99) return 517.50;
        else if (basicSalary <= 12249.99) return 540.00;
        else if (basicSalary <= 12749.99) return 562.50;
        else if (basicSalary <= 13249.99) return 585.00;
        else if (basicSalary <= 13749.99) return 607.50;
        else if (basicSalary <= 14249.99) return 630.00;
        else if (basicSalary <= 14749.99) return 652.50;
        else if (basicSalary <= 15249.99) return 675.00;
        else if (basicSalary <= 15749.99) return 697.50;
        else if (basicSalary <= 16249.99) return 720.00;
        else if (basicSalary <= 16749.99) return 742.50;
        else if (basicSalary <= 17249.99) return 765.00;
        else if (basicSalary <= 17749.99) return 787.50;
        else if (basicSalary <= 18249.99) return 810.00;
        else if (basicSalary <= 18749.99) return 832.50;
        else if (basicSalary <= 19249.99) return 855.00;
        else if (basicSalary <= 19749.99) return 877.50;
        else if (basicSalary <= 20249.99) return 900.00;
        else if (basicSalary <= 20749.99) return 922.50;
        else if (basicSalary <= 21249.99) return 945.00;
        else if (basicSalary <= 21749.99) return 967.50;
        else if (basicSalary <= 22249.99) return 990.00;
        else if (basicSalary <= 22749.99) return 1012.50;
        else if (basicSalary <= 23249.99) return 1035.00;
        else if (basicSalary <= 23749.99) return 1057.50;
        else if (basicSalary <= 24249.99) return 1080.00;
        else if (basicSalary <= 24749.99) return 1102.50;
        else return 1125.00;
    }

    private double calculatePhilHealth(double basicSalary) {
        if (basicSalary >= 10000 && basicSalary <= 60000) {
            return basicSalary * 0.03;
        }
        return 0;
    }

    private double calculatePagIbig(double basicSalary) {
        double contribution = 0;
        if (basicSalary >= 1000 && basicSalary < 1500) {
            contribution = basicSalary * 0.01;
        } else if (basicSalary >= 1500) {
            contribution = basicSalary * 0.02;
        }
        return Math.min(contribution, 100);
    }

    private double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome < 20833) return 0;
        else if (taxableIncome < 33333) return (taxableIncome - 20833) * 0.20;
        else if (taxableIncome < 66667) return 2500 + (taxableIncome - 33333) * 0.25;
        else if (taxableIncome < 166667) return 10833 + (taxableIncome - 66667) * 0.30;
        else if (taxableIncome < 666667) return 40833.33 + (taxableIncome - 166667) * 0.32;
        else return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}