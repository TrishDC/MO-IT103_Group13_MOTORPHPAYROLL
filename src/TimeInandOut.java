import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class TimeInandOut {
    private static final String CSV_FILE_PATH = "TimeInandOuts.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");
    private static final LocalTime LATE_THRESHOLD = LocalTime.of(8, 15);
    private static final double LATE_PENALTY = 100.0;

    public static List<AttendanceRecord> readAttendanceRecords(String employeeId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
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
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return records;
    }

    static class AttendanceRecord {
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
    }
}