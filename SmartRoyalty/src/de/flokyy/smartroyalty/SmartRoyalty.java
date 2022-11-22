package de.flokyy.smartroyalty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import de.flokyy.smartroyalty.listener.CommandListener;
import de.flokyy.smartroyalty.utils.CoralCubeAPI;
import de.flokyy.smartroyalty.utils.Dashboard;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class SmartRoyalty {

	public static boolean startup;
	public static JDABuilder builder;
	public static JDA jda;
	
	public static String collection = "lily";
	public static String update_authority = "834eju6tqRnTZYremg5PQiEhnRmaHqLKj7wyLt4eNeHa";
	public static String secret_key = "INPUTYOURKEYHERE"; // Discord Bot Secret Key
	public static void main(String[] args) {
		
		builder = JDABuilder.createDefault(secret_key); 
		builder.setStatus(OnlineStatus.ONLINE);
		
		builder.addEventListeners(new CommandListener()); // Implementing the Command Listener
	     
		try {
			jda = builder.build();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Timer timer = new Timer();
		 TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					try {
					
						CoralCubeAPI.data.clear();
						CoralCubeAPI.readData.clear();
						
						String limit = "50"; // Gets the last 50 sales
						CoralCubeAPI.getLatestData(limit, update_authority, collection); //Fetching new data
						
						Dashboard.clearData(); // Clear data before
						Dashboard.start(); // Starting the dashboard
						}

						catch (Exception e2) {
							System.out.println("Error when trying to post the royalty dashboard.");
						}
				}
			};
		  timer.schedule(timerTask, 0l, 300000); // Update every 5 minutes
		
		}
}