/*
Matthew Tkachuk
2021/02/05
CS320
Assignment01
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;

public class RouteFinder implements IRouteFinder{

    private Map<Character, Map<String, Map<String, String>>> routeMap = new HashMap<>();
    private Map<String, LinkedHashMap<String, String>> routeStopMap = new HashMap<>();

    //Used for testing + client when printing out stops
    private String[] currentDestinations = new String[2];

    //Constructor, builds out entirety of routeMap
    public RouteFinder(){

        //Grab bus schedule overview
        String webContent = getUrlText(this.TRANSIT_WEB_URL);

        //Grab larger chunks of text. Each chunk should contain the location, url, route for a particular location
        String blockRegex = "<h3>(.*)</h3>(([\\s]*?[\\d\\D]*?<hr id=)|([\\s]*?[\\d\\D]*? denotes))";
        Pattern blockPattern = Pattern.compile(blockRegex);
        Matcher blockMatcher = blockPattern.matcher(webContent);

        String routeRegex = "<a href=\"/schedules/(.*)\"(>|.*)>(.*)</a[\\D\\d]*?-1\">(.*)<";
        Pattern routePattern = Pattern.compile(routeRegex);

        //Outer loop loops through matches from blockMatcher
        while(blockMatcher.find()){
            Map<String, String> innerMap = new HashMap<>();
            String text = blockMatcher.group();
            Matcher routeMatcher = routePattern.matcher(text);

            //Processes details from block, stores in map
            while(routeMatcher.find()) {
                String key = routeMatcher.group(3);
                String value = routeMatcher.group(1);

                innerMap.put(key, value);
            }

            //Check to see if current destination initial already has entries
            if(this.routeMap.containsKey(blockMatcher.group(1).charAt(0))){

                //If so, we grab existing map for that initial, and add current city to it to avoid
                // wiping existing content
                Map<String, Map<String, String>> medMap = routeMap.get(blockMatcher.group(1).charAt(0));
                medMap.put(blockMatcher.group(1), innerMap);
            }else {
                //Otherwise, no entry for that initial, so we can add straight into the map
                Map<String, Map<String, String>> medMap = new HashMap<>();
                medMap.put(blockMatcher.group(1), innerMap);
                this.routeMap.put(blockMatcher.group(1).charAt(0), medMap);
            }
        }
    }

    /**
     * The function returns the route URLs for a specific destination initial using the URL text
     * @param destInitial This represents a destination (e.g. b/B is initial for Bellevue, Bothell, ...)
     * @return key/value map of the routes with key is destination and
     *       value is an inner map with a pair of route ID and the route page URL
     *       (e.g. of a map element <Brier, <111, https://www.communitytransit.org/busservice/schedules/route/111>>)
     *
     * @Override
     */
    public Map<String, Map<String, String>> getBusRoutesUrls(char destInitial){
        //Keeping this simple, since construction of this class creates a completed map, we just grab the inner map
        // associated with that initial after validating input
        if(Character.isAlphabetic(destInitial)) {
            destInitial = Character.toUpperCase(destInitial);
            Map<String, Map<String, String>> map = this.routeMap.get(destInitial);

            //If destInitial is an alphabetic character, but does not correspond to an entry in the map,
            // a run time exception error is thrown
            if (map == null) {
                throw new RuntimeException();
            } else {
                return map;
            }
        }else{
            throw new RuntimeException();
        }
    }

    /**
     * The function returns route stops, grouped by destination To/From, for a certain route ID url
     * @param url: the URL of the route that you want to get its bus stops
     * @return map of the stops grouped by destination with key is the destination (e.g. To Bellevue)
     *  and value is the list of stops in the same order that it was parsed on
     * (e.g. of a map element <To Mountlake Terrace, <<1, Brier Rd &amp; 228th Pl SW>, <2, 228th St SW &amp; 48th Ave W>, ...>>)
     */
    @Override
    public Map<String, LinkedHashMap<String, String>> getRouteStops(String url) {

        //Use regex to validate URL, if invalid, run time exception is thrown
        String webRegex = "https://www\\.communitytransit\\.org/busservice/schedules/route/([\\da-zA-Z-]{3,16}$)";
        Pattern urlPattern = Pattern.compile(webRegex);
        Matcher urlMatcher = urlPattern.matcher(url);
        if(!urlMatcher.find()){
            throw new RuntimeException();

        }else{

            //Grab content from validated URL
            String webContent = getUrlText(url);

            //Set several different regex to grab data
            //Block grabs large chunks of text that contain relevant data. Each block should contain stops for a route
            String blockRegex = "<h2>Weekday[\\s\\d\\D]*?</thead>";
            Pattern blockPattern = Pattern.compile(blockRegex);
            Matcher blockMatcher = blockPattern.matcher(webContent);

            //destinationRegex/routeRegex grabs the details we want
            String destinationRegex = "<small>(.*)</small>";
            Pattern destinationPattern = Pattern.compile(destinationRegex);
            String routeRegex = "<p>(.*?)</p>";
            Pattern routePattern = Pattern.compile(routeRegex);
            int destCounter = 0;

            //Check to make sure route is valid
            if(!blockMatcher.find()){
                throw new RuntimeException();
            }

            //Grabs all stops for each direction
            while (blockMatcher.find()) {
                LinkedHashMap<String, String> innerMap = new LinkedHashMap<>();
                Matcher destinationMatcher = destinationPattern.matcher(blockMatcher.group());
                Matcher routeMatcher = routePattern.matcher(blockMatcher.group());
                int counter = 1;

                //Process all stops for one direction
                while (routeMatcher.find()) {
                    innerMap.put(String.valueOf(counter), routeMatcher.group(1));
                    counter++;
                }
                if (destinationMatcher.find()) {
                    this.currentDestinations[destCounter] = destinationMatcher.group(1);
                    destCounter++;
                    if (!this.routeStopMap.containsKey(destinationMatcher.group(1))) {
                        this.routeStopMap.put(destinationMatcher.group(1), innerMap);
                    }
                }
            }
            return this.routeStopMap;
        }
    }

    //Helper function for testing + client
    public String[] getCurrentDestinations(){
        return this.currentDestinations;
    }

    //Helper function, reads all HTML on target url, returns as a String
    private String getUrlText(String url){

        try {
            URLConnection busRoutesBase = new URL(url).openConnection();
            busRoutesBase.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            BufferedReader routeReader = new BufferedReader(new InputStreamReader(busRoutesBase.getInputStream()));
            String webContent = "";
            String line = "";
            while ((line = routeReader.readLine()) != null) {
                webContent += line + "\n";
            }
            if (webContent == null | webContent == "") {
                throw new RuntimeException();
            }

            return webContent;
        }catch(Exception error){
            throw new RuntimeException();
        }
    }

}