package hotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to Hotel Management System");
        System.out.println("please enter 1 if you are admin");
        System.out.println("please enter 2 if you are user");
        System.out.println("enter the number:");
        try {
            int ch =Integer.parseInt(br.readLine());
            if(ch==1) {
                admin.adminprotocals();//admin property
            }
            else if(ch==2){ //user property
                System.out.println("Enter 1 for booking room");
                System.out.println("Enter 2 for checkIn room");
                System.out.println("Enter 3 for checkOut room");
                System.out.println("enter the number:");
                int customerpress = Integer.parseInt(br.readLine());
                if(customerpress == 1) {//booking areas
                    System.out.println("Enter your name:");
                    String cname = br.readLine();
                    boolean book = true;
                    int bookingid;
                    if(booking.getLastBookingId()==0)
                    {
                        bookingid = 1001;
                    }
                    else bookingid = booking.getLastBookingId()+1;
                    while(book) {
                        System.out.println("Enter how many rooms you want to book");
                        try {
                            int roomcount = Integer.parseInt(br.readLine());
                            System.out.println("Enter single or double bed room or guest room");
                            String roomtype = br.readLine();
                            String inputDate1="";
                            String inputDate2="";
                            StringBuilder bookeddates = new StringBuilder();
                            try {
                                System.out.println("Enter check-in date (YYYY-MM-DD): ");
                                inputDate1 = br.readLine();
                                System.out.println("Enter check-out date (YYYY-MM-DD): ");
                                inputDate2 = br.readLine();
                                bookeddates.append(inputDate1);
                                bookeddates.append(" to ");
                                bookeddates.append(inputDate2);
                                LocalDate checkInDate = LocalDate.parse(inputDate1);
                                Date sqlDate = Date.valueOf(checkInDate);
                                System.out.println("Room available are given below:");
                                List<String> roomlist = room.getroomlist(roomtype,sqlDate);
                                if(roomlist.isEmpty())
                                {
                                    System.out.println("Room does not exist for the given date");
                                }
                                else{
                                for (String room : roomlist) {
                                    System.out.println(room);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("provide date in format YYYY-MM-DD");
                            }
                            int count=0;
                            booking.bookroom(bookingid,cname,roomcount,bookeddates+"");
                            while (count < roomcount) {
                                System.out.println("Enter the room number to be booked:");
                                int roomnumber = Integer.parseInt(br.readLine());
                                bookingrooms.bookingsroomsupdate(bookingid,roomnumber);
                                count++;
                            }
                            System.out.println("for continue booking enter 7 or else enter 0 to exit:");
                            int ans=Integer.parseInt(br.readLine());
                            if(ans==0) {
                                book = false;
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("provide accurate details");
                        }
                    }
                    System.out.println("Thank you for booking kindly visit the Hotel again");
                }
                else if(customerpress==2)//checkin codes
                {
                    System.out.println("Enter your bookingId:");
                    int bookingId = Integer.parseInt(br.readLine());
                    System.out.println("Enter check-in date (YYYY-MM-DD): ");
                    String inputDate = br.readLine();
                    LocalDate sqlDate = LocalDate.parse(inputDate);
                    Date checkInDate= Date.valueOf(sqlDate);
                    booking.checkIn(bookingId,checkInDate);
                }
                else if(customerpress==3)//checkout codes
                {
                    System.out.println("Enter your bookingId:");
                    int bookingId = Integer.parseInt(br.readLine());
                    System.out.println("Enter check-out-date (YYYY-MM-DD): ");
                    String inputDate = br.readLine();
                    LocalDate sqlDate = LocalDate.parse(inputDate);
                    Date checkOutDate= Date.valueOf(sqlDate);
                    if(booking.checkOut(bookingId,checkOutDate)==true)
                    {
                        System.out.println("Thank you for booking kindly visit the Hotel again");
                    }
                    else {
                        System.out.println("error in check-out");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("please enter a valid number");
        }

    }
}