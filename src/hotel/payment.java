package hotel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class payment {
    static Connection con;
    public static int  bookingprice(int bookingId)
    {
        con=connection.getConnection();
        int totalprice=0;
        int totalDays=0;
        try{
            String sql="select * from bookings where bookingid=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                Date checkIn=rs.getDate("CheckInDate");
                Date checkOut=rs.getDate("CheckOutDate");
                try {
                    totalDays = (int) DateUtils.calculateTotalDaysInclusive(checkIn, checkOut);
                } catch (IllegalArgumentException e) {
                    System.err.println("error:occured in bookingprice while calculating days");
                }
            }
            else
            {
                return 0;
            }
        }
        catch (Exception e)
        {
            System.out.println("error occured in bookingprice ");
        }
        try{
                String sql="select * from rooms where roomId in(select roomId from booking_rooms where bookingId = ?)";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setInt(1,bookingId);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    totalprice+=rs.getInt("price");
                }
                return totalprice*totalDays;
        }
        catch(Exception e){
            System.out.println("error in payment class bookingprice method");
        }
        return totalprice;
    }
    public static boolean paymentdetailsupdate(int bookingId,java.sql.Date date,int price,String paymenttype)
    {
        con=connection.getConnection();
        try {
            String sql="insert into paymentdetails(bookingId,paymentDate,totalAmount,paymentMethod) values(?,?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1,bookingId);
            ps.setDate(2,date);
            ps.setInt(3,price);
            ps.setString(4,paymenttype);
            if(ps.executeUpdate()==1)
            {
                return true;
            }
            else return false;
        }
        catch (Exception e) {
            System.out.println("error in payment class paymentdetailsupdate method");
            return false;
        }

    }
}
