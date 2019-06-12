# ASTAR for Elevator Control 

This is a controller for a multi-car elevator controller using A* search. It was done by Vanessa Toborek (vanessatoborek@icloud.com) under the supervisin of Sebastian Sardina during her visit to RMIT University as part of her Master program.


The project consists of the Java implementation of the A* algorithm in order to distribute an arbitrary number of requests to the available elevators in a way that minimizes the overall waiting time of passengers. 

## Compile & Run

The implementation uses Apache Commons Lang utility classes (`org.apache.commons.lang3`). In Ubuntu-type Linux it is provided by package `libcommons-lang3-java`.

To compile:

    $ javac -cp /usr/share/java/commons-lang3.jar src/astar/*.java


To run:

    $ java -cp src/:/usr/share/java/commons-lang3.jar astar.Test


    ###############################
    Floor: 4
    Floor: 3
    Floor: 19
    Floor: 6
    Floor: 8
    
    Elevator #1 in floor 19
    Elevator #2 in floor 25
    ###############################
    Done with search.
    Elevator 1 from 19 to 8 (finalCost: 66)
    Elevator 1 from 8 to 6 (finalCost: 30)
    Elevator 1 from 6 to 4 (finalCost: 26)
    Elevator 2 from 25 to 19 (finalCost: 28)
    Elevator 1 from 4 to 3 (finalCost: 23)
    Path done.





## Implementation Details

## A* Algorithm

The A* algorithm is a famous search algorithm that can be used for graph traversal, finding the best path between nodes. Its main advantage is that it uses a heuristic, that means estimated, cost of finishing its search from the current node additionally to the actual cost of the node. By minimizing the sum of these costs, it can iteratively evaluate each node and figure out the optimal path.

## Code Structure

The Java implementation of the A* algorithm is adapted for the elevator planning problem. It takes an arbitrary number of requests and distributes them optimally to the available elevators. The structure of the implementation consists out of three classes: 

* `SearchNode`
* `AStarSearch`
* `Test`

### SearchNode

The `SearchNode` class implements the different states during the A* algorithm. It contains the following variables:

* `allElevators`: integer array with the positions of all elevators
* `allRequests`: integer array with the positions of the requests
* `parent`: previous node representing the previous state
* `hCost`: heuristic cost of the current search node
* `gCost`: actual cost for the current search node
* `finalCost`: sum of hCost and gCost 
* `currentElevator`: index of the current elevator
* `currentRequest`: index of the current request

The class has two constructors: one `SearchNode(int[] allElevators, int[] allRequests)` for the initialization of the very first node. It sets the overall information about all available elevators and all initially pending requests. The second `SearchNode(SearchNode parent, int currentElevator, int currentRequest)` is used for the initialization of every other node and relies on the informations of the parent node as well as the information about the current elevator and the current processed request.

The function `calcH(int[] allElevators, int[] allRequests)` calculates the heuristic cost of the current state. For that it considers the best possible case in which each pending request can be served by the closest elevator at the same time.

### AStarSearch

The most important variables for the A* algorithm are the following:

* `PriorityQueue<SearchNode> open`: it sorts the different nodes according to their final cost
* `List<SearchNode> closed`: keeps track of already evaluated nodes

The function `Search(int[] allElevators, int[] allRequests)` performs the search on the given input of requests and elevators. It will return the last node to the solution. 

After the search the function `List<SearchNode> makePath(SearchNode lastNode)` will save the solution consisting of all relevant nodes to a list and return it.



### Test

The test class can be used to run the A* search on some exemplary test data. The functions `createElevators(int numElevators, int numFloors)` and `createRequests(int numRequests, int numFloors)` are responsible for creating the test data. By changing the values for the variables `numElevators`, `numFloors`, `numRequests` the test data can be adjusted accordingly.
