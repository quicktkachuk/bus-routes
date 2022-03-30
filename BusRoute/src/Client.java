//Tie things together here


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        RouteFinder route = new RouteFinder();
        route.getRouteStops("https://www.communitytransit.org/busservice/schedules/route/123");
//        Scanner input = new Scanner(System.in);
//        boolean exit = false;
//
//        while(!exit) {
//            System.out.print("Please enter a letter that your destinations start with: ");
//            char selection = Character.toUpperCase(input.next().charAt(0));
//
//            route.getBusRoutesUrls(selection).entrySet().forEach(characterMapEntry -> {
//                Map<String, String> innerMap = characterMapEntry.getValue();
//                System.out.print(characterMapEntry.getKey() + ":");
//                innerMap.entrySet().forEach(innerMapEntry->{
//                    System.out.print(" " + innerMapEntry.getKey());
//                });
//                System.out.println();
//            });
//
//            input.nextLine();
//            System.out.print("Please enter your destination: ");
//            String destination = input.nextLine();
//            System.out.print("Please enter a route ID: ");
//            String routeID = input.nextLine();
//            System.out.println();
//
//            String url = route.TRANSIT_WEB_URL + route.getBusRoutesUrls(selection).get(destination).get(routeID);
//            Map<String, LinkedHashMap<String, String>> routeStops = route.getRouteStops(url);
//
//            for(int i = 0; i < route.getCurrentDestinations().length; i++){
//                System.out.print("Destination: " + route.getCurrentDestinations()[i]);
//                routeStops.get(route.getCurrentDestinations()[i]).entrySet().forEach(routeStopMapEntry->{
//                    System.out.print("\n" + routeStopMapEntry.getKey() + ": ");
//                    System.out.print(routeStopMapEntry.getValue());
//                });
//                System.out.println("\n");
//            }
//
//            System.out.print("If you would like to check another destination, type y/Y to continue, " +
//                    "or any other character to exit: ");
//            char exitCheck = Character.toUpperCase(input.next().charAt(0));
//            System.out.println();
//            if(exitCheck != 'Y'){
//                exit = true;
//            }
//        }
    }
}
