package Client_Java;

import Client_Java.corbaGame.wordy;
import Client_Java.corbaGame.wordyHelper;
import Server_Java.corbaGame.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Client {
    static wordy wordyImpl;
    private static ORB orb;
    private static NamingContextExt ncRef;

    public static void main(String[] args) {
        // Start the client login UI immediately, connection will be established when needed
        ClientLoginUI.startLogin();
    }

    // Method to establish or re-establish connection to server
    public static boolean connectToServer() {
        return connectToServer(3); // Default 3 retry attempts
    }

    public static boolean connectToServer(int maxRetries) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                System.out.println("Attempting to connect to server... (Attempt " + (attempts + 1) + "/" + maxRetries + ")");

                // Create and initialize the ORB
                orb = ORB.init(new String[]{"-ORBInitialHost", "192.168.1.6", "-ORBInitialPort", "1050"}, null);

                // Get the root naming context
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                ncRef = NamingContextExtHelper.narrow(objRef);

                POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                rootpoa.the_POAManager().activate();

                // Resolve the object reference
                String name = "Wordy";
                wordyImpl = wordyHelper.narrow(ncRef.resolve_str(name));

                // Test the connection with a simple call
                if (wordyImpl != null) {
                    // Try a simple operation to verify connection
                    wordyImpl.lobbyPlayerCount(); // This should work if server is running
                    System.out.println("✅ Successfully connected to server!");
                    return true;
                }

            } catch (Exception e) {
                attempts++;
                System.out.println("❌ Connection attempt " + attempts + " failed: " + e.getMessage());

                if (attempts < maxRetries) {
                    try {
                        System.out.println("⏳ Retrying in 2 seconds...");
                        Thread.sleep(2000); // Wait 2 seconds before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    System.out.println("❌ Failed to connect after " + maxRetries + " attempts");
                }
            }
        }

        return false;
    }

    // Method to check if connection is still valid
    public static boolean isConnected() {
        if (wordyImpl == null) {
            return false;
        }

        try {
            // Test connection with a lightweight call
            wordyImpl.lobbyPlayerCount();
            return true;
        } catch (Exception e) {
            System.out.println("Connection lost: " + e.getMessage());
            return false;
        }
    }

    // Method to reconnect if connection is lost
    public static boolean ensureConnection() {
        if (isConnected()) {
            return true;
        }

        System.out.println("🔄 Connection lost, attempting to reconnect...");
        return connectToServer();
    }

    // Wrapper method for making safe server calls with auto-reconnect
    public static <T> T safeServerCall(ServerOperation<T> operation, T defaultValue) {
        try {
            if (!ensureConnection()) {
                System.out.println("❌ Cannot establish server connection");
                return defaultValue;
            }

            return operation.execute(wordyImpl);

        } catch (Exception e) {
            System.out.println("Server call failed: " + e.getMessage());
            // Try to reconnect once more
            if (connectToServer(1)) {
                try {
                    return operation.execute(wordyImpl);
                } catch (Exception e2) {
                    System.out.println("Server call failed after reconnect: " + e2.getMessage());
                }
            }
            return defaultValue;
        }
    }

    // Functional interface for server operations
    @FunctionalInterface
    public interface ServerOperation<T> {
        T execute(wordy server) throws Exception;
    }
}
