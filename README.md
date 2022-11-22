# SmartRoyalty by Flokyy 

# About Smart Royalty
- Smart Royalty is a discord tool that tracks and saves royalty history and gives projects an overview of royalty fulfillment, analysis or and much more.
- This projects uses the CoralCube API as the main data source: https://optemization.notion.site/optemization/Coral-Cube-Royalty-API-Documentation-4c37410d75ed40fe84ec212c82e33ac2

# 1 Minute Video Overview
- Coming very soon

# Demo
- You can test the Smart Royalty tool on our discord: https://discord.gg/q7pJcPgvKz
- Please use only this discord for the review.

# Installation
- Step 1: Clone/Download the repository
- Step 2: Import all required libraries which haven't been implemented automatically by Maven.
- Step 3: Create a new discord bot on https://discord.com/developers/docs/intro if you haven't yet.
- Step 4: Enable the PRESENCE INTENT inside the Discord Developer Portal (https://discord.com/developers)
- Step 4: Change the "secret_key" inside the SmartRoyalty class.
- Step 5: Update the "collection" String. Use the exact Magic Eden marketplace symbol for the "collection" like in the picture shown below. 
<img width="561" alt="Bildschirmfoto 2022-11-21 um 11 12 25" src="https://user-images.githubusercontent.com/68162827/203024425-2f64821c-c9f1-49eb-ab44-d145503d57b3.png">

- Step 6: Update the "update_authority" String. You can find the update authority from any collection at https://solscan.io using a token-address from the collection.
<img width="1273" alt="Bildschirmfoto 2022-11-21 um 11 06 02" src="https://user-images.githubusercontent.com/68162827/203023933-11849ba8-7756-4592-9d18-8366007114de.png">

- Step 7: Adjust the "dashboardChannel" long value with your channel ID (long) inside the Dashboard class. Make sure to enable the Developer Mode inside Discord to retrieve the channel ID by right-clicking on the channel → "Copy ID".
- Step 8: Export the whole project as a runnable jar file.
- Step 9: Run the runnable jar file on a virtual machine and invite the discord bot into your server and grant permissions to the bot (Administrator suggested).
⇒ If everything was set up correctly, Smart Royalty will start fetching data from the API and will post the dashboard automatically inside the dashboard channel.

# All Functions of Smart Royalty
- Here you can find the explanation of each function of Smart Royalty:

# Fulfillment & Rejection Rate
- Calculates an overview how many users paid/not paid the Creator Royalty.
<img width="477" alt="Bildschirmfoto 2022-11-21 um 12 48 11" src="https://user-images.githubusercontent.com/68162827/203044505-1c0b177b-3a34-43fc-b729-ed51d39a8a61.png">

# Gained & Lost Royalty Calculation

Gained Royalty:
- Collects the overall paid royalties from all data sales and calculates a total gained Creator Royalty in SOL and USD($).

Lost Royalty:
- Collects the overall lost royalties by using the seller_fee_points from the defined collection and multiplying the creator fee with the selling price from each sale from the data sales.

![Screenshot (1149)](https://user-images.githubusercontent.com/68162827/203119484-f638865e-7d15-429e-b8b7-983cf0f99f0e.png)

# Sales Allocation
- Creates a pie chart using the data.csv for the sales allocation on the different marketplaces.
- During the reading process, Smart Royalty automatically checks each marketplace and their sales on the given collection.

![PieChart](https://user-images.githubusercontent.com/68162827/203050452-81706ed7-0c00-488f-9312-21e99f8b07c9.png)

# Download Royalty Payers
- Downloads a csv file containing all wallets and further information, which paid the Creator Royalty for the given collection.
- This function can be really useful to projects who would like to reward users that respect their creator royalty and want to give them something back.

![Screenshot_1145](https://user-images.githubusercontent.com/68162827/203057888-15cef841-982e-433a-a66d-cdfde5b7f226.png)

# Download All Sales
- Downloads the data.csv file containing all data sales.

# Track specific wallets
- Search all sales for specific wallet as the seller. It will post results if the input is found inside the data of sales.

![Screenshot_1146](https://user-images.githubusercontent.com/68162827/203058041-b9ae8df7-0357-4ba1-b565-b1545ac3b2b4.png)

# Track specific token address
- Search for a specific token address inside the data.csv. It will post results if the token-address is found inside the data sales.

![Screenshot_1147](https://user-images.githubusercontent.com/68162827/203058122-c7da7c32-58bf-42d4-836d-6a36f3d237e8.png)

# Contact for help
Twitter: https://twitter.com/SolFloky


