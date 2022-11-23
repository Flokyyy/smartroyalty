package de.flokyy.smartroyalty.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.style.PieStyler.LabelType;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;

import de.flokyy.smartroyalty.SmartRoyalty;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dashboard {
	
	public static double total = 0;
	public static double paidRoyalty = 0;
	public static double notPaid = 0;
	
	public static int magicEdenHits = 0;
	public static int yawwwHits = 0;
	public static int foxySwapHits = 0;
	public static int hadeSwapHits = 0;
	public static int coralCubeHits = 0;
	public static int solanArtHits = 0;
	
	public static HashMap royaltyPaidMap = new HashMap<>();
	public static HashMap royaltyNotPaidMap = new HashMap<>();
	
	public static Long dashboardChannel = 1043493298309959761L;
	
	public static void clearData() {
		total = 0;
		paidRoyalty = 0;
		notPaid = 0;
		
		magicEdenHits = 0;
		yawwwHits = 0;
		foxySwapHits = 0;
		hadeSwapHits = 0;
		coralCubeHits = 0;
		solanArtHits = 0;
		
		royaltyPaidMap.clear();
		royaltyNotPaidMap.clear();
	}
     
	 
	public static File salesChart()  {	    
		PieChart chart = null;
		try {
			chart = new PieChartBuilder().width(400).height(400)
					    .title("Sales Allocation").build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				// Customize Chart
				  chart.getStyler().setChartTitleVisible(false);
				  chart.getStyler().setPlotContentSize(0.6);
				  chart.getStyler().setPlotBorderColor(Color.WHITE);
				  chart.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
				  chart.getStyler().setDonutThickness(0.5);
				  Color[] colorArr = {new Color(215,225,250),new Color(35,125,120),     
				    new Color(255,115,135), new Color(70, 50, 140),new    
				    Color(165,5,75) };
				  chart.getStyler().setSeriesColors(colorArr);
				  chart.getStyler().setAnnotationTextPanelBackgroundColor
				  (Color.BLACK);
				  chart.getStyler().setAnnotationLineColor(Color.BLUE);
				  chart.getStyler().setAnnotationTextFontColor(Color.WHITE);
				  chart.getStyler().setChartTitleBoxBackgroundColor(Color.WHITE);
				  chart.getStyler().setChartTitleBoxVisible(false);
				  chart.getStyler().setChartTitleBoxBorderColor(Color.CYAN);
				  // chart.getStyler().setChartTitleVisible(false);
				  chart.getStyler().setChartBackgroundColor(Color.WHITE);
				  //chart.getStyler().setCircular(false);
				  chart.getStyler().setLegendVisible(true);
				  chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
				  chart.getStyler().setLegendLayout(LegendLayout.Horizontal);
				  chart.getStyler().setLegendBorderColor(Color.CYAN);
				  //Label
				  chart.getStyler().setLabelsVisible(true);
				  chart.getStyler().setLabelsDistance(1.1);
				  chart.getStyler().setLabelsFontColor(Color.BLACK);
				  chart.getStyler().setLabelsFontColorAutomaticEnabled(false);
				  chart.getStyler().setAnnotationLineStroke(new BasicStroke(1,   
				      BasicStroke.CAP_BUTT, BasicStroke.CAP_ROUND));
				  chart.getStyler().setForceAllLabelsVisible(true);
				  chart.getStyler().setLabelType(LabelType.Value);
			
				
				  if(magicEdenHits != 0) {
				  chart.addSeries("MagicEden", magicEdenHits);
				  }
				  if(hadeSwapHits >= 1) {
					 chart.addSeries("HadeSwap", hadeSwapHits);
				  }
				  if(foxySwapHits >= 5) {
					  chart.addSeries("FoxySwap", foxySwapHits);
				  }
				  if(coralCubeHits >= 6) {
					  chart.addSeries("CoralCube", coralCubeHits);
				  }
				  if(yawwwHits >= 7) {
					  chart.addSeries("Yawww", yawwwHits);
				  }
				  if(solanArtHits >= 8) {
					  chart.addSeries("SolanArt", solanArtHits);
				  }
				
				try {
		
					BitmapEncoder.saveBitmapWithDPI(chart, "Chart", BitmapFormat.PNG, 300);
				} catch (IOException e) {
				  e.printStackTrace();
				}
				
				File f = new File("Chart.png");
				
				return f;
				

	
	}
	
	 public static double round(double d, int decimalPlace){
		 BigDecimal bd = new BigDecimal(Double.toString(d));
		 bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		 return bd.doubleValue();
		    
	}
	 
	 // Gets the current SOL USD price
	 public static Double getSolanaPrice() {
			OkHttpClient client = new OkHttpClient().newBuilder()
				    .connectTimeout(10,TimeUnit.SECONDS)
				    .writeTimeout(10,TimeUnit.SECONDS)
				    .readTimeout(30,TimeUnit.SECONDS)
				    .build();
			Request request = new Request.Builder()
					.url("https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=usd")
					.method("GET", null).build();
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

			JSONObject json = null;
			try {
				json = (JSONObject) parser.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			JSONObject jsonObject = (JSONObject) json.get("solana");

			Double price = (Double) jsonObject.get("usd");
			
			return price;
			  
		}
	 
	public static void start() {
		
		String line = "";
	    String splitBy = ",";
	    
	    Double lostRoyalty = 0.0;
	    Double gainedRoyalty = 0.0;
	    
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
	      System.out.println("Startin reading data from file...");
	   
	      while ((line = br.readLine()) != null)
	      {
	    	
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
	        
	        total++; // +1 total 
	        
	        Double royaltyD = Double.parseDouble(royalty);
	        if(royaltyD == 0.0 || royaltyD == 0) { // Royalty was not paid
	        	notPaid++;
	        
	        	Double priceD = Double.parseDouble(price);
	        	Double creatorRoyalty = (double) (CoralCubeAPI.royalty / 100) / 100;
	        	
	        	
	        	Double realRoyalty = priceD * creatorRoyalty;
	        	
	        	lostRoyalty = lostRoyalty + realRoyalty;
	        	
	        	if(!royaltyNotPaidMap.containsKey(marketplace)) {
	        		royaltyNotPaidMap.put(marketplace, lostRoyalty);
	        	}
	        	else {
	        		Double value = (Double) royaltyNotPaidMap.get(marketplace);
	        		royaltyNotPaidMap.put(marketplace, value + lostRoyalty);
	        	}
	        }
	        
	        if(royaltyD > 0.0) { // Royalty was paid
	        	paidRoyalty++;
	        	
	        	gainedRoyalty = gainedRoyalty + royaltyD;
	        	if(!royaltyNotPaidMap.containsKey(marketplace)) {
	        		royaltyNotPaidMap.put(marketplace, gainedRoyalty);
	        	}
	        	else {
	        		Double value = (Double) royaltyNotPaidMap.get(marketplace);
	        		royaltyNotPaidMap.put(marketplace, value + gainedRoyalty);
	        	}
	        }
	        
	        if(marketplace.equalsIgnoreCase("MagicEden V2")) {
	        	magicEdenHits++;
	        }
	        if(marketplace.equalsIgnoreCase("FoxySwap")) {
	        	foxySwapHits++;
	        }
	        if(marketplace.equalsIgnoreCase("CoralCube")) {
	        	coralCubeHits++;
	        }
	        if(marketplace.equalsIgnoreCase("HadeSwap")) {
	        	hadeSwapHits++;
	        }
	        if(marketplace.equalsIgnoreCase("SolanArt")) {
	        	solanArtHits++;
	        }
	        if(marketplace.equalsIgnoreCase("Yawww")) {
	        	yawwwHits++;
	         }
	        }
	       }
	      
	       EmbedBuilder b = new EmbedBuilder();
		   b.setTitle("ROYALTY DASHBOARD");
		   b.addField(":gem: | Collection", "" + SmartRoyalty.collection.replaceAll("_", " ").toUpperCase() +" - [MagicEden](" + "https://magiceden.io/marketplace/" + SmartRoyalty.collection +")",false);
		   
		   if((total == 0)) {
			 b.setDescription("No data has been fetched yet. Please wait!");
		   }
		   if(!(total == 0)) {
			Double royaltyPaid = round((double) (paidRoyalty / total) * 100, 3);
		   	Double royaltyNotPaid = round((double) (notPaid / total) * 100, 3);
		 
		   	b.addField(":white_check_mark: | Royalty Fulfillment Rate", "" + "" + royaltyPaid + "% (" + Math.round(paidRoyalty) +" Users **paid** the Creator Royalty out of " + Math.round(total) + " total saved sales)",false);
		   	b.addField(":small_red_triangle_down: | Royalty Rejection Rate", "" + "" + royaltyNotPaid + "% (" + Math.round(notPaid) +" Users **didn't** pay the Creator Royalty out of " + Math.round(total) + " total saved sales)",false);
		   
		   	Double solPrice = getSolanaPrice();
		   	if(solPrice != null) {
		   	b.addField(":money_with_wings: | Gained Royalty", "" + round(gainedRoyalty,3) + " SOL (" + round(gainedRoyalty * solPrice, 3) + "$)",false);
		   	}
		   	else {
		   	 	b.addField(":money_with_wings: | Gained Royalty", "" + round(gainedRoyalty,3) + " SOL",false);
		   	}
		   	
		   	if(solPrice != null) {
			   	b.addField(":o: | Lost Royalty", "" + round(lostRoyalty, 3) + " SOL (" + round(lostRoyalty * solPrice, 3) + "$)",false);
			   	}
			   	else {
			   	 	b.addField(":o: | Lost Royalty", "" + round(lostRoyalty, 3) + " SOL",false);
			   	}
		   	
		   	//b.addField(":bar_chart: | Marketplace Analysis", "Overall Sales on MagicEden: " + magicEdenHits + "\nOverall Sales on HadeSwap: " + hadeSwapHits + "\nOverall Sales on FoxySwap: " + foxySwapHits + "\nOverall Sales on CoralCube: " + coralCubeHits+ "\nOverall Sales on Solanart: " + SolanartHits + "\nOverall Sales on Yawww: " + yawwwHits,false);
		   
		   	b.addField(":receipt: | Total saved transactions", "" + "" + Math.round(total),false);
		    }
		   
		    b.setColor(Color.cyan);
		    b.setFooter("Powered by SmartRoyalty");
		    b.setTimestamp(OffsetDateTime.now(Clock.systemDefaultZone()));
		   
		    try {
		     TextChannel tc = SmartRoyalty.jda.getTextChannelById(dashboardChannel); 	  
			 MessageHistory history = new MessageHistory(tc);
  		  	 List<Message> msgs = null;
  		  	 msgs = history.retrievePast(10).complete();
  		  	 if(!msgs.isEmpty()) {
  		  		tc.deleteMessages(msgs).queue();
  		  	}
  		  }
  		
  		 catch(Exception e) {
  			System.out.println("Error when trying to clear the dashboard channel");
  			return;
  		  }
		 
		  List<Button> buttons = new ArrayList<Button>();
		  buttons.add(Button.secondary("Sales", "Sales Allocation"));
		  buttons.add(Button.secondary("Royalty", "Download Royalty Payers"));
		  buttons.add(Button.secondary("All", "Download all Sales"));
		  buttons.add(Button.secondary("Wallet", "Track specific wallets"));
		  buttons.add(Button.secondary("Token", "Track a specific token-address"));

		  TextChannel tc = SmartRoyalty.jda.getTextChannelById(dashboardChannel);  
		  tc.sendMessageEmbeds(b.build()).setActionRow(buttons).queue();
		  tc.sendMessage(":white_check_mark: | Updates automatically").queue();

		 
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	    }
		
}

}
