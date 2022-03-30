//package com.cs320.assignments.assignment1.test;
//import com.cs320.assignments.assignment1.application.IRouteFinder;
//import com.cs320.assignments.assignment1.application.RouteFinder;

import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;


/**
 * Test fixture for route finder implementations
 * @author Asel S
 */
class RouteFinderTest 
{
	IRouteFinder routeFinder = new RouteFinder();
	
	/**
	 * Passes in valid destinations and expected outer / inner map sizes.
	 * 
	 * @return A stream of arguments
	 */
	private static Stream<Arguments> provideDestinationsForBusRoutes()
	{
		return Stream.of
				(
					Arguments.of('A', 1, 5),
					Arguments.of('B', 3, 8),
					Arguments.of('C', 0, 0),
					Arguments.of('D', 1, 1),
					Arguments.of('E', 3, 33),
					Arguments.of('F', 0, 0),
					Arguments.of('G', 2, 2),
					Arguments.of('H', 0, 0),
					Arguments.of('I', 0, 0),
					Arguments.of('J', 0, 0),
					Arguments.of('K', 0, 0),
					Arguments.of('L', 2, 28),
					Arguments.of('M', 6, 38),
					Arguments.of('N', 0, 0),
					Arguments.of('O', 0, 0),
					Arguments.of('P', 0, 0),
					Arguments.of('Q', 0, 0),
					Arguments.of('R', 0, 0),
					Arguments.of('S', 7, 38),
					Arguments.of('T', 1, 2),
					Arguments.of('U', 0, 0),
					Arguments.of('V', 0, 0),
					Arguments.of('W', 0, 0),
					Arguments.of('X', 0, 0),
					Arguments.of('Y', 0, 0),
					Arguments.of('Z', 0, 0)
					
				);
	}
	
	/**
	 * Passes in invalid route urls.
	 * 
	 * @return A stream of arguments.
	 */
	private static Stream<Arguments> provideInvalidRouteUrlsForBusRoutes()
	{
		return Stream.of
				(
					Arguments.of("https://www.communitytransit.org/busservice/schedules/route/Swift-Green-Lin"),
					Arguments.of("http"),
					Arguments.of(".org"),
					Arguments.of("https://www.communitytransit.org/busservice/schedules/route/298767")
				);
	}
	
	/**
	 * Passes in some invalid destinations
	 * 
	 * \u2164: Roman numeral V
	 * \u02b0: Modifier small h
	 * 
	 * @return A stream of characters. 
	 */
	private static Stream<Character> provideInvalidDestinationsForBusRoutes()
	{
		return "0123456789\u2164\u02b0".chars().mapToObj(c -> (char)c);
	}
	
	@ParameterizedTest
	@MethodSource("provideDestinationsForBusRoutes")
	/**
	 * Checks the actual size of the outer and inner map returned by getBusRouteUrls 
	 * against the expected sizes for both.
	 * 
	 * @param destinationInitial The destination's first character
	 * @param expectedOuterMapSize Expected size for the outer / main map.
	 * @param expectedInnerMapSize Expected size for the inner / nested map.
	 */
	void getBusRoutesUrls_OnValidDestination_ReturnsTrue(char destinationInitial, int expectedOuterMapSize, int expectedInnerMapSize) 
	{
		var mapResult = routeFinder.getBusRoutesUrls(destinationInitial);
		
		int actualOuterMapSize = mapResult.size();
		int actualInnerMapSize = getInnerMapSize(mapResult);
		
		boolean testSucceeded = actualOuterMapSize == expectedOuterMapSize &&
								actualInnerMapSize == expectedInnerMapSize;
		
		assert(testSucceeded);
	}
	
	@ParameterizedTest
	@MethodSource("provideDestinationsForBusRoutes")
	/**
	 * Checks the actual size of the outer and inner map returned by getBusRouteUrls 
	 * against the expected sizes for both. 
	 * 
	 * The destination's first character is set to lowercase.
	 * 
	 * @param destinationInitial The destination's first character
	 * @param expectedOuterMapSize Expected size for the outer / main map.
	 * @param expectedInnerMapSize Expected size for the inner / nested map.
	 */
	void getBusRoutesUrls_OnValidLowercaseDestination_ReturnsTrue(char destinationInitial, int expectedOuterMapSize, int expectedInnerMapSize) 
	{
		var mapResult = routeFinder.getBusRoutesUrls(Character.toLowerCase(destinationInitial));
		
		int actualOuterMapSize = mapResult.size();
		int actualInnerMapSize = getInnerMapSize(mapResult);
		
		boolean testSucceeded = actualOuterMapSize == expectedOuterMapSize &&
								actualInnerMapSize == expectedInnerMapSize;
		
		assert(testSucceeded);
	}
	
	/**
	 * Helper method to calculate inner map size.
	 * 
	 * @param <K> Generic key
	 * @param <V> Generic value with a constraint to implement the Map interface
	 * @param map The main map
	 * 
	 * @return The size of the inner map.
	 */
	private static <K, V extends Map> int getInnerMapSize(Map<K, V> map)
	{
		int innerMapSize = 0;
		
		for (var outerEntry : map.entrySet())
		{
			innerMapSize += outerEntry.getValue().size();
		}
		
		return innerMapSize;
	}
	
	@ParameterizedTest
	@MethodSource("provideInvalidDestinationsForBusRoutes")
	/**
	 * Checks whether the getBusRoutesUrls method throws a run time exception when passed a invalid destination.
	 * 
	 * @param destinationInitial The destination's first character
	 */
	void getBusRoutesUrls_OnInvalidDestination_ThrowsRunTimeException(char destinationInitial) 
	{
		
		Assertions.assertThrows(RuntimeException.class, () -> {
				
				routeFinder.getBusRoutesUrls(Character.toLowerCase(destinationInitial));
			}
		);
	}
	
	@ParameterizedTest
	@MethodSource("provdeInvalidRouteIdsForBusRoutes")
	/**
	 * Checks whether the getRouteStops method throws a run time exception when passed a invalid url.
	 * 
	 * @param url The route url
	 */
	void getRouteStops_OnInvalidRouteUrl_ThrowsException(String url) 
	{
		Assertions.assertThrows(RuntimeException.class, () -> {
			
				routeFinder.getRouteStops(url);
			}
		);
	}
	


}
