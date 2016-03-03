import java.io.Console;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;


public class LoginXmlRpc {
	Object[] prod_objTax = null;
	Object[] prod_objPricelistid = null;
	Double prod_weight;
	Double prod_price;
	String prod_name;
	int pricelist_id;
	int prod_id;
	int prod_tax_id;
	String price_id;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////***********************************************************************************//////////////////
	//////////////////////////>>>>>>>>>>>>>>>>Make order<<<<<<<<<<<<<<<<<<<<<<</////////////////////////////////////////
	///////////////***************************Parameters**********************************************//////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	int customer_id;         //the customer_id
	String vendor_partner_id;  //vendor id
	String date_order = "2016-02-24";         //date Format dd-mm-yyyy | 2014-10-07
	String copia_date_order = "2016-02-24";   //date Format dd-mm-yyyy | 2014-10-07
	String date_delivery = "2016-02-26";      //date Format dd-mm-yyyy | 2014-10-07
	String mode = "sms";       // eg //order_mode = "sms"
	String carrier_id = "false"; //customer_data['property_delivery_carrier']
	int shop_id = 1;  //vendor_data['shop_id']
	int user_id = 297; //vendor_data['user_id'] also knwn as sale person id
	
	Object[] param_partner_id; 
	Object[] param_partner_shipping_id;
	Object[] param_partner_invoice_id;
	Object[] param_vendor_partner_id;
	Object[] param_date_order;
	Object[] param_copia_date_order;
	Object[] param_date_delivery;
	Object[] param_order_line;
	Object[] param_mode;
	Object[] param_islayaway;  
	Object[] param_carrier_id;
	Object[] param_shop_id;  //vendor_data['shop_id']
	Object[] param_user_id;  //$vendor_data['user_id']
	
