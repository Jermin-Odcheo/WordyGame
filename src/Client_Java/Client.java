package Client_Java;


import Client_Java.corba.lobby;
import Client_Java.corba.lobbyHelper;
import Client_Java.corba.sendLobby;
import Server_Java.corba.lobbyPOA;
import Server_Java.corba.wordy;
import Server_Java.corba.wordyHelper;
import Server_Java.WordyCallbackImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Client {
    static wordy wordyImpl;
    static WordyCallbackImpl wordyCallback;
    static sendLobby lobbyImpl;
    public static void main(String[] args) {
        try {
            // create and initialize the ORB
            //Connect to the server using Server IP Address
            ORB orb = ORB.init(new String[]{"-ORBInitialHost", "192.168.1.22", "-ORBInitialPort", "1050"}, null);

            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            // Resolve the object reference using a corbaloc URL
            String name = "Wordy";
            wordyImpl = wordyHelper.narrow(ncRef.resolve_str(name));




            lobbyServant servant = new lobbyServant();
            servant.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
            lobby cref = lobbyHelper.narrow(ref);




            ClientLoginUI.startLogin();
            String say1 = lobbyImpl.sendPlayerList(cref,"Hello");
            System.out.println(say1);
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

        } catch (Exception e) {
            System.out.println("Exception in main: " + e);
            e.printStackTrace();
        }
    }
    static class lobbyServant extends lobbyPOA
    {
        private ORB orb;

        public void setORB(ORB orb_val) {
            orb = orb_val;
        }

        @Override
        public void time(double countdown) {
            System.out.println(countdown);
        }

        @Override
        public void playerList(String message) {
            System.out.println(message);
        }
    }
}
