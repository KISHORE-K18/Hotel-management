package hotel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class booking {
    static Connection con=connection.getConnection();
    //FOR GETTING THE MAX BOOKINGID
    public static int getLastBookingId() {
        int lastBookingId = 1000; // Default to 0 if no bookings exist
        try {
            String sql = "SELECT MAX(bookingId) FROM bookings";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lastBookingId = rs.getInt(1); // Get the max booking ID
            }
        } catch (Exception ex) {
            System.out.println("Error occurred while fetching last booking ID: " + ex.getMessage());
        }
        return lastBookingId;
    }

    //BOOKING ROOM METHOD
    public static void bookroom(int bookingId,String cname,int roomsBooked,String bookedDates) {
        try {
            String sql = "insert into bookings(bookingId,customerName,roomsBooked,bookedDates) values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ps.setString(2, cname);
            ps.setInt(3,roomsBooked);
            ps.setString(4, bookedDates);
            if(ps.executeUpdate()==1);
            {

            }
        } catch (Exception ex) {
            System.out.println("error occurred in booking class bookroom method");
        }
    }
    //CHECKIN ROOM METHOD
    public static void checkIn(int bookingID,java.sql.Date checkInDate)
    {
        try {
            String sql = "update bookings set checkInDate= ? where bookingID= ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, checkInDate);
            ps.setInt(2, bookingID);
            if(ps.executeUpdate()==1)
            {
                System.out.println("BookingId:"+bookingID+" is checkin successfully");
            }
            else{
                System.out.println("BookingId:"+bookingID+" is not checkin successfully");
                System.out.println("check your bookingId");
            }
        }
        catch (Exception ex) {
            System.out.println("provide the bookingId correctly-->>error occurred in booking class checkIn method");
        }
    }
    public static boolean checkOut(int bookingID,java.sql.Date checkOutDate)//checkout method and update the payment
    {
        int amount=0;
        String paymentMethod="";
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        try{
            String sql = "update bookings set checkOutDate= ? where bookingID= ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, checkOutDate);
            ps.setInt(2, bookingID);
            if(ps.executeUpdate()==1)
            {
                System.out.println("***Total receipt to be paid***:");
                amount=payment.bookingprice(bookingID);
                if(amount==0)//if returned amount 0
                {
                    return false;
                }
                System.out.println("BookingId:"+bookingID+" Amount:"+amount);
                System.out.println("Enter the type of paymentMethod:");
                paymentMethod=br.readLine();
                if(payment.paymentdetailsupdate(bookingID,checkOutDate,amount,paymentMethod)==true)
                {
                    System.out.println("BookingId:"+bookingID+" paid the amount "+amount+" successfully");
                }
                else return false;
                if(bookingrooms.checkoutbookedrooms(bookingID)==true) {
                    System.out.println("BookingId" + bookingID + " is checkout successfully");
                    return true;
                }
                else return false;

            }
            else return false;
        }
        catch (Exception ex) {
            System.out.println("provide the bookingId correctly->>error occurred in booking class checkOut method");
        }
        return false;
    }
}
