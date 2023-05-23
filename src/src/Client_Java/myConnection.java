package src.Client_Java;

import java.sql.Connection;
import java.sql.DriverManager;
//Should be accessed by the Server
public class myConnection {
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/wordy_accounts","root","");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        int countdown = getTimerCountdown();
        printTimer(countdown);
    }

    public static int getTimerCountdown() {
        int duration = 10; // Timer duration in seconds

        for (int seconds = duration; seconds >= 0; seconds--) {
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Return the remaining time after each second
            return seconds;
        }

        return 0; // If the loop completes, return 0
    }

    public static void printTimer(int countdown) {
        while (countdown >= 0) {
            int minutes = countdown / 60;
            int seconds = countdown % 60;
            System.out.printf("%02d:%02d\n", minutes, seconds);

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countdown--; // Decrement the countdown by 1 second
        }

        System.out.println("Time's up!");
    }
}


