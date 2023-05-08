package Server_Java;

import Server_Java.corba.WordyCallbackPOA;
import org.omg.CORBA.ORB;

import java.util.Arrays;
import java.util.List;

public class WordyCallbackImpl extends WordyCallbackPOA {
    private ORB orb;
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
    @Override
    public void notifyCountdownStarted(int i) {
        System.out.println("Countdown started!");
        System.out.println(i);

    }

    @Override
    public void notifyPlayersList(List<String> players) {
        System.out.println("Players in game:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i));
        }
    }


    @Override
    public void notifyGameStarted() {
        System.out.println("Game started!");
    }


}
