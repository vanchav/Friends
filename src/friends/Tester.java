package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Tester {
	
	public static void main(String args[]) throws FileNotFoundException {
		
	Scanner myscanner = null;
	Graph newGraph = null;
	try {
		File newFile= new File("hello.txt");
		myscanner = new Scanner(newFile);
		newGraph = new Graph(myscanner);
		myscanner.close();
		//System.out.println("Works");
	}
	 catch (FileNotFoundException exception) {
         System.out.println("No such file!");
        
     }
	
	//test shortest
	ArrayList<String> shortest = Friends.shortestChain(newGraph, "sam", "aparna"); 
	if(shortest == null) {
		System.out.println("Cannot be reached");
	} else {
		for(int i = 0; i<shortest.size(); i++) {
			System.out.print(shortest.get(i) + "-->");
		}
		System.out.println(); 
	}
	
	/*//cliques tester
	Scanner sc = new Scanner(System.in); 
	System.out.println("School: ");
	String school = sc.nextLine(); 
	ArrayList<ArrayList<String>> cliques = Friends.cliques(newGraph, school); 
	if(cliques == null || cliques.size() == 0) {
		System.out.println("None found");
	} else {
		System.out.println(cliques); 
	}*/
	
	ArrayList<String> temp = new ArrayList<String>();
	temp = Friends.connectors(newGraph);
	System.out.println(temp.toString());
	}
}