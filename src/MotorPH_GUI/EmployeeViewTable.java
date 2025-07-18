package MotorPH_GUI;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class EmployeeViewTable extends javax.swing.JFrame {
    
    // Employee data fields
    private JTextField empNumField, lastNameField, firstNameField, sssField;
    private JTextField philhealthField, tinField, pagibigField, birthdayField;
    private JTextField basicSalaryField, hourlyRateField, addressField, phoneField;
    private JTextField statusField, positionField, supervisorField;
    private JTextField riceSubsidyField, phoneAllowanceField, clothingAllowanceField, grossSemiMonthlyField;
    
    // Buttons
    private JButton updateButton, deleteButton;
    
    // Current selected employee data
    private String[] currentEmployeeData;

    /**
     * Creates new form Main
     */
    public EmployeeViewTable() {
        initComponents();
        loadEmployeeData();
        setupTableSelectionListener();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MOTORPHMUTIPLE = new javax.swing.JTable();
        newempbtn = new javax.swing.JButton();
        viewempbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MOTORPH PAYROLL SYSTEM");

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 36));
        jLabel1.setText("MOTORPH PAYROLL SYSTEM");

        MOTORPHMUTIPLE.setModel(new javax.swing.table.DefaultTableModel());
        MOTORPHMUTIPLE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(MOTORPHMUTIPLE);

        newempbtn.setText("NEW EMPLOYEE");
        newempbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newempbtnActionPerformed(evt);
            }
        });

        viewempbtn.setText("VIEW EMPLOYEE");
        viewempbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewempbtnActionPerformed(evt);
            }
        });

        // Create employee details panel
        JPanel employeeDetailsPanel = createEmployeeDetailsPanel();

        // Main layout
        setLayout(new BorderLayout());
        
        // Top panel with title and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(jLabel1);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(newempbtn);
        buttonPanel.add(viewempbtn);
        
        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Center panel with table and employee details
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(jScrollPane1, BorderLayout.NORTH);
        centerPanel.add(employeeDetailsPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createEmployeeDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Initialize text fields
        empNumField = new JTextField(15);
        empNumField.setEditable(false); // Employee number should not be editable
        lastNameField = new JTextField(15);
        firstNameField = new JTextField(15);
        birthdayField = new JTextField(15);
        basicSalaryField = new JTextField(15);
        hourlyRateField = new JTextField(15);
        addressField = new JTextField(20);
        phoneField = new JTextField(15);
        sssField = new JTextField(15);
        philhealthField = new JTextField(15);
        tinField = new JTextField(15);
        pagibigField = new JTextField(15);
        statusField = new JTextField(15);
        positionField = new JTextField(15);
        supervisorField = new JTextField(15);
        riceSubsidyField = new JTextField(15);
        phoneAllowanceField = new JTextField(15);
        clothingAllowanceField = new JTextField(15);
        grossSemiMonthlyField = new JTextField(15);
        
        // Add form fields
        int row = 0;
        addFormField(formPanel, gbc, "Employee #:", empNumField, 0, row++);
        addFormField(formPanel, gbc, "Last Name:", lastNameField, 0, row++);
        addFormField(formPanel, gbc, "First Name:", firstNameField, 0, row++);
        addFormField(formPanel, gbc, "Birthday:", birthdayField, 0, row++);
        addFormField(formPanel, gbc, "Basic Salary:", basicSalaryField, 0, row++);
        addFormField(formPanel, gbc, "Hourly Rate:", hourlyRateField, 0, row++);
        addFormField(formPanel, gbc, "Address:", addressField, 0, row++);
        addFormField(formPanel, gbc, "Phone:", phoneField, 0, row++);
        addFormField(formPanel, gbc, "SSS #:", sssField, 0, row++);
        addFormField(formPanel, gbc, "PhilHealth #:", philhealthField, 0, row++);
        
        // Second column
        row = 0;
        addFormField(formPanel, gbc, "TIN #:", tinField, 2, row++);
        addFormField(formPanel, gbc, "Pag-IBIG #:", pagibigField, 2, row++);
        addFormField(formPanel, gbc, "Status:", statusField, 2, row++);
        addFormField(formPanel, gbc, "Position:", positionField, 2, row++);
        addFormField(formPanel, gbc, "Supervisor:", supervisorField, 2, row++);
        addFormField(formPanel, gbc, "Rice Subsidy:", riceSubsidyField, 2, row++);
        addFormField(formPanel, gbc, "Phone Allowance:", phoneAllowanceField, 2, row++);
        addFormField(formPanel, gbc, "Clothing Allowance:", clothingAllowanceField, 2, row++);
        addFormField(formPanel, gbc, "Gross Semi-Monthly:", grossSemiMonthlyField, 2, row++);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        
        updateButton.addActionListener(this::updateEmployee);
        deleteButton.addActionListener(this::deleteEmployee);
        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        
        panel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int col, int row) {
        gbc.gridx = col;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = col + 1;
        panel.add(field, gbc);
    }
    
    private void setupTableSelectionListener() {
        MOTORPHMUTIPLE.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = MOTORPHMUTIPLE.getSelectedRow();
                if (selectedRow >= 0) {
                    String employeeId = (String) MOTORPHMUTIPLE.getValueAt(selectedRow, 0);
                    loadEmployeeDetails(employeeId);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    clearEmployeeDetails();
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
    }
    
    private void loadEmployeeDetails(String employeeId) {
        currentEmployeeData = EmployeeUtil.getEmployeeById("src/MotorPH_CSVFiles/Emp_Details.csv", employeeId);
        if (currentEmployeeData != null && currentEmployeeData.length >= 19) {
            empNumField.setText(currentEmployeeData[0]);
            lastNameField.setText(currentEmployeeData[1]);
            firstNameField.setText(currentEmployeeData[2]);
            birthdayField.setText(currentEmployeeData[3]);
            basicSalaryField.setText(currentEmployeeData[4]);
            hourlyRateField.setText(currentEmployeeData[5]);
            addressField.setText(currentEmployeeData[6]);
            phoneField.setText(currentEmployeeData[7]);
            sssField.setText(currentEmployeeData[8]);
            philhealthField.setText(currentEmployeeData[9]);
            tinField.setText(currentEmployeeData[10]);
            pagibigField.setText(currentEmployeeData[11]);
            statusField.setText(currentEmployeeData[12]);
            positionField.setText(currentEmployeeData[13]);
            supervisorField.setText(currentEmployeeData[14]);
            riceSubsidyField.setText(currentEmployeeData[15]);
            phoneAllowanceField.setText(currentEmployeeData[16]);
            clothingAllowanceField.setText(currentEmployeeData[17]);
            grossSemiMonthlyField.setText(currentEmployeeData[18]);
        }
    }
    
    private void clearEmployeeDetails() {
        empNumField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        birthdayField.setText("");
        basicSalaryField.setText("");
        hourlyRateField.setText("");
        addressField.setText("");
        phoneField.setText("");
        sssField.setText("");
        philhealthField.setText("");
        tinField.setText("");
        pagibigField.setText("");
        statusField.setText("");
        positionField.setText("");
        supervisorField.setText("");
        riceSubsidyField.setText("");
        phoneAllowanceField.setText("");
        clothingAllowanceField.setText("");
        grossSemiMonthlyField.setText("");
        currentEmployeeData = null;
    }
    
    private void updateEmployee(ActionEvent evt) {
        if (currentEmployeeData == null) {
            JOptionPane.showMessageDialog(this, "No employee selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Get updated data from fields
            String[] updatedData = {
                empNumField.getText().trim(),
                lastNameField.getText().trim(),
                firstNameField.getText().trim(),
                birthdayField.getText().trim(),
                basicSalaryField.getText().trim(),
                hourlyRateField.getText().trim(),
                addressField.getText().trim(),
                phoneField.getText().trim(),
                sssField.getText().trim(),
                philhealthField.getText().trim(),
                tinField.getText().trim(),
                pagibigField.getText().trim(),
                statusField.getText().trim(),
                positionField.getText().trim(),
                supervisorField.getText().trim(),
                riceSubsidyField.getText().trim(),
                phoneAllowanceField.getText().trim(),
                clothingAllowanceField.getText().trim(),
                grossSemiMonthlyField.getText().trim()
            };
            
            // Validate required fields
            if (updatedData[0].isEmpty() || updatedData[1].isEmpty() || updatedData[2].isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee #, Last Name, and First Name are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update CSV file
            String employeeId = updatedData[0];
            if (EmployeeUtil.updateEmployeeById("src/MotorPH_CSVFiles/Emp_Details.csv", employeeId, updatedData)) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadEmployeeData(); // Refresh table
                clearEmployeeDetails();
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEmployee(ActionEvent evt) {
        if (currentEmployeeData == null) {
            JOptionPane.showMessageDialog(this, "No employee selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete employee " + currentEmployeeData[1] + ", " + currentEmployeeData[2] + "?",
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String employeeId = currentEmployeeData[0];
                if (EmployeeUtil.deleteEmployeeById("src/MOTORPH_CSVFILES/Emp_Details.csv", employeeId)) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadEmployeeData(); // Refresh table
                    clearEmployeeDetails();
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void newempbtnActionPerformed(java.awt.event.ActionEvent evt) {
        NewEmployee newEmpForm = new NewEmployee();
        newEmpForm.setVisible(true);
        this.setVisible(false);

        // Add window listener to refresh data when returning
        newEmpForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                EmployeeViewTable.this.setVisible(true);
                loadEmployeeData(); // Refresh the employee data
            }
        });
    }

    private void viewempbtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = MOTORPHMUTIPLE.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Please select an employee from the table first.",
                    "No Selection",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the employee ID from the selected row
        String employeeId = (String) MOTORPHMUTIPLE.getValueAt(selectedRow, 0);
        
        // Open employee details viewer
        EmployeeDetailsViewer detailsViewer = new EmployeeDetailsViewer(employeeId);
        detailsViewer.setVisible(true);
    }

    private void loadEmployeeData() {
        try {
            if (!EmployeeUtil.isFileReadable("src/MotorPH_CSVFILES/Emp_Details.csv")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Employee data file not found or cannot be read.",
                        "File Error",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String[]> employeeData = EmployeeUtil.readCSV("src/MotorPH_CSVFILES/Emp_Details.csv");

            // Get table model
            DefaultTableModel model = (DefaultTableModel) MOTORPHMUTIPLE.getModel();

            // Clear existing data
            model.setRowCount(0);

            // Set specific column headers for required fields only
            String[] specificHeaders = {"Employee #", "Last Name", "First Name", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #"};
            model.setColumnCount(specificHeaders.length);
            model.setColumnIdentifiers(specificHeaders);

            // Add data rows with only required columns (skip header)
            if (!employeeData.isEmpty()) {
                for (int i = 1; i < employeeData.size(); i++) {
                    String[] fullRow = employeeData.get(i);
                    // Only add non-empty rows
                    if (fullRow.length > 0 && !fullRow[0].trim().isEmpty()) {
                        // Extract only the required columns: 0, 1, 2, 8, 9, 10, 11
                        String[] specificRow = {
                            fullRow.length > 0 ? fullRow[0] : "",  // Employee #
                            fullRow.length > 1 ? fullRow[1] : "",  // Last Name
                            fullRow.length > 2 ? fullRow[2] : "",  // First Name
                            fullRow.length > 8 ? fullRow[8] : "",  // SSS #
                            fullRow.length > 9 ? fullRow[9] : "",  // Philhealth #
                            fullRow.length > 10 ? fullRow[10] : "", // TIN #
                            fullRow.length > 11 ? fullRow[11] : ""  // Pag-ibig #
                        };
                        model.addRow(specificRow);
                    }
                }
            }

            // Auto-resize columns
            MOTORPHMUTIPLE.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error loading employee data: " + e.getMessage(),
                    "Load Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeViewTable.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeViewTable.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeViewTable.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeViewTable.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EmployeeViewTable().setVisible(true);
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTable MOTORPHMUTIPLE;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newempbtn;
    private javax.swing.JButton viewempbtn;
    // End of variables declaration
}