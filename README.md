# Brandeis-Map-Graph-Campus-Tour
This program is a programming assignment from algorithm class. It Implements a walking map utility for the Brandeis campus. The program will ask for a begin location, a finish location, whether using a skateboard, and whether to minimize distance or time. 


## Files
```
Map/
    MapDataEdges.txt
    MapDataVertices.txt
    SampleOutputBoarding/
    SampleOutputWalking/
    MatlabScriptsToDisplayAPath/
        Display.m
        DisplayCropped.m
        DisplayWithArrows.m
        DisplayWithArrowsCropped.m
        RouteCropped.txt
        BrandeisMapLabeledCropped.jpg
    src/
        BinaryMinHeap.java
        Entry.java
        FindCampusTour_Kruskal.java
        FindCampusTour_Prim.java
        FindCampusTour_Prim_Dijkstra.java
        FindMinPath_Dijkstra.java
        Graph.java
        GraphEdge.java
        GraphNode.java
        IndexHashMap.java
        IndexMap.java
        Map.java
        MinPriorityQueue.java
        QuickSort.java
        SinglyLinkedList.java
        SinglyLinkedListNode.java
        UnionFind.java
```

- ```Construct graph```
MapDataEdges.txt and MapDataVertices.txt store the campus map vertices and edges, and are used to constuct the Brandeis campus map graph.
- ```Data structures```
Data structures implemented include DynamicArray, LinkedList, BinaryMinHeap, PriorityQueue, HashTable, HashMap, Graph (adjacency list), and UnionFind (impletemented using map instead of array).
- ```Algorithms```
Algorithms implemented include QuickSort (sort either on edge length or traveling time), UnionFind, Dijkstra's algorithm, Kruskal's algorithm, Prim's algorithm, and a shortest campus tour algorithrm improves on Minimum-Spanning-Tree generated by Kruskal's and Prim's. However, the Hamilton-Tour is a NP-Complete problem, the shortest campus tour algorithm is an approximation to it.
- ```MATLAB visualization```
Includes files in the \MatlabScriptsToDisplayAPath folder. The main file is Display.m.
- ```Tests```
No test files are uploaded. All data structures and algorithms were tested on my machine before uploading.

## Compile and Run files
Main function is in Map.java. Run the Map.java file, interact with console, and follow instructions to generate campus tour.

## Visualize campus tour
The Map.java will generate a RouteCropped.txt file in MatlabScriptsToDisplayAPath folder. Run Display.m in MatlabScriptsToDisplayAPath folder to visualize the campus tour. Display.m will read coordinates in RouteCropped.txt and draw lines on BrandeisMapLabeledCropped.jpg.

## Sample output and visualization
```
************* WELCOME TO THE BRANDEIS MAP*************
Enter start (return to quit): castle
Enter finish (or return to do a tour): gordon field
Have a skateboard (y/n - default=n)? 
Minimize time (y/n - default=n)? 

From: (U40) Usen Castle
On: Usen Driveway
Walk 126 feet in direction 273 degrees West.
To: (7) Usen Main Entrance 
(28 seconds)

From: (7) Usen Main Entrance
On: Usen Bypass
Walk 130 feet in direction 157 degrees SE.
To: (S) Top Of Usen Steps 
(29 seconds)

From: (S) Top Of Usen Steps
On: Usen Bypass
Walk down 297 feet in direction 218 degrees SW.
To: (V) Loop Road At The Top Of Squire Bridge Steps 
(60 seconds)

From: (V) Loop Road At The Top Of Squire Bridge Steps
On: Squire Bridge Steps
Go down 132 feet in direction 164 degrees South.
To: (A1n) Squire Bridge North Entrance 
(32 seconds)

From: (A1n) Squire Bridge North Entrance
On: Squire Bridge
Walk 280 feet in direction 132 degrees SE.
To: (A1s) Squire Bridge South Entrance 
(1.0 minute)

From: (A1s) Squire Bridge South Entrance
Walk 90 feet in direction 197 degrees South.
To: (Y) Athletics Front Entrance 
(20 seconds)

From: (Y) Athletics Front Entrance
On: Marcus Field
Walk 514 feet in direction 218 degrees SW.
To: (A7) Marcus Field 
(1.9 minutes)

From: (A7) Marcus Field
On: Marcus Field
Walk 418 feet in direction 266 degrees West.
To: (^) Gordon Bleachers 
(1.5 minutes)

From: (^) Gordon Bleachers
On: Gordon Field
Walk 267 feet in direction 259 degrees West.
To: (A8) Gordon Field 
(59 seconds)

legs = 9, distance = 2254 feet, time = 8.2 minutes
```
![alt text](https://github.com/LArchCS/Brandeis-Campus-Tour-Graph-Algorithms/blob/master/SampleOutputWalking/SampleOutputWalkMap.jpg?raw=true)

More sample outputs are in MatlabScriptsToDisplayAPath folder.
