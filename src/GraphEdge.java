/**
 * A graph edge.
 * Supports finding its reversed edge, print itself, and compute traveling time on this edge.
 * @author difan
 *
 */
public class GraphEdge {
	// # label1 label2 v1 v2 length, angle, direction (C) name
	int id;
	String label1;
	String label2;
	int v1;
	int v2;
	int length;
	int angel;
	String direction;
	String condition;
	String name;

	GraphNode start;
	GraphNode end;
	double time;
	String verb;
	boolean skateboard;
	
	public GraphEdge() {
		this(-1, null, null, -1, -1, "", "", "");
	}

	public GraphEdge(int id, GraphNode start, GraphNode end, int length, int angel, String direction, String condition, String name) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.length = length;
		this.direction = direction;
		this.condition = condition;
		this.name = name;
		this.angel = angel;
		this.label1 = start == null ? "" : start.label;
		this.label2 = end == null ? "" : end.label;
		this.v1 = start == null ? -1 : start.id;
		this.v2 = end == null ? -1 : end.id;
	}

	public String toString() {
		return String.format("%d %s %s %d %d %d %d %s %s %s", id, label1, label2, v1, v2, length, angel, direction, condition, name);
	}
	
	public String printEdge(boolean skateboard) {
		getTime(skateboard);
		String res = "From: " + start.printNode() + "\n" + 
				 (name.equals("") ? "" : "On: " + name + "\n") + 
				 verb + " " + length + " feet in direction " + angel + " degrees " + direction + ".\n" +
				 "To: " + end.printNode() + " \n" + 
				 "(" + (!condition.equals("x") && skateboard && condition.equals(condition.toLowerCase()) ? "no skateboards allowed, " : "") + formatTime() + ")";
		return res;
	}

	/* Condition & Speed -
	// f/F = flat, u/U = up, d/D = down,
	// s = steps up, t = steps down, b = bridge
	// (lower case is no skateboards, upper case is skateboards allowed),
	// or x = default that can be translated at run time (e.g., set to F).

	// WalkFactorU = 0.9 - Multiply WalkSpeed by this for walk up.
	// WalkFactorD = 1.1 - Multiply WalkSpeed by this for walk down.
	// SkateFactorU = 1.1 - Multiply WalkSpeed by this for skateboard up.
	// SkateFactorF = 2.0 - Multiply WalkSpeed by this for skateboard flat.
	// SkateFactorD = 5.0 - Multiply WalkSpeed by this for skateboard down.
	// StepFactorU = 0.5 - Multiply WalkSpeed by this for walk up steps.
	// StepFactorD = 0.9 - Multiply WalkSpeed by this for walk down steps.
	// BridgeFactor = 1.0 - Multiply WalkSpeed by this for walking on a bridge. */
	public double getTime(boolean skateboard) {
		this.skateboard = skateboard;
		double speed = 272.0 / 60.0;
		verb = skateboard ? "Glide" : "Walk";

		if (condition.equals("F") || condition.equals("x")) {
			if (skateboard) {
				speed = speed * 2.0;
				verb = "Glide";
			}
		} else if (condition.equals("f")) {
			speed = speed * 1.0;
			verb = "Walk";
		} else if (condition.equals("U")) {
			if (!skateboard) {
				speed = speed * 0.9;
				verb = "Walk up";
			} else {
				speed = speed * 1.1;
				verb = "Glide up";
			}
		} else if (condition.equals("u")) {
			speed = speed * 0.9;
			verb = "Walk up";
		} else if (condition.equals("D")) {
			if (!skateboard) {
				speed = speed * 1.1;
				verb = "Walk down";
			} else {
				speed = speed * 5.0;
				verb = "Coast down";
			}
		} else if (condition.equals("d")) {
			speed = speed * 1.1;
			verb = "Walk down";
		} else if (condition.equals("s")) {
			speed = speed * 0.5;
			verb = "Go up";
		} else if (condition.equals("t")) {
			speed = speed * 0.9;
			verb = "Go down";
		} else if (condition.equals("b")) {
			speed = speed * 1.0;
			verb = "Walk";
		}
		time = length / speed;
		return time;
	}
	
	public String formatTime() {
		return formatTime(time);
	}

	public String formatTime(double t) {
		String minute = (t / 60.0 >= 1.0) ? ((int) ((t / 60.0 + 0.05)* 10) / 10.0) + (((t / 60.0 > 1) ? " minutes" : " minute")) : "";
		if (minute != "") {
			return minute;
		}
		String seconds = (int) (t % 60 + 0.5) + ((t % 60 > 1) ? " seconds" : " second");
		return seconds;
		//return minute + (minute != "" ? " " : "") + seconds;
	}
	
	public GraphEdge getReverse() {
		Graph g = start.g;
		// normally, reversed edge is stored adjacent to current edge
		if (id > 0) {
			GraphEdge e = g.edges[id - 1];
			if (e.start == end && e.end == start) {
				return e;
			}
		}
		if (id < g.edges.length - 1) {
			GraphEdge e = g.edges[id + 1];
			if (e.start == end && e.end == start) {
				return e;
			}
		}
		// if adjacent edge fails (for example, edges may be sorted not by Ids)
		// Scan through all edges
		for (int i = 0; i < g.edges.length; i++ ) {
			GraphEdge e = g.edges[i];
			if (e.start == end && e.end == start) {
				return e;
			}
		}
		// If linear scan fails, forcefully return a reversed edge
		// since for pedestrian, no reason cannot walk in opposite direction on campus
		return cloneReversedEdge();
	}

	public String printEdgeReverse(boolean skateboard) {
		return getReverse().printEdge(skateboard);
	}

	public GraphEdge cloneReversedEdge() {
		// reverse angel
		int angel0 = (angel + 180) % 360;
		// reverse direction
		String direction0 = direction;
		if (direction.equals("East")) {
			direction0 = "West";
		} else if (direction.equals("West")) {
			direction0 = "East";
		} else if (direction.equals("South")) {
			direction0 = "North";
		} else if (direction.equals("North")) {
			direction0 = "South";
		} else if (direction.equals("NW")) {
			direction0 = "SE";
		} else if (direction.equals("SE")) {
			direction0 = "NW";
		} else if (direction.equals("NE")) {
			direction0 = "SW";
		} else if (direction.equals("SW")) {
			direction0 = "NE";
		}
		// reverse condition
		String condition0 = condition;
		if (condition.equals("U")) {
			condition0 = "D";
		} else if (condition.equals("u")) {
			condition0 = "d";
		} else if (condition.equals("D")) {
			condition0 = "U";
		} else if (condition.equals("d")) {
			condition0 = "u";
		} else if (condition.equals("s")) {
			condition0 = "t";
		} else if (condition.equals("t")) {
			condition0 = "s";
		}
		GraphEdge res = new GraphEdge(-1, end, start, length, angel0, direction0, condition0, name);
		res.getTime(skateboard);
		return res;
	}
}