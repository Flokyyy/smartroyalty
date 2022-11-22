package de.flokyy.smartroyalty.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVWriter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoralCubeAPI {
	
	public static int royalty = 0;
	public static List<String> readData = new ArrayList<String>();
	public static List<String[]> data = new ArrayList<String[]>();
	 
	public static void readAndSave() {
    String line = "";
    String splitBy = ",";
    
    data.clear(); // Clear before reading again
    readData.clear(); // Clear before reading again
    
    File f = new File("data.csv");
    if(!f.exists()){
      try {
		f.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
    }
    
    try {
      // parsing a CSV file into BufferedReader class constructor  
      BufferedReader br = new BufferedReader(new FileReader("data.csv"));
      System.out.println("Starting reading data from file...");
   
      while ((line = br.readLine()) != null)
      {
        String[] array = line.split(splitBy);
        //use comma as separator  
        String nft_name = array[0].replace("\"", "");
        String token = array[1].replace("\"", "");
        String royalty = array[2].replace("\"", "");
        String marketplace = array[3].replace("\"", "");
        String tx = array[4].replace("\"", "");
        String price = array[5].replace("\"", "");
        String seller = array[6].replace("\"", "");
   
    	readData.add(tx); // Save transaction separately  
        data.add(new String[] { nft_name, token, royalty, marketplace, tx, price, seller});
      }
      
    }
    catch(IOException e) {
      e.printStackTrace();
      }
	}

	public static void getLatestData(String limit, String authority, String collectionName) {
		try {

			readAndSave();
			
			System.out.println("Fetching data from coralcube...");
			OkHttpClient client = new OkHttpClient().newBuilder()
					 .connectTimeout(3,TimeUnit.MINUTES)
					 .writeTimeout(3,TimeUnit.MINUTES)
					 .readTimeout(3,TimeUnit.MINUTES)
					 .build();
     
			String builder = "https://api.coralcube.cc/0dec5037-f67d-4da8-9eb6-97e2a09ffe9a/inspector/getMintActivities?update_authority=" + authority + "&collection_symbol=" + collectionName +"&limit=" + limit + "";
			System.out.println(builder);
			Request request = new Request.Builder().url(builder).method("GET", null).build();
			Response response = null;
			
			try {
				response = client.newCall(request).execute();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String s = null;
			try {
				s = response.body().string();
			
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			JSONParser parser = new JSONParser();

			Object obj = parser.parse(s);
            JSONArray array = (JSONArray)obj;
    	    
            for(Object ob : array) {
               JSONObject entry = (JSONObject) ob;
               Long royaltyFee = (Long)entry.get("royalty_fee");
               String marketplace = (String)entry.get("marketplace");
               String seller = (String)entry.get("seller");
               
               Long price = (Long)entry.get("price");
               
               JSONObject entryObject = (JSONObject) entry.get("metadata");
           	
               String name = (String)entryObject.get("name");
               Long seller_fee_basis_points = (Long)entryObject.get("seller_fee_basis_points"); // Fetch creator fee one time for later usage (lost royalty calculation)
               if(royalty == 0) {
            	  int t = new BigDecimal(seller_fee_basis_points).intValueExact();
            	  royalty = t;
               }
               String mint = (String)entryObject.get("mint");
               String signature = (String)entry.get("signature");
             
               if(readData.contains(signature)) { // Skipping if entry(tx) already exists in the file
            	   continue;
               }
               
               long length = (long) (Math.log10(royaltyFee) + 1);

       			double royalty = 0;

       			
       			if (length != 0) {

       			if (length >= 9) {
       				royalty = (royaltyFee.doubleValue() / 1000000000);

       			}

       			if (length <= 8) {
       				royalty = (royaltyFee.doubleValue() / 10000000000L);

       			}
       		}
       			
       	
    		double priceD = 0;
    		priceD = (price.doubleValue() / 1000000000);

    		String priceString = "" + priceD;
       		
       		if(royalty == 0.0 || royalty == 0) { // Royalty was not paid
       			data.add(new String[] { name, mint, "" + royalty, marketplace, signature, priceString, seller});
       		}
       		
       		if(royalty > 0) { // Royalty was paid
       			data.add(new String[] { name, mint, "" + royalty, marketplace, signature, priceString, seller});
       			}
            }
            
            File file = new File("data.csv");
            if(!file.exists()){
              try {
        		file.createNewFile();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		}
            }
           
      	  
         	 
  	        // create FileWriter object with file as parameter
  	        FileWriter outputfile = new FileWriter(file);
  	  
  	        // create CSVWriter object filewriter object as parameter
  	        CSVWriter writer = new CSVWriter(outputfile);
  	        
  	        
  	        writer.writeAll(data);
	        writer.close();
  	        // create a List which contains String array
            
           
  	        
		}
		catch(Exception e) {
			return;
		}
		

	}

}
