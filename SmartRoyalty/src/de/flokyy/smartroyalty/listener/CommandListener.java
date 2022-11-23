package de.flokyy.smartroyalty.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import de.flokyy.smartroyalty.utils.Dashboard;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;

public class CommandListener extends ListenerAdapter {
	   
    // Tracks a specific wallet inside the data.csv and returns information for further usage
	public String searchForToken(String tokenaddress) { 
	    String line = "";
	    String splitBy = ",";
	    
	    String export = "";
	    
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
	      System.out.println("Starting reading...");
	   
	      while ((line = br.readLine()) != null)
	      {
	    	System.out.println(line);
	        String[] array = line.split(splitBy);
	        // use comma as separator  
	        String nft_name = array[0].replace("\"", "");
	        String token = array[1].replace("\"", "");
	        String royalty = array[2].replace("\"", "");
	        String marketplace = array[3].replace("\"", "");
	        String tx = array[4].replace("\"", "");
	        String price = array[5].replace("\"", "");
	        String seller = array[6].replace("\"", "");
	        
	        Double royaltyD = Double.parseDouble(royalty);
	        if(token.equalsIgnoreCase(tokenaddress)) { // Entry was found
	          if(royaltyD > 0.0) { // Royalty was paid
	          String s = export +"\nNFT: ``"+ nft_name + "`` | Royalty: Paid [:green_circle:] | [TRANSACTION](https://solana.fm/tx/" + tx + ") | Price: ``" + price + " SOL``";
	          export = s;
	          }
	        
	          else { //Royalty was not paid
	        	  String s = export +"\nNFT: ``"+ nft_name + "`` | Royalty: Not Paid [:small_red_triangle_down:] | [TRANSACTION](https://solana.fm/tx/" + tx + ") | Price: ``" + price + " SOL``";
	    		  export = s;
	          }
	        }
	        continue;
	      }
	      
	      return export;
	     
	      
	    }
	    catch(IOException e) {
	     return null;
	      }
	 }
		
		
	// Tracks a specific wallet inside the data.csv and returns information for further usage
	public String searchForWallet(String wallet) { 
	    String line = "";
	    String splitBy = ",";
	    
	    String export = "";
	    
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
	      System.out.println("Starting reading...");
	   
	      while ((line = br.readLine()) != null)
	      {
	    	System.out.println(line);
	        String[] array = line.split(splitBy);
	        // use comma as separator  
	        String nft_name = array[0].replace("\"", "");
	        String token = array[1].replace("\"", "");
	        String royalty = array[2].replace("\"", "");
	        String marketplace = array[3].replace("\"", "");
	        String tx = array[4].replace("\"", "");
	        String price = array[5].replace("\"", "");
	        String seller = array[6].replace("\"", "");
	        
	        if(seller.equalsIgnoreCase(wallet)) { // Wallet was found inside the data.csv
	        	String s = export +"\nNFT: ``"+ nft_name + "`` | [TRANSACTION](https://solana.fm/tx/" + tx + ") | Price: ``" + price + " SOL``";
	        	export = s;
	        }
	        continue;
	      }
	      
	      return export;
	    }
	    catch(IOException e) {
	     return null;
	      }
	}
	
	
	// Gets all wallets that paid the Royalty
	public static void getAllRoyaltyPayers() {
	    String line = "";
	    String splitBy = ",";
	    
	    List<String[]> data = new ArrayList<String[]>();
	    
	    data.clear();
	    File f = new File("royaltypayers.csv");
	    if(f.exists()) {
	      f.delete();
	    }
	      File f2 = new File("royaltypayers.csv");
		    if(!f.exists()) {
		      try {
				f2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		    
	    try {
	      // parsing a CSV file into BufferedReader class constructor  
	      BufferedReader br = new BufferedReader(new FileReader("data.csv"));
	      System.out.println("Starting reading...");
	   
	      while ((line = br.readLine()) != null)
	      {
	    	System.out.println(line);
	        String[] array = line.split(splitBy);
	        // use comma as separator  
	        String nft_name = array[0].replace("\"", "");
	        String token = array[1].replace("\"", "");
	        String royalty = array[2].replace("\"", "");
	        String marketplace = array[3].replace("\"", "");
	        String tx = array[4].replace("\"", "");
	        String price = array[5].replace("\"", "");
	        String seller = array[6].replace("\"", "");
	        
	        if(nft_name != null) {
	          Double d = Double.parseDouble(royalty);
	          if(d > 0.0) { //Royalty was paid
	          data.add(new String[] { nft_name, token, royalty, marketplace, tx, price, seller});
	          }
	         }
	         continue;
	        }
	      
	        // create FileWriter object with file as parameter
	        FileWriter outputfile = new FileWriter(f2);
	  
	        // create CSVWriter object filewriter object as parameter
	        CSVWriter writer = new CSVWriter(outputfile);
	        
	        
	        writer.writeAll(data);
	        writer.close();
	        // create a List which contains String array
	      
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	      }
		}
	

	@Override
	public void onModalInteraction(ModalInteractionEvent event) {
		event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
		InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
	    hook.setEphemeral(true);
	    
		if(event.getModalId().equalsIgnoreCase("royaltytracker")) {
		
			
			ModalMapping input = event.getValue("Wallet");
			String wallet = input.getAsString();
			String data = searchForWallet(wallet);
			
			if(data.isEmpty()) {
			hook.sendMessage("No entries for this wallet were found.").queue();
			}
			if(data != null) {
			  hook.sendMessage(data).queue();
			}
		}
		
		if(event.getModalId().equalsIgnoreCase("tokentracker")) {
			ModalMapping input = event.getValue("Token");
			String token = input.getAsString();
			String data = searchForToken(token);
			
			if(data.isEmpty()) {
			  hook.sendMessage("No entries for this token were found.").queue();
			}
			if(data != null) {
			  hook.sendMessage(data).queue();
			}
		}
	}
	
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		
		if(event.isAcknowledged() == true) {
			return;
		}
		
		if (event.getComponentId().equals("Sales")) {
			File pieChart = Dashboard.salesChart();
			event.reply("Here you can see the overall sales allocation").addFile(pieChart).setEphemeral(true).queue();
		}
		
		if (event.getComponentId().equals("All")) {
			event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
			InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
		    hook.setEphemeral(true);
		    File f = new File("data.csv");
			
		    hook.sendMessage("Here are all sales that are currently saved.").addFile(f).queue();
		}
		
		if (event.getComponentId().equals("Royalty")) {
			event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
			InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
		    hook.setEphemeral(true);
		    
		    getAllRoyaltyPayers();
		    File f = new File("royaltypayers.csv");
			
		    hook.sendMessage("Here are all sales from wallets that paid the creator royalty").addFile(f).queue();
		}
		
		if (event.getComponentId().equals("Wallet")) {
				 TextInput name = TextInput.create("Wallet", "Wallet-Address", TextInputStyle.SHORT)
	          .setPlaceholder("Enter the wallet address here")
	          .setMinLength(1)
	          .setRequired(true)
	          .build();
	 
			 Modal modal = Modal.create("royaltytracker", "Royalty Wallet Tracker").addActionRow(name).build();
			 event.replyModal(modal).queue();
		}
		
		if (event.getComponentId().equals("Token")) {
			 TextInput name = TextInput.create("Token", "Token-Address", TextInputStyle.SHORT)
			          .setPlaceholder("Enter a vaild token-address here")
			          .setMinLength(1)
			          .setRequired(true)
			          .build();
			 
			
			Modal modal = Modal.create("tokentracker", "Royalty Token Tracker").addActionRow(name).build();
			event.replyModal(modal).queue();
		}
	}
}
