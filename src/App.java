

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
	public static void main (String[] args) {
		
		//setting the location and the filename of maze input
		Scanner sc = new Scanner(System.in);
		System.out.print("Please write location of the file: ");
		String path = sc.nextLine();	
		System.out.print("Please write name of the file: ");
		String fileName = sc.nextLine();
		File file = new File(path + "\\" + fileName);
		sc.close();
		
		//loading the file with input
		try {
			// create new scanner and paste the maze from file into the matrix
			Scanner s = new Scanner(file);
			int width = s.nextInt();
			int height = s.nextInt();
			char [][] maze = new char[height][width];
			int startX = s.nextInt();
			int startY = s.nextInt();
			int endX = s.nextInt();
			int endY = s.nextInt();
			// read the maze from file and save it into maze
			for (int y = 0 ; y < maze.length ; y++ ) {
				for ( int x = 0 ; x < maze[0].length ; x++ ) {
					maze [y][x] = (s.nextInt() == 0)?' ':'#';
				}
			}
			s.close();
			
			MazeSolver mazeSolver = new MazeSolver(maze, startX, startY, endX, endY);
			
			//solve the maze and save the position of the path into the maze
			for (int y = 0; y <maze.length; y++) {
				for (int x = 0 ; x <maze[0].length; x++) {
					maze[y][x] = (mazeSolver.solve()[y][x] == 'X'? 'X': maze[y][x]);
				}
			}
			
			maze[startY][startX] = 'S';	//marks the start position
			maze[endY][endX] = 'E';	//marks the end position
			
			//prints out the array
			for (char[] arr: maze) {
				for (char c: arr) {
					System.out.print(c);
				}
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Incorect path name or file name.");
		}
	}
}

