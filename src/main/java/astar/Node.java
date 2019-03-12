package astar;

import java.util.ArrayList;
import java.util.List;

import astar.Request;
import astar.SendCar;

/*
 * Node class used for the A* algorithm containing information about
 * hCost: the heuristic cost
 * gCost: cost in terms of waiting time till pickup
 * finalCost: overall cost of the path up until that node
 * leftRequests: List with all requests that are left to be processed
 * order: associated SendCar order
 * parent: previous node of the path
 */
public class Node {

	int hCost;
	int gCost;
	int finalCost;
	List<Request> leftRequests = new ArrayList<Request>();
	SendCar order;
	Node parent;
	
	public Node(List<Request> requests) {
		this.leftRequests = requests;
	}
}
