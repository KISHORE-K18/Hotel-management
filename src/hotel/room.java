package hotel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.*;
import java.time.LocalDate;
public class room {
    static Connection con = connection.getConnection();
    public static int getLastRoomId(String roomType) {
        int lastRoomId = 100; // Default to 0 if no bookings exist
        try {
            String sql = "SELECT MAX(roomId) FROM rooms where roomType = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roomType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lastRoomId  = rs.getInt(1); // Get the max booking ID
            }
        } catch (Exception ex) {
            System.out.println("Error occurred while fetching last booking ID: " + ex.getMessage());
        }
        return lastRoomId;
    }
    public static void insertroom(int roomId,String roomType, Double price) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String sql = "insert into rooms(roomId,roomType,price) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setString(2, roomType);
            ps.setDouble(3, price);
            if (ps.executeUpdate() == 1)
                System.out.println("Rooms added Successfully");
        } catch (Exception e) {
            System.out.println("problem in adding room");
        }
    }

    public static void removeroom(int roomId) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String sql = "delete from rooms where roomId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            if (ps.executeUpdate() == 0)
                System.out.println("Rooms removed Successfully");
            else
                System.out.println("Invalid Room Id");
        } catch (Exception e) {
            System.out.println("problem in removing the room");
        }
    }

    public static List<String> getroomlist(String roomtype,java.sql.Date checkIn) {
        List<String> roomlist = new ArrayList<String>();
        try {
            String sql= """
    SELECT *
    FROM rooms r
    LEFT JOIN booking_rooms br 
        ON r.roomId = br.roomId
    LEFT JOIN bookings b 
        ON br.bookingId = b.bookingId
    WHERE r.availability = TRUE and r.roomType = ?
      AND (b.checkInDate IS NULL OR b.checkOutDate < ?)
    """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,roomtype);
            ps.setDate(2, checkIn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roomlist.add("Roomno:" + rs.getInt("roomId") + " " + "RoomType:" + rs.getString("roomType") + " " + "Room price per day:" + rs.getString("price"));
            }
            return roomlist;
        } catch (Exception e) {
            System.out.println("problem in getting roomlist");

        }
        return null;
    }

}