	static final String db = "copiaERP";
	static final String host = "http://52.89.125.104";
	static final int port = 8069;
	static final String password = "@dm1n2014_cop1a_erp";
	public LoginXmlRpc(){
		
	}
	
	
	public void odooLogin(){
		date_order = "2016-02-26";       
		copia_date_order = "2016-02-26";  
		 date_delivery = "2016-02-28"; 
		 shop_id = 1; 
		 vendor_partner_id = "17549";
		 user_id = 297; 
		
		//http://54.201.157.222:8069
		int port = 8069;
		String url = "http://52.89.125.104:8069/xmlrpc/common";
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
		try {
			config.setServerURL(new URL(url));
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    }
	    
	    XmlRpcClient client = new XmlRpcClient();
	    /*XmlRpcClientConfigImpl pConfig;*/
	    client.setConfig(config);
	    
	    String username = "admin";
	    String password = "@dm1n2014_cop1a_erp";
	    //Object[] params = new Object[]{"terp", "admin", "a"};	    
	    Object[] params = new Object[]{db,username, password};	    
		Object res;
		try {
      			res = client.execute("login", params);
			System.out.print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		///////////////////////////////////////////////////////////////
		
		XmlRpcClient xmlrpcDb = new XmlRpcClient();
		XmlRpcClientConfigImpl xmlrpcConfigDb = new XmlRpcClientConfigImpl();
		xmlrpcConfigDb.setEnabledForExtensions(true);
		try {
			xmlrpcConfigDb.setServerURL((new URL("http", "52.89.125.104", 8069, "/xmlrpc/db")));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		xmlrpcDb.setConfig(xmlrpcConfigDb);
		try {
		// Retrieve databases
		Vector<Object> param = new Vector<Object>();
		Object result = xmlrpcDb.execute("list", param);
		Object[] a = (Object[]) result;
		Vector<String> r = new Vector<String>();
		for (int i = 0; i < a.length; i++) {
		if (a[i] instanceof String) {
		r.addElement((String) a[i]);
		//System.out.println(a[i]);
		}
		}
		} catch (XmlRpcException e) {
		e.printStackTrace();
		}
		
		ArrayList listIds = new ArrayList();
		
//////////////////////////////////////////////////////////////////////////////////////////////
		try{
		client = new XmlRpcClient();
		XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
		clientConfig.setEnabledForExtensions(true);
		clientConfig.setServerURL(new URL("http", "52.89.125.104", 8069, "/xmlrpc/object"));
		client.setConfig(clientConfig);


		Object[] params2 = { "name", "!=", "" };
		Vector<Object> paramset = new Vector<Object>();
		paramset.add(params2);
		Vector<Object> arg = new Vector<Object>();
		arg.add(db); //database name
		arg.add(1);
		arg.add(password); //the password of the database
		arg.add("res.partner");
		arg.add("search");
		arg.add(paramset);

		
		Object[] ids = (Object[]) client.execute("execute", arg);
		//System.out.println("partner addressees with create_uid 1 :");
		for (Object obj : ids) {
		int a = Integer.parseInt(obj.toString());
		//System.out.println(a);
		}

		ArrayList<Object[]> listparam = new ArrayList<>();
		Object[] params3 = { "agent", "=", "True" };
		Object[] param_3= {"active","=","True"};
		listparam.add(params3);
		listparam.add(param_3);
		Vector<Object> params4 = new Vector<Object>();
		paramset.add(params3);
		Vector<Object> arg2 = new Vector<Object>();
		arg2.add(db);
		arg2.add(1); //assumes the first user id is admin
		arg2.add(password); // The Database password 
		arg2.add("res.partner");
		arg2.add("search");
		arg2.add(listparam);

		Object[] ids2 = (Object[]) client.execute("execute", arg2);
		System.out.println("BomID's 1 :");
		for (Object obj2 : ids2) {
		int b = Integer.parseInt(obj2.toString());
		//System.out.print(b);
		listIds.add(Integer.toString(b));
		//System.out.print(",");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++CHECK (<<<<<>>>>) THIS+++++++++++++++++++++++++++++++++++++"); //new line
		System.out.println(arg2); //print the args
		}
		catch(Exception e){
			
		}
//////////////////////////////////////////////////////////////////////////////////////////////
		Object[] hm = null;
		Vector<Object> args = new Vector<Object>();
		try{
		XmlRpcClient client1 = new XmlRpcClient();
		XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
		clientConfig.setEnabledForExtensions(true);
		clientConfig.setServerURL(new URL("http", "52.89.125.104", 8069, "/xmlrpc/object"));
		client1.setConfig(clientConfig);

		Object[] params2 = { "name","phone","id","experiment_id","write_date","create_date","write_uid","create_uid","property_delivery_carrier","shop_id","user_id"};
		//Object[] params1 = {"phone","write_date"};
		//, "company_id"
		//,"create_uid"

		

		args.add(db);
		args.add(1);
		args.add(password);
		args.add("res.partner");
		args.add("read");
		args.add(listIds); // <- THE PYTHON SYNTAX SAYS input 'LIST OF IDS' here What is the Java equivalent???
		args.add(params2);
		hm = (Object[])client1.execute("execute", args);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		HashMap globalMap = new HashMap();
		for (Object object : hm) {
            HashMap hash = (HashMap)object;   
            if (!hash.get("shop_id").equals(false)){
            Object[] objArr = (Object[]) hash.get("shop_id");
            //System.out.println("Shop id is : "+ objArr[0]);
            }
            if (!hash.get("user_id").equals(false)){
            Object[] objArruser = (Object[]) hash.get("user_id");
            //System.out.println("Shop id is : "+ objArruser[0]);
            }
            
            globalMap.put("name", hash.get("name"));    
            //System.out.println(hash.values());
            //System.out.println(object.toString());
            /*System.out.println(hash.get("name"));
            System.out.println(hash.get("phone"));
            System.out.println(hash.get("id"));*/
            if(hash.get("experiment_id").toString() != "false"){
            	Object[] ob = (Object[]) hash.get("experiment_id");
            	
            	//System.out.println(ob[0].toString());
            	//System.out.println((Boolean) hash.get("experiment_id"));
            }
            	
        }  
		
		createCustomer();

		System.out.println("the args as they are: " + args);
///////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////********log an order in the order table**************////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

/*
* 1. check that the customer exists
* 2. Add the customer if they dont exist
* 3. Place the order for the customer under a given vendor
* */
//creating the customer
		///////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	
	
	public void createCustomer(){
		System.out.println("creating customer");
		XmlRpcClient client = new XmlRpcClient();
		XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
		clientConfig.setEnabledForExtensions(true);
		Object res = null;
		try {
			clientConfig.setServerURL(new URL("http", "52.89.125.104", 8069, "/xmlrpc/object"));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.setConfig(clientConfig);

		try {
	    HashMap<String, Object> vals = new HashMap<String, Object>();
	    vals.put("name", "+254722715026");
	    //vals.put("ref", "MGA");
	    Object[] params = new Object[]{db, 1, password, "res.partner", "create", vals};
	       
		
		
		
			vals = new HashMap<String, Object>();
		    vals.put("name", "+254722715040");
		    vals.put("phone", "+254722715040");
		    vals.put("mobile", "+254722715040");
		    vals.put("customer", "True");
		    
		    System.out.println("The values are: "+ vals);
			params = new Object[]{db, 1, password, "res.partner", "create", vals};
			
			System.out.println("The writing parameters: "+params);
			res = client.execute("execute", params);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		System.out.println(res);
		customer_id = Integer.parseInt(res.toString());
		createOrder(customer_id);
	}
		
	
	
	public void createOrder(int resultCustomerId){
		//read the customer from res partners
		customer_id =  resultCustomerId;
		
		
		
		XmlRpcClient client = new XmlRpcClient();
		XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
		clientConfig.setEnabledForExtensions(true);
		try {
			clientConfig.setServerURL(new URL("http", "52.89.125.104", 8069, "/xmlrpc/object"));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.setConfig(clientConfig);
		ArrayList listIds = new ArrayList();
		try{
			listIds = new ArrayList<>();
			listIds.add(resultCustomerId);
			ArrayList<Object[]> listparam = new ArrayList<>();
			
			
			Vector<Object> arg2 = new Vector<Object>();
			arg2.add(db);
			arg2.add(1); //assumes the first user id is admin
			arg2.add(password); // The Database password 
			arg2.add("product.product");
			arg2.add("read");
			arg2.add(listIds);

			Object[] hm = (Object[]) client.execute("execute", arg2);
			
			for (Object obj: hm) {
				HashMap hash = (HashMap)obj;   
	            System.out.println(obj.toString());
	            System.out.println(hash.values());
			}
			System.out.println(""); //new line
			}
		catch (Exception e){
			
		}
		
		//create in sales order lines... 
		//first read the product ids in the table then read the orders from the table
		
		try{
			listIds = new ArrayList<>();
			//listIds.add(Integer.parseInt(resultCustomerId));
			listIds.add("5994");
			//listIds.add("9793");
			
			Object[] params2 = { "name","list_price","id","taxes_id","weight","property_product_pricelist"};
			Vector<Object> arg2 = new Vector<Object>();
			arg2.add(db);
			arg2.add(1); //assumes the first user id is admin
			arg2.add(password); // The Database password 
			arg2.add("product.product");
			arg2.add("read");
			arg2.add(listIds);
			arg2.add(params2);
			

			Object[] hm = (Object[]) client.execute("execute", arg2);
			System.out.println("BomID's :");
			for (Object obj: hm) {
				HashMap hash = (HashMap)obj;   
	            System.out.println(obj.toString());
	            System.out.println(hash.values());
	            
	            prod_name = (String) hash.get("name");
	            prod_price = (Double) hash.get("list_price");
	            prod_objTax = (Object[]) hash.get("taxes_id"); //an object array
	            //price_id = (String) hash.get("property_product_pricelist");
	            //prod_objPricelistid = (Object[]) hash.get("property_product_pricelist");
	            prod_weight = (Double) hash.get("weight");
	            prod_id  = (int) hash.get("id");
	            prod_tax_id = (int) prod_objTax[0];
	            
	            //pricelist_id = (int) prod_objPricelistid[0];
	            //System.out.println(prod_objPricelistid.toString());
	            
	            System.out.println("tax id is "+prod_tax_id);
			}
			System.out.println("Prod name: "+prod_name+" Prod price: "+prod_price+ " Tax_ id: "+ prod_objTax+ " Prod weight: " + prod_weight+ " Prod id: " + prod_id+ " prod_tax "+prod_tax_id); //new line
			}
		catch (Exception e){
			e.printStackTrace();
		}
		
		//then 
		Object res = null;
		LinkedHashMap<String, Object> linked_vals = new LinkedHashMap<String, Object>();//use linkedHashMap to maintain order
		
		linked_vals = new LinkedHashMap<String, Object>();
		    
		    //do the iteration for the products here
		    //vals.put("ref", "MGA");
		       
		    linked_vals.put("product_id", prod_id);
		    linked_vals.put("name", prod_name);
		    linked_vals.put("price_unit", prod_price);
		    linked_vals.put("tax_id",prod_objTax);
		    linked_vals.put("weight", prod_weight);
		    linked_vals.put("product_uom_qty", "2.00");
			    //vals.put("tax_id", prod_tax_id);
			    
				//params = new Object[]{db, 1, password, "sale.order.line", "create", vals};
					//res = client.execute("execute", params);
		    Vector<Object> linearg = new Vector<Object>();
		    linearg.add(0);
		    linearg.add(0);
		    linearg.add(linked_vals);
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Get the line args here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		   
		    /********************************************************************************* The expected output *********************************************************/
		    /**************************************[0, 0, {product_id=0, name=Ken Tomato Sauce - dozen, price_unit=null, weight=null, product_uom_qty=2.00}]************/
		    System.out.println(linearg);
		    Vector[] linearArr  = {linearg,linearg,linearg};
		    /*********************************************[Ljava.util.Vector;@2ff5659e**************************/
		    /*/|\because it is an array of objects*/
		    System.out.println(linearArr);
		    
			ArrayList<order_lines> arrayLine = new ArrayList();
			//HashMap<String, Object> vals = new HashMap<String, Object>();
			order_lines orderline = new order_lines();
			orderline.setOperation(0); //operation for insert.
			orderline.setRecordId(0); //ignore the id because it is an insert not an update
			orderline.setProductLine(linked_vals);
			
			System.out.println(linked_vals.values());
			System.out.println(orderline.toString());
			arrayLine.add(orderline);
			System.out.println(arrayLine.toString());
			//send the products here
		try {
		
		
		System.out.println("customer ID : "+ customer_id);
		System.out.println("vendor id : "+ vendor_partner_id );
		System.out.println("date order: " + date_order);
		System.out.println("mode : "+ mode);
		System.out.println("carrier ID : " + carrier_id);
		System.out.println("shop_id : " + shop_id);
		System.out.println("user_id : " + user_id);
		System.out.println("now let's go to work");
		Boolean layaway = false;
		
		 Object[] param_partner_id = { "partner_id", "=", customer_id}; //the customer id
		 Object[] param_partner_shipping_id={"partner_shipping_id","=",customer_id}; //the customer id
		 Object[] param_partner_invoice_id={"partner_invoice_id","=",customer_id}; //the customer id
		 Object[] param_vendor_partner_id= {"vendor_partner_id","=",vendor_partner_id}; //vendor id vendor_partner_id,
		 Object[] param_date_order= {"date_order","=",date_order}; //dates Format dd-mm-yyyy | 2014-10-07
		 Object[] param_copia_date_order= {"copia_date_order","=",copia_date_order}; //dates Format dd-mm-yyyy | 2014-10-07
		 Object[] param_date_delivery= {"date_delivery","=",date_delivery}; //date Format dd-mm-yyyy | 2014-10-07
		 Object[] param_order_line= {"order_line","=",linearArr};  //the array of orderline operation like 
		 Object[] param_mode= {"mode","=",mode};		    //$order_mode = "sms";
		 Object[] param_islayaway= {"islayaway","=",layaway};  
		 Object[] param_carrier_id= {"carrier_id","=",carrier_id}; //$customer_data['property_delivery_carrier']
		 Object[] param_shop_id= {"shop_id","=",shop_id};  //vendor_data['shop_id']
		 Object[] param_user_id= {"user_id","=",user_id};  //$vendor_data['user_id']
		
		 HashMap<String, Object> vals = new HashMap<String, Object>();
		    
		    
			
		
				
			vals.put("pricelist_id", 1);
			vals.put("partner_id", customer_id);
			vals.put("partner_shipping_id",customer_id);
			vals.put("partner_invoice_id",customer_id);
			vals.put("vendor_partner_id",vendor_partner_id);
			vals.put("date_order",date_order);
			vals.put("copia_date_order",copia_date_order);
			vals.put("date_delivery",date_delivery);
			vals.put("order_line",linearArr);
			vals.put("mode",mode);
			vals.put("islayaway",layaway);
			vals.put("carrier_id",0);
			vals.put("shop_id",shop_id);
			vals.put("user_id",user_id);

		
		
		
		Vector<Object> args = new Vector<Object>();
	    args.add(db);
		args.add(1);
		args.add(password);
		args.add("sale.order");
		args.add("create");
		args.add(vals);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>check out the args<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(args);
		int result = 0;
		result = (int)client.execute("execute", args);
		System.out.println(" Them result is >>>>>>>>>>>>>>>>"+ result);
	} catch (XmlRpcException e) {
		e.printStackTrace();
	}
	}
	

		
}		


