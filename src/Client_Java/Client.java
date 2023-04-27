package Client_Java;


public class Client {
    public static void main(String[] args) {
        try {
            ClientLoginUI.startLogin();
        } catch (Exception e){
            System.out.println("ERROR: Client error");
        }
    }
}
