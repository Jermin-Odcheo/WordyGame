package Server_Java;

import Server_Java.CORBA_IDL.wordy;
import Server_Java.CORBA_IDL.wordyHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.Properties;

public class Server {
    public static void main(String[] args) {

        try {
            ServerLoginUI.runServer();
            ORB orb = ORB.init(args,null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            WordyServer wordyServer = new WordyServer();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(wordyServer);
            wordy href = wordyHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Hello";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path,href);
            System.out.println("Server is ready");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
