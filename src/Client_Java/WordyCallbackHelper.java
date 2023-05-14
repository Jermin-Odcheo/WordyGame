package Client_Java;

import Server_Java.corba.WordyCallbackPOA;

import java.util.List;

public class WordyCallbackHelper extends WordyCallbackPOA {


    @Override
    public void notifyCountdownStarted(int count) {
        System.out.println(count);
    }

    @Override
    public void notifyPlayersList(String[] players) {

    }


    @Override
    public void notifyGameStarted() {

    }
}
