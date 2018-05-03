package bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class bfs {
	
	static LinkedList<Integer> G[];
	static int marked[];
	static int L[][];
	static StringBuffer P[][];
	
	
	public static void genGraph(int vertices, int edges){
		int totalVertices = vertices;
		int totalEdges = edges;
		int v1, v2;
		
		G = new LinkedList[totalVertices];
		
		for (int i=0;i<totalVertices;i++)
			G[i] = new LinkedList<Integer>();
		
		
		//generate random ajacent list
		Random r = new Random();
		while(totalEdges > 0){
			v1 = r.nextInt(totalVertices);
			v2 = r.nextInt(totalVertices);					
			if(v2 != v1 && G[v1].indexOf(v2) == -1 && G[v2].indexOf(v1) == -1){
				G[v1].add(v2);
				G[v2].add(v1);			 
				totalEdges--;
			}
		}
		
		
		//print adjacent list
		for(int i=0; i<vertices; i++)
			System.out.println(i+":"+G[i].toString());
	
	}
		
	public static void BFS(LinkedList<Integer> G[], Integer s) {
		Queue<Integer> Q = new LinkedList();
		
		//init values
		for(int i=0; i<G.length; i++){
			L[s][i] = -1;//suppose to be infinite
			P[s][i] = new StringBuffer();
		}
		L[s][s] = 0;
		P[s][s].append(s);
		
		
		//start doing bfs
		Q.add(s);
		int v;
		while (!Q.isEmpty()) {
			v = Q.poll(); 
			for(Integer j : G[v]){
				if(L[s][j] == -1){	//if vertex is unmarked
					L[s][j] = L[s][v] +1;  // add weight and mark vertex
					P[s][j].setLength(0);
					P[s][j].append(j + "->" + P[s][v]); //add Predecessors
					Q.add(j); // add neighbours to queue	
				}//end of if				
			}// end of for
		}//end of while
	}//end of bfs
	
	
	public static void testBFS(int v, int e){
		
		int vertices = v; //change this
		int edges = e; // change this
		
		double startTime;
		double CPUTime;
		
		L = new int[vertices][vertices];
		P = new StringBuffer[vertices][vertices];
	
		genGraph(vertices, edges); //adjacency list
		
		
		startTime = System.nanoTime();
		for(int i=0; i<vertices; i++)
			BFS(G, i);
		CPUTime = (System.nanoTime() - startTime) / 1000000.0;
		
		System.out.println("CPU Time: " + CPUTime);
		
	}
	
	public static void main(String[] args) {
		
		//testBFS(10,10);
		
		System.out.println("Set 1");
		testBFS(5000,1000);
		//testBFS(5000,5000);
		//testBFS(5000,10000);
		//testBFS(5000,50000);
		//testBFS(5000,100000);
		
		System.out.println();
		
		//System.out.println("Set 2");
		//testBFS(10000,1000);
		//testBFS(10000,5000);
		//testBFS(10000,10000);
		//testBFS(10000,50000);
		//testBFS(10000,100000);
		int d,s;
		
		while(true){
			
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Source(int) -1 to exit: ");
		d = sc.nextInt();
		if(d==-1)break;
		
		System.out.print("Enter Destination(int) -1 to exit: ");
		s = sc.nextInt();
		if(s==-1)break;
		
		System.out.println("Number of stops: "+L[s][d]);
		System.out.println("Predecessors: "+P[s][d]);
		}
		
		System.out.println("Exiting program.. ");
		
		
		
	}


}
