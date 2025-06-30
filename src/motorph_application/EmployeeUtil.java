
package MotorPHGUI;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.*;

/**
 * Utility class for handling CSV operations using OpenCSV
 */
public class EmployeeUtil {
    
    /**
     * Reads all lines from a CSV file and returns them as a list of string arrays
     * @param filename The CSV file to read
     * @return List of string arrays, each representing a row
     * @throws IOException if file cannot be read
     */
    public static List<String[]> readCSV(String filename) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (FileReader fr = new FileReader(filename);
             CSVReader csvReader = new CSVReader(fr)) {
            
            records = csvReader.readAll();
        } catch (CsvException e) {
            throw new IOException("Error reading CSV file: " + e.getMessage(), e);
        }
        
        return records;
    }
    
    /**
     * Writes data to a CSV file
     * @param filename The CSV file to write to
     * @param data List of string arrays to write
     * @param append Whether to append to existing file or overwrite
     * @throws IOException if file cannot be written
     */
    public static void writeCSV(String filename, List<String[]> data, boolean append) throws IOException {
        try (FileWriter fw = new FileWriter(filename, append);
             CSVWriter csvWriter = new CSVWriter(fw)) {
            
            csvWriter.writeAll(data);
        }
    }
    
    /**
     * Appends a single row to a CSV file
     * @param filename The CSV file to append to
     * @param row The row data to append
     * @throws IOException if file cannot be written
     */
    public static void appendCSVRow(String filename, String[] row) throws IOException {
        // Ensure proper path resolution
        String fullPath = filename.startsWith("src/") ? filename : "src/MotorPH_CSVFiles/" + filename;
        try (FileWriter fw = new FileWriter(fullPath, true);
             CSVWriter csvWriter = new CSVWriter(fw)) {
            
            csvWriter.writeNext(row);
        }
    }
    
    /**
     * Reads the header row from a CSV file
     * @param filename The CSV file to read
     * @return Array of header column names
     * @throws IOException if file cannot be read
     */
    public static String[] getCSVHeaders(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename);
             CSVReader csvReader = new CSVReader(fr)) {
            
            String[] headers = csvReader.readNext();
            return headers != null ? headers : new String[0];
        } catch (CsvException e) {
            throw new IOException("Error reading CSV headers: " + e.getMessage(), e);
        }
    }
    
    /**
     * Finds rows in CSV that match a specific value in a given column
     * @param filename The CSV file to search
     * @param columnIndex The column index to search in
     * @param value The value to search for
     * @param skipHeader Whether to skip the first row (header)
     * @return List of matching rows
     * @throws IOException if file cannot be read
     */
    public static List<String[]> findRowsByValue(String filename, int columnIndex, String value, boolean skipHeader) throws IOException {
        List<String[]> matchingRows = new ArrayList<>();
        
        try (FileReader fr = new FileReader(filename);
             CSVReader csvReader = new CSVReader(fr)) {
            
            List<String[]> allRows = csvReader.readAll();
            int startIndex = skipHeader ? 1 : 0;
            
            for (int i = startIndex; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length > columnIndex && row[columnIndex] != null) {
                    String cellValue = row[columnIndex].trim();
                    // Skip empty rows
                    if (!cellValue.isEmpty() && cellValue.equals(value.trim())) {
                        matchingRows.add(row);
                    }
                }
            }
        } catch (CsvException e) {
            throw new IOException("Error searching CSV file: " + e.getMessage(), e);
        }
        
        return matchingRows;
    }
    
    /**
     * Checks if a value exists in a specific column of the CSV
     * @param filename The CSV file to check
     * @param columnIndex The column index to check
     * @param value The value to look for
     * @param skipHeader Whether to skip the first row (header)
     * @return true if value exists, false otherwise
     */
    public static boolean valueExistsInColumn(String filename, int columnIndex, String value, boolean skipHeader) {
        try {
            // Ensure proper path resolution
            String fullPath = filename.startsWith("src/") ? filename : "src/MotorPH_CSVFiles/" + filename;
            List<String[]> matches = findRowsByValue(fullPath, columnIndex, value, skipHeader);
            return !matches.isEmpty();
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Gets the next available employee ID from the CSV
     * @param filename The CSV file to check
     * @return The next available employee ID
     */
    public static int getNextEmployeeId(String filename) {
        int maxId = 10000;
        
        // Ensure proper path resolution
        String fullPath = filename.startsWith("src/") ? filename : "src/MotorPH_CSVFiles/" + filename;
        
        try (FileReader fr = new FileReader(fullPath);
             CSVReader csvReader = new CSVReader(fr)) {
            
            List<String[]> allRows = csvReader.readAll();
            
            // Skip header row
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length > 0 && !row[0].trim().isEmpty()) {
                    try {
                        // Remove any quotes or extra characters from employee ID
                        String idStr = row[0].trim().replaceAll("[^0-9]", "");
                        if (!idStr.isEmpty()) {
                            int id = Integer.parseInt(idStr);
                            if (id > maxId) {
                                maxId = id;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid IDs
                    }
                }
            }
        } catch (IOException | CsvException e) {
            // If file doesn't exist or can't be read, start from 10001
            System.err.println("Error reading CSV file for employee ID: " + e.getMessage());
        }
        
        return maxId + 1;
    }
    
    /**
     * Validates if a file exists and is readable
     * @param filename The file to check
     * @return true if file exists and is readable, false otherwise
     */
    public static boolean isFileReadable(String filename) {
        File file = new File(filename);
        return file.exists() && file.canRead();
    }
    
    /**
     * Gets the total number of rows in a CSV file (excluding header if specified)
     * @param filename The CSV file to count
     * @param skipHeader Whether to exclude header row from count
     * @return Number of rows
     * @throws IOException if file cannot be read
     */
    public static int getRowCount(String filename, boolean skipHeader) throws IOException {
        try (FileReader fr = new FileReader(filename);
             CSVReader csvReader = new CSVReader(fr)) {
            
            List<String[]> allRows = csvReader.readAll();
            int count = allRows.size();
            return skipHeader ? Math.max(0, count - 1) : count;
        } catch (CsvException e) {
            throw new IOException("Error counting CSV rows: " + e.getMessage(), e);
        }
    }
    
    /**
     * Creates a CSV file with headers if it doesn't exist
     * @param filename The CSV file to create
     * @param headers The header row to write
     * @throws IOException if file cannot be created
     */
    public static void createCSVWithHeaders(String filename, String[] headers) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            try (FileWriter fw = new FileWriter(filename);
                 CSVWriter csvWriter = new CSVWriter(fw)) {
                
                csvWriter.writeNext(headers);
            }
        }
    }
    
    /**
     * Updates a specific row in the CSV file
     * @param filename The CSV file to update
     * @param rowIndex The index of the row to update (0-based, including header)
     * @param newRowData The new data for the row
     * @throws IOException if file cannot be updated
     */
    public static void updateCSVRow(String filename, int rowIndex, String[] newRowData) throws IOException {
        List<String[]> allRows = readCSV(filename);
        
        if (rowIndex >= 0 && rowIndex < allRows.size()) {
            allRows.set(rowIndex, newRowData);
            writeCSV(filename, allRows, false);
        } else {
            throw new IndexOutOfBoundsException("Row index " + rowIndex + " is out of bounds");
        }
    }
    
    /**
     * Gets employee data by employee ID
     * @param filename The CSV file to search
     * @param employeeId The employee ID to find
     * @return Employee data array or null if not found
     */
    public static String[] getEmployeeById(String filename, String employeeId) {
        try {
            List<String[]> matches = findRowsByValue(filename, 0, employeeId, true);
            return matches.isEmpty() ? null : matches.get(0);
        } catch (IOException e) {
            System.err.println("Error finding employee: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Updates an employee record by employee ID
     * @param filename The CSV file to update
     * @param employeeId The employee ID to find and update
     * @param newData The new employee data
     * @return true if update was successful, false otherwise
     */
    public static boolean updateEmployeeById(String filename, String employeeId, String[] newData) {
        try {
            List<String[]> allRows = readCSV(filename);
            boolean found = false;
            
            // Find and update the employee record
            for (int i = 1; i < allRows.size(); i++) { // Skip header
                String[] row = allRows.get(i);
                if (row.length > 0 && row[0].trim().equals(employeeId.trim())) {
                    allRows.set(i, newData);
                    found = true;
                    break;
                }
            }
            
            if (found) {
                writeCSV(filename, allRows, false);
                return true;
            }
            
        } catch (IOException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Deletes an employee record by employee ID
     * @param filename The CSV file to update
     * @param employeeId The employee ID to delete
     * @return true if deletion was successful, false otherwise
     */
    public static boolean deleteEmployeeById(String filename, String employeeId) {
        try {
            List<String[]> allRows = readCSV(filename);
            boolean found = false;
            
            // Find and remove the employee record
            for (int i = 1; i < allRows.size(); i++) { // Skip header
                String[] row = allRows.get(i);
                if (row.length > 0 && row[0].trim().equals(employeeId.trim())) {
                    allRows.remove(i);
                    found = true;
                    break;
                }
            }
            
            if (found) {
                writeCSV(filename, allRows, false);
                return true;
            }
            
        } catch (IOException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Ensures the CSV file exists with proper headers for Emp_Details.csv
     * @param filename The CSV file to initialize
     */
    public static void initializeEmployeeCSV(String filename) {
        try {
            String[] headers = {
                "Employee #", "Last Name", "First Name", "Birthday", "Basic Salary", 
                "Hourly Rate", "Address", "Phone Number", "SSS #", "Philhealth #", 
                "TIN #", "Pag-ibig #", "Status", "Position", "Immediate Supervisor", 
                "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate"
            };
            createCSVWithHeaders(filename, headers);
        } catch (IOException e) {
            System.err.println("Error initializing CSV file: " + e.getMessage());
        }
    }
}
