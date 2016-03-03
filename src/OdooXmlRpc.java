
public class OdooXmlRpc {
	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Login log = new Login();
        LoginXmlRpc logRpc = new LoginXmlRpc();
        //log.odooLogin();
        logRpc.odooLogin();
    }
}
