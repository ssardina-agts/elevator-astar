package astar;

import astar.SendingOrder;

/*
 * Node class used for the A* algorithm containing information about
 * hCost: the heuristic cost
 * gCost: cost in terms of waiting time till pickup
 * finalCost: overall cost of the path up until that node
 * leftRequests: List with all requests that are left to be processed
 * order: associated SendCar order
 * parent: previous node of the path
 */
public class Node implements Comparable<Node>{

	int hCost;
	int gCost;
	int finalCost;
	SendingOrder order;
	Node parent;
	
	Node(Node parent, Request request, Elevator current, int gCost, int hCost) {
		this.gCost = gCost;
		this.hCost = hCost;
		this.finalCost = this.gCost + this.hCost;
		this.order = new SendingOrder(current.number, current.floor, request.floor);
		this.parent = parent;
	}
	
	public String print() {
		return this.order.print();
	}
	
	@Override
	public int compareTo(Node node) {
		if (this.finalCost > node.finalCost) {
			return 1; 
		} else if (this.finalCost < node.finalCost) {
			return -1; 
		} else {
			return 0;
		}
	}
	
}
