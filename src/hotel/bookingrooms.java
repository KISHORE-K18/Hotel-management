package hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class bookingrooms {
    static Connection con;
        public static void bookingsroomsupdate(int bookingId,int roomId){
            con=connection.getConnection();
            try{
            String sql1="update rooms set availability=false where roomId=?";
            PreparedStatement ps1=con.prepareStatement(sql1);
            ps1.setInt(1,roomId);
            ps1.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println("error in updating rooms availability");
        }
         try{
             String sql2="insert into booking_rooms(bookingId,roomId) values(?,?)";
             PreparedStatement ps2=con.prepareStatement(sql2);
             ps2.setInt(1,bookingId);
             ps2.setInt(2,roomId);

             if(ps2.executeUpdate()==1) {
                 System.out.println("Room number: " + roomId + " booked successfully");
             }
         }
         catch(Exception e)
         {
             System.out.println("error in inserting booking rooms");
         }
    }
    public static boolean checkoutbookedrooms(int bookingId) {//changing the checkout rooms availability to true
            con=connection.getConnection();
            try{
                String sql1="update rooms set availability =true where roomId in(select roomId from booking_rooms where bookingId=?)";
                PreparedStatement ps1=con.prepareStatement(sql1);
                ps1.setInt(1,bookingId);
                if(ps1.executeUpdate()>0)
                {
                    return true;
                }
                else return false;
            }
            catch(Exception e)
            {
                System.out.println("error in checking out booking_rooms method");
            }
            return false;
    }
}

