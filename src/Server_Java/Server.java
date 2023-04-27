package Server_Java;

public class Server {
    public static void main(String args[]) {
        //Testing
//        try{
//            // create and initialize the ORB
//            ORB orb = ORB.init(args, null);
//
//            // get reference to root POA and activate the POAManager
//            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//            rootPOA.the_POAManager().activate();
//
//            // create servant and register it with the ORB
//            HelloPOA helloPOA = new HelloPOA();
//            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(helloPOA);
//            Hello href = HelloApp.HelloHelper.narrow(ref);
//
//            // bind the object reference to the naming service
//            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//            NameComponent path[] = ncRef.to_name("Hello");
//            ncRef.rebind(path, href);
//
//            // wait for incoming requests
//            orb.run();
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
    }
}
/**
 * login;
 * start join game;
 * obtain 17 characters random
 * sendwords; with player (kung sino nag send)
 * endGame;
 * endRound;
 * wonRoundOrNot;
 * gameEnded;
 * longestWord;
 * topPlayers;(ranking)
 */
