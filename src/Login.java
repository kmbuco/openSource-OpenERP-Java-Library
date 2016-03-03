import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.Session;

public class Login {
	public Login(){
        
    }
	public void odooLogin(){
		//http://52.89.125.104:8069
		/*String url = "http://52.89.125.104:8069/xmlrpc/";
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(url));
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    }
	    
	    XmlRpcClient client = new XmlRpcClient();
            //XmlRpcClientConfig pConfig;
	    //pConfig = (XmlRpcClientConfig) config;
	    client.setConfig(config);
	    
	    String username = "admin";
	    String password = "@dm1n2014_cop1a_erp";
	    //Object[] params = new Object[]{"terp", "admin", "a"};	    
	    Object[] params = new Object[]{username, password};	    
		Object res;
		try {
      			res = client.execute("login", params);
			System.out.print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		////////////////////////////////////////
		
		

		//openerp instance, pass login,password,dbname and serverURL 
		//$kengen_model = new OpenERPXmlrpc($username, $password, 'copiaERP', 'http://54.201.157.222:8069/xmlrpc/');

		String database = "copiaERP";
		String username="admin";
		String password="@dm1n2014_cop1a_erp";
		
		String url = "http://54.201.157.222:8069/xmlrpc/";
		String url1 = "http://52.89.125.104";
		URI uri = null;
		try {
			uri = new URI(url1);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//url1 = uri.toString();
		RPCProtocol xmlrpc = RPCProtocol.RPC_HTTP;
		//Session openERPSession = new Session("openerp1", 8069, "demo_database", "admin", "admin");
		Session openERPSession = new Session(xmlrpc, url1, 8069, database, username, password);
		try {
		    // startSession logs into the server and keeps the userid of the logged in user
		    openERPSession.startSession();
		    ObjectAdapter partnerAd = openERPSession.getObjectAdapter("res.partner");
		    System.out.println(partnerAd.toString());
		    ////// 
		    ////// Example code snippet goes here
		    //////
		} catch (Exception e) {
			e.printStackTrace();
		    System.out.println("Error while reading data from server:\n\n" + e.getMessage());
		}
	}
    
}
