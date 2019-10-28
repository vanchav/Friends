package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		ArrayList<String> fin = new ArrayList<String>(); 
		Queue<Person> q = new Queue<Person>();
		Stack<String> path = new Stack<String>(); 
		
		int size = g.members.length; 
		boolean found = false; 
		int count = 0; 
		
		boolean[] seen = new boolean[size]; 
		int[] prev = new int[size]; 
		
		int currpos = getPersonInt(g, p1); //position of current person in the array
		if(currpos == -1 || getPersonInt(g, p2) == -1){
			return null; 
		}
		
		int str = currpos; //starting position
		Person curr = g.members[currpos]; //current person
		Friend nbr; //current friend
		int pos; //position of current friend
		int h; 
		
		Person hold; //holds the dequeued person
		
		seen[currpos] = true; 
		if(curr.first != null) {
			prev[curr.first.fnum] = currpos; 
		}else {
			return null; 
		}
		
		while(count<size) {	
			nbr = curr.first; 
			
			while(nbr != null) {
				pos = nbr.fnum;
				if(!(seen[pos])) {
					q.enqueue(g.members[pos]); //enqueue the friend 
					seen[pos] = true; 
					prev[pos] = currpos; 
				}
				nbr = nbr.next;
			}
			
			if(!(q.isEmpty())) {
				hold = q.dequeue();
			} else {
				break; 
			}
			
			//System.out.println(hold.name);
			//found the end
			if(hold.name.equals(p2)) {
				found = true;
				break; 
			} 
			curr = hold; 
			currpos = getPersonInt(g, hold.name); 
			count++; 
		}
		pos = getPersonInt(g, p2);
		
		if(found) {
			while(pos!=str) { 
				//System.out.println(g.members[pos].name);
				path.push(g.members[pos].name); 
				h = prev[pos];
				pos = h; 
			}
			
			path.push(p1);
			while(!(path.isEmpty())) {
				fin.add(path.pop()); 
			}
		}else {
			fin = null; 
		}
		
		return fin;
	}
	
	private static int getPersonInt(Graph g, String name){
		try{ 
			return g.map.get(name); 
		}catch(Exception NullPointerException){
			return -1;
		}
	}
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		ArrayList<ArrayList<String>> fin = new ArrayList<ArrayList<String>>();
		Queue<Person> queue = new Queue<Person>(); 
		
		boolean[] seen = new boolean[g.members.length]; 
		ArrayList<String> sq = new ArrayList<String>(); //one squad at a time
		ArrayList<String> hold = new ArrayList<String>();
		school = school.toLowerCase();
		
		for(int i =0; i<seen.length; i++) {
			seen[i] = false; 
		}
		
		for(int v = 0; v<seen.length; v++) {
			if(!seen[v]) {
				hold = squad(sq, g, school, v, seen, queue); 
				if(hold.size()>0) {
					fin.add(hold); 
				}
			} 
		}
		
		return fin; 
		
	}
	
	private static ArrayList<String> squad(ArrayList<String> squad, Graph g, String school, int start, boolean[] seen, Queue<Person> queue) {
		Person currperson; 
		int curpos = start; 
		int pos;
		squad = new ArrayList<String>(); 
		
		if(!(g.members[start].school == null)){
			if(g.members[start].school.toLowerCase().equals(school)) {
				queue.enqueue(g.members[start]);
				squad.add(g.members[start].name); 
			}
		}
		seen[start] = true; 
		
		while(!queue.isEmpty()) {
			currperson = queue.dequeue(); 
			curpos = getPersonInt(g,currperson.name); 
			for(Friend nbr = g.members[curpos].first; nbr !=null; nbr = nbr.next) {
				pos = nbr.fnum; 
				if(!seen[pos]) {
					if(!(g.members[pos].school == null)){
						if(g.members[pos].school.toLowerCase().equals(school)) {
							queue.enqueue(g.members[pos]);
							squad.add(g.members[pos].name); 
						}
					}
					seen[pos] = true; 
				}
			}
		
		}
		
		return squad; 
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		Friend ptr = null; 
		Friend prev = null;
		
		String keep; 
		int start;
		int count = 0; 
		int b = 0; 
		int size = g.members.length; 
		
		ArrayList<Integer> hold = new ArrayList<Integer>(); 
		ArrayList<String> finalList = new ArrayList<String>(); 
		
		while(count<g.members.length) {
			hold = new ArrayList<Integer>();
			ptr = g.members[count].first; 
			
			if(ptr.next == null) {
				count++;  
			} 
			
			if(count<size) {
				ptr = g.members[count].first; 
				prev = g.members[count].first;
				
				//make a list of all the friends
				while(ptr !=null) {
					hold.add(ptr.fnum); 
					ptr = ptr.next; 
				}//end while
					
				g.members[count].first = null; 
					 
		uno: for(int a = 0; a<hold.size(); a++) {
				while(b<hold.size()) {
						if(g.members[hold.get(a)].name.equals(g.members[hold.get(b)].name)) {
							break; 
						}
						//keep = shortestChain(g, g.members[hold.get(x)].name, g.members[hold.get(y)].name); 
						if(shortestChain(g, g.members[hold.get(a)].name, g.members[hold.get(b)].name) == null) {
							finalList.add(g.members[count].name); 
							break uno; 
						}
						
						b++; 
					}
				}//end outer for
				
					g.members[count].first = prev;
			}
			count++; 
		}
		
		return finalList; 
	}
}

