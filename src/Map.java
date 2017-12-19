import java.io.File;  // to print .txt for MATLAB
import java.io.FileNotFoundException;  // to print .txt for MATLAB
import java.io.PrintStream;  // to print .txt for MATLAB
import java.util.Scanner;  // for user input


/**
 * Main file to run the program
 * Interact with user for inputs, and print result
 * @author difan
 *
 */
public class Map {
	// begin location, a finish location
	// whether using a skateboard
	// whether to minimize distance or time.
	static Graph g;
	static GraphNode home = null;
	static GraphNode goal = null;
	static boolean skateboard = false;
	static boolean minTime = false;
	static int KruskalVsPrim = 1;
	// constants for printing .txt for image
	static int MapWidthFeet = 5521;
	static int MapHeightFeet = 4369;
	static int MapWidthPixels = 2528;
	static int MapHeightPixels = 2000;
	static int CropLeft = 150;
	static int CropDown = 125;
	static String fileName = "RouteCropped.txt";

	public static void main(String[] args) {
		g = new Graph();

		intro();
		SinglyLinkedList<GraphEdge> tour;
		if (goal != null) {
			tour = new FindMinPath_Dijkstra(g, home, goal, skateboard, minTime).getMinPath();
		} else {
			if (KruskalVsPrim == 1) {
				tour = new FindCampusTour_Prim(g, home, skateboard, minTime).getCampusTour();
				// if only need MST, use code below and comment above
				//tour = new FindCampusTour_Prim(g, home, skateboard, minTime).prim();
			} else if (KruskalVsPrim == 2) {
				tour = new FindCampusTour_Kruskal(g, home, skateboard, minTime).getCampusTour();
			} else {  // 3
				tour = new FindCampusTour_Prim_Dijkstra(g, home, skateboard, minTime).getCampusTour();
			}
		}
		report(tour);
	}
	
	// interact with user for inputs
	public static void intro() {
		Scanner console = new Scanner(System.in);
		String input = null;
		System.out.println("************* WELCOME TO THE BRANDEIS MAP*************");
		while (home == null) {
			System.out.print("Enter start (return to quit): ");
			input = console.nextLine();
			for (int i = 0; i < g.vertex.length; i++) {
				GraphNode node = g.vertex[i];
				String target = input.toLowerCase().trim();
				if (node.name.toLowerCase().contains(target) || node.label.toLowerCase().equals(target)) {
					home = node;
					node.isHome = true;
				}
			}
		}
		System.out.print("Enter finish (or return to do a tour): ");
		input = console.nextLine();
		if (input.length() > 0) {
			for (int i = 0; i < g.vertex.length; i++) {
				GraphNode node = g.vertex[i];
				String target = input.toLowerCase().trim();
				if (node.name.toLowerCase().contains(target) || node.label.toLowerCase().equals(target)) {
					goal = node;
					node.isGoal = true;
				}
			}
		}
		if (goal == null) {
			input = "";
			while (input.length() == 0 || !Character.isDigit(input.charAt(0))) {
				System.out.print("Select an algorithm for the tour (1)Prim, (2)Kruskal, (3)Shortest Tour: ");
				input = console.nextLine();
			}
			KruskalVsPrim = Integer.parseInt(input);
		}
		
		System.out.print("Have a skateboard (y/n - default=n)? ");
		input = console.nextLine();
		if (input.equals("y")) {
			skateboard = true;
		}
		System.out.print("Minimize time (y/n - default=n)? ");
		input = console.nextLine();
		if (input.equals("y")) {
			minTime = true;
		}
		console.close();
	}
	
	// report tour
	public static void report(SinglyLinkedList<GraphEdge> tour) {		
		try {
			String res = "";
			double time = 0;
			int length = 0;
			int legs = 0;
			PrintStream output = new PrintStream(new File(fileName));
			SinglyLinkedListNode<GraphEdge> node = tour.head;
			while (node != null) {
				GraphEdge e = node.getData();
				res += "\n" + e.printEdge(skateboard) + "\n";
				time += e.getTime(skateboard);
				length += e.length;
				legs += 1;
				node = node.next;
				// generate Route.txt
				int a = e.start.x * MapHeightPixels / MapHeightFeet - CropLeft;
				int b = e.start.y * MapWidthPixels / MapWidthFeet - CropDown;
				int c = e.end.x * MapHeightPixels / MapHeightFeet - CropLeft;
				int d = e.end.y * MapWidthPixels / MapWidthFeet - CropDown;
				output.println(a + " " + b + " " + c + " " + d);
			}
			System.out.println(res);
			System.out.print(String.format("legs = %d, distance = %d feet, time = %s", legs, length, new GraphEdge().formatTime(time)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}