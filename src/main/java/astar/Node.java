package astar;

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
	SendCar order;
	Node parent;
	
	Node() {
	}
	
	public String print() {
		return this.order.print();
	}
}
