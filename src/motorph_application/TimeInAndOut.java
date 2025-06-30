import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class TimeInAndOut {
    private static final String CSV_FILE_PATH = "src/main/java/TimeInAndOuts.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");
    private static final LocalTime LATE_THRESHOLD = LocalTime.of(8, 15);
    private static final double LATE_PENALTY = 100.0;

    public static List<AttendanceRecord> readAttendanceRecords(String employeeId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = new ArrayList<>();

        // Check if file exists
        java.io.File file = new java.io.File(CSV_FILE_PATH);
        if (!file.exists()) {
            System.err.println("CSV file not found: " + CSV_FILE_PATH);
            return records;
        }

        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            List<String[]> allRecords = reader.readAll();
            
            // Skip header (first row)
            for (int i = 1; i < allRecords.size(); i++) {
                String[] data = allRecords.get(i);
                if (data.length >= 6 && data[0].equals(employeeId)) {
                    try {
                        LocalDate date = LocalDate.parse(data[3], DATE_FORMAT);
                        if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                            records.add(new AttendanceRecord(
                                data[0],
                                data[1],
                                data[2],
                                date,
                                LocalTime.parse(data[4], TIME_FORMAT),
                                LocalTime.parse(data[5], TIME_FORMAT)
                            ));
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing attendance record: " + String.join(",", data));
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return records;
    }

    public static class AttendanceRecord {
        String employeeId;
        String lastName;
        String firstName;
        LocalDate date;
        LocalTime timeIn;
        LocalTime timeOut;

        AttendanceRecord(String employeeId, String lastName, String firstName,
                LocalDate date, LocalTime timeIn, LocalTime timeOut) {
            this.employeeId = employeeId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
        }

        public double calculateHours() {
            long minutes = Duration.between(timeIn, timeOut).toMinutes();
            return minutes / 60.0;
        }

        public double calculateLatePenalty() {
            if (timeIn.isAfter(LATE_THRESHOLD)) {
                return LATE_PENALTY;
            }
            return 0.0;
        }

        public double calculateNetPay(double hourlyRate) {
            double grossPay = calculateHours() * hourlyRate;
            double penalty = calculateLatePenalty();
            return grossPay - penalty;
        }

        // Getters
        public String getEmployeeId() {
            return employeeId;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTimeIn() {
            return timeIn;
        }

        public LocalTime getTimeOut() {
            return timeOut;
        }
    }
}