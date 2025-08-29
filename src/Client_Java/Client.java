package Client_Java;

import corbaGame.wordy;
import corbaGame.wordyHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.nio.file.Files;
import java.nio.file.Paths;

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

                // 1) Try IOR bootstrap (no NameService or IP edits needed)
                String iorPath = System.getenv().getOrDefault("WORDY_IOR_PATH", "wordy.ior");
                try {
                    if (Files.exists(Paths.get(iorPath))) {
                        System.out.println("Found IOR file at: " + iorPath + ", using direct bootstrap");
                        orb = ORB.init(new String[]{}, null);
                        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                        rootpoa.the_POAManager().activate();
                        String ior = new String(Files.readAllBytes(Paths.get(iorPath)));
                        org.omg.CORBA.Object obj = orb.string_to_object(ior.trim());
                        wordyImpl = wordyHelper.narrow(obj);
                        if (wordyImpl != null) {
                            // Test the connection with a simple call
                            wordyImpl.lobbyPlayerCount(); // This should work if server is running
                            System.out.println("Connected via IOR file");
                            return true;
                        }
                    }
                } catch (Exception ignore) {
                    System.out.println("IOR bootstrap failed, trying NameService: " + ignore.getMessage());
                }

                // 2) Fallback: NameService (legacy)
                String host = System.getenv().getOrDefault("WORDY_ORB_HOST", "127.0.0.1");
                String port = System.getenv().getOrDefault("WORDY_ORB_PORT", "1050");
                orb = ORB.init(new String[]{"-ORBInitialHost", host, "-ORBInitialPort", port}, null);

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
                    System.out.println("Connected via NameService at " + host + ":" + port);
                    return true;
                }

            } catch (Exception e) {
                attempts++;
                System.out.println("Connection attempt " + attempts + " failed: " + e.getMessage());

                if (attempts < maxRetries) {
                    try {
                        System.out.println("⌛ Retrying in 2 seconds...");
                        Thread.sleep(2000); // Wait 2 seconds before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    System.out.println("Failed to connect after " + maxRetries + " attempts");
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

    // Functional interface for server operations
    @FunctionalInterface
    public interface ServerOperation<T> {
        T execute(wordy server) throws Exception;
    }

    // Wrapper method for making safe server calls with auto-reconnect
    public static <T> T safeServerCall(ServerOperation<T> operation, T defaultValue) {
        try {
            if (!ensureConnection()) {
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

    // Safe lobby join with retries
    public static boolean safeJoinLobby(String username) {
        return safeServerCall(server -> server.joinLobby(username), false);
    }

    // Safe timer retrieval
    public static double safeGetTimer() {
        return safeServerCall(wordy::gettimer, 0.0);
    }

    // Safe lobby count retrieval
    public static double safeGetLobbyCount() {
        return safeServerCall(wordy::lobbyPlayerCount, 0.0);
    }

    // Safe leave game
    public static void safeLeaveGame(String username) {
        safeServerCall(server -> {
            server.leaveGame(username);
            return null;
        }, null);
    }

    // Alternative game state methods using existing CORBA interface
    public static boolean safeIsPlayerInGame(String username) {
        // Use server-reported game activity since per-player status isn't exposed in IDL
        return safeServerCall(wordy::isGameActive, false);
    }
}
