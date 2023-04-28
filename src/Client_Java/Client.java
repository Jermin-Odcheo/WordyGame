package Client_Java;


import Server_Java.CORBA_IDL.wordy;
import Server_Java.CORBA_IDL.wordyHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Client {
    static wordy wordyImpl;
    public static void main(String[] args) {
        try {

            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "Hello";
            wordyImpl = wordyHelper.narrow(ncRef.resolve_str(name));
            System.out.println("Obtained a handle on server object: " + wordyImpl);

            ClientLoginUI.startLogin();
        } catch (Exception e){
            System.out.println("ERROR: Client error" + e.getMessage());
        }
    }
}
