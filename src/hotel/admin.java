package hotel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class admin {

    public static void adminprotocals() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("please enter your password");
            String password = br.readLine();
            while (true) {
                System.out.println("please enter 1 if you are adding the room");
                System.out.println("please enter 2 if you are removing the room");
                System.out.println("please enter 3 to exit");
                System.out.println("enter the number:");
                int val = Integer.parseInt(br.readLine());
                if (val == 1) {//admin inserting roomtype and there prices
                    try {
                        System.out.println("enter how many rooms you want to add");
                        int roomcount = Integer.parseInt(br.readLine());
                        System.out.println("enter single or double bed room or guest room");
                        String roomtype = br.readLine();
                        System.out.println("enter the price of the room");
                        double price = Double.parseDouble(br.readLine());
                        int count = room.getLastRoomId(roomtype);
                        if (count == 0 && "single bed room".equals(roomtype))
                            count = 100;
                        else if (count == 0 && "double bed room".equals(roomtype)) {
                            count = 200;
                        }
                        roomcount = roomcount + count;
                        while (count < roomcount) {
                            room.insertroom(count, roomtype, price);
                            count++;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("provide accurate details");
                    }
                }
                if (val == 2) {//admin removing the rooms that are under reconstruction
                    System.out.println("Enter the room number to be removed");
                    try {
                        int roomid = Integer.parseInt(br.readLine());
                        room.removeroom(roomid);
                    } catch (NumberFormatException e) {
                        System.out.println("provide accurate details");
                    }
                }
                else if (val == 3) {
                    break;
                }
            }
            }
        catch (Exception e) {
            System.out.println("please enter again");
        }
    }
}
