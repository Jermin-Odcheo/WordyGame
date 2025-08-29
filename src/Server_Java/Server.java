package Server_Java;


import corbaGame.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class Server {
    /*
    Start the server
    1. Open the cmd in Server_Java
    2. Type the command "start orbd -ORBInitialPort 1050" and press enter (optional when using IOR file)
    3. Start the Server class in intellij
     */
    public static void main(String[] args) {
        try {
            // Resolve host
            InetAddress address = InetAddress.getLocalHost();
            String detectedHost = address.getHostAddress();
            String host = System.getenv().getOrDefault("WORDY_ORB_HOST", detectedHost);
            String nsPort = System.getenv().getOrDefault("WORDY_ORB_PORT", "1050");
            String serverPort = System.getenv().getOrDefault("WORDY_SERVER_PORT", "3700");
            String iorPath = System.getenv().getOrDefault("WORDY_IOR_PATH", "wordy.ior");

            System.setProperty("com.sun.CORBA.ORBServerHost", host);
            System.setProperty("com.sun.CORBA.ORBServerPort", serverPort);

            // Initialize ORB with properties (NameService optional)
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", host);
            props.put("org.omg.CORBA.ORBInitialPort", nsPort);
            ORB orb = ORB.init(new String[]{}, props);

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create servant and object ref
            WordyServer wordyServant = new WordyServer();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(wordyServant);
            wordy href = wordyHelper.narrow(ref);

            // Optional: bind to NameService if available
            try {
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
                NameComponent[] wordyPath = ncRef.to_name("Wordy");
                ncRef.rebind(wordyPath, href);
                System.out.println("NameService binding complete (Wordy)");
            } catch (Exception nsEx) {
                System.out.println("NameService not available; proceeding with IOR file only");
            }

            // Write IOR file for clients (no NameService needed)
            String ior = orb.object_to_string(href);
            File out = new File(iorPath);
            try (Writer w = new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8)) {
                w.write(ior);
            }
            System.out.println("IOR written to: " + out.getAbsolutePath());
            System.out.println("Server host=" + host + " serverPort=" + serverPort + " NS port=" + nsPort);
            System.out.println("Server is ready...");

            orb.run();

        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }
    }
}
