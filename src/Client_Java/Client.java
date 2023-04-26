package Client_Java;


public class Client {
    public static void main(String[] args) {
        try {
            LoginUI.startLogin();
        } catch (Exception e){
            System.out.println("ERROR: Client error");
        }
    }

}
