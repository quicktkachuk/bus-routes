//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Scanner;
//
////Handles user input and makes calls to RouteFinder.java object
//public class caller {
//    public caller(){
//        RouteFinder route = new RouteFinder();
//        Scanner input = new Scanner(System.in);
//        boolean exit = false;
//
//        while(!exit) {
//            System.out.print("Please enter a letter that your destinations start with: ");
//            char selection = Character.toUpperCase(input.next().charAt(0));
//
////            Map<String, Map<String, String>> outerMap = route.getBusRoutesUrls(selection);
//
//            //Prints out associated
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
//            String routeID = input.next();
//            System.out.println();
//
//            String[] dumbTest = new String[3];
//            dumbTest[0] = Character.toString(selection);
//            dumbTest[1] = destination;
//            dumbTest[2] = routeID;
//
//            route.dumbTest(dumbTest);
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
//    }
//}