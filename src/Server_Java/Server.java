package Server_Java;


import Server_Java.corbaGame.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;


import java.net.InetAddress;


public class Server {
    /*
    Start the server
    1. Open the cmd in Server_Java
    2. Type the command "start orbd -ORBInitialPort 1050" and press enter
    3. Start the Server class in intellij
     */
    public static void main(String[] args) {
        try {
            // Get the IP address of the machine the server is running on
            InetAddress address = InetAddress.getLocalHost();
            String host = address.getHostAddress();
            System.out.println("Server IP address: " + host);

            // Initialize ORB and POA
            ORB orb = ORB.init(new String[]{"-ORBInitialHost", host, "-ORBInitialPort", "1050"}, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the Wordy servant and register it with the Naming Service
            WordyServer wordyServant = new WordyServer();
            //Narrow the object references to their respective interfaces
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(wordyServant);;

            // Narrow the object references to their respective interfaces
            wordy href = wordyHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] wordyPath = ncRef.to_name("Wordy");
            ncRef.rebind(wordyPath, href);


            // Print server ready message
            System.out.println("Server is ready...");
            // Start ORB event loop
            orb.run();

        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }
    }
}

