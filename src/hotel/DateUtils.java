package hotel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class DateUtils {
    public static long calculateTotalDaysInclusive(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            System.out.println("Check-in and check-out dates cannot be null.");
        }
        // Convert java.sql.Date to java.time.LocalDate
        LocalDate checkInDate = checkIn.toLocalDate();
        LocalDate checkOutDate = checkOut.toLocalDate();

        if (checkOutDate.isBefore(checkInDate)) {
            System.out.println("Check-out date cannot be before check-in date.");
        }

        // Calculate total days and include the last day
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;
    }
}
