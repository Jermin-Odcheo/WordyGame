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
    public static void main(String[] args) {
        try {
            // create and initialize the ORB
            //Connect to the server using Server IP Address
            ORB orb = ORB.init(new String[]{"-ORBInitialHost", "192.168.1.11", "-ORBInitialPort", "1050"}, null);
            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            // Resolve the object reference using a corbaloc URL
            String name = "Wordy";
            wordyImpl = wordyHelper.narrow(ncRef.resolve_str(name));
            //Start Login

            ClientLoginUI.startLogin();
        } catch (Exception e) {
            System.out.println("Exception in main: " + e);
            e.printStackTrace();
        }
    }
}


////            // join the game
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter your name: ");
//            String playerName = scanner.nextLine();
//            boolean success = wordyImpl.joinGame(playerName);
//            if (!success) {
//                System.out.println("Failed to join game.");
//            }
//
//            // play the game
//            while (true) {
//                System.out.print("Enter your word: ");
//                String word = scanner.nextLine();
//                try {
//                    System.out.println(wordyImpl.generateLetters());
//                    wordyImpl.playWord(playerName, word);
//                } catch (GameException e) {
//                    System.out.println(e.message);
//                }
//            }
