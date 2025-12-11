package eventapp;

import java.sql.*;
import java.util.Scanner;

public class EventAppSwing {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== EVENT REGISTRATION SYSTEM ====");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Add Participant");
            System.out.println("4. View Participants");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();  

            switch (ch) {
                case 1: addEvent(); break;
                case 2: viewEvents(); break;
                case 3: addParticipant(); break;
                case 4: viewParticipants(); break;
                case 5: 
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 1. Add Event
    static void addEvent() {
        try (Connection conn = DBConnect.getConnection()) {
            System.out.print("Event Name: ");
            String name = sc.nextLine();

            System.out.print("Event Date: ");
            String date = sc.nextLine();

            System.out.print("Venue: ");
            String venue = sc.nextLine();

            String sql = "INSERT INTO events(name, date, venue) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, venue);

            ps.executeUpdate();
            System.out.println("Event Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. View Events
    static void viewEvents() {
        try (Connection conn = DBConnect.getConnection()) {
            String sql = "SELECT * FROM events";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n--- EVENTS LIST ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("date") + " | " +
                                   rs.getString("venue"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Add Participant
    static void addParticipant() {
        try (Connection conn = DBConnect.getConnection()) {

            System.out.print("Enter Event ID: ");
            int eventId = sc.nextInt();
            sc.nextLine();

            System.out.print("Participant Name: ");
            String name = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Phone: ");
            String phone = sc.nextLine();

            String sql = "INSERT INTO participants(event_id, name, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);

            ps.executeUpdate();
            System.out.println("Participant Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. View Participants
    static void viewParticipants() {
        try (Connection conn = DBConnect.getConnection()) {

            String sql = "SELECT * FROM participants";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n--- PARTICIPANTS LIST ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getInt("event_id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("email") + " | " +
                                   rs.getString("phone"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
