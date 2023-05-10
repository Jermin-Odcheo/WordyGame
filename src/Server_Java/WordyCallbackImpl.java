package Server_Java;

import Server_Java.corba.WordyCallback;
import Server_Java.corba.WordyCallbackPOA;
import org.omg.CORBA.ORB;

import java.util.ArrayList;
import java.util.List;

public class WordyCallbackImpl extends WordyCallbackPOA {
    private List<WordyCallback> callbacks = new ArrayList<>();
    private ORB orb;
    private WordyCallback wordyCallback;
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
    @Override
    public void notifyCountdownStarted(int i) {
        System.out.println("Countdown started!");
        System.out.println(i);
        for (WordyCallback callback : callbacks) {
            callback.notifyCountdownStarted(i);
        }
    }

    @Override
    public void notifyPlayersList(String[] players) {

    }




    @Override
    public void notifyGameStarted() {
        System.out.println("Game started!");
    }

    @Override
    public void sendMessage(String sendMessage) {

    }


}
