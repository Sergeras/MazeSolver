
import java.util.ArrayList;

public class MazeSolver {
	private boolean [][] maze; // maze 
	private int startX, startY; // starting position
	private int endX, endY; // ending position
	private char[][] solution; // solved maze 
	private boolean [][] wasntHere; // serves for checking that the position wasnt visited before
	private boolean isSolvable = true; // check to find out if maze can be solved
	private ArrayList<Point> tracker = new ArrayList<>(); // keeps the correct path

	// transform the maze of characters to boolean array for simplier resolution and setting all matrixes
	private MazeSolver(char [][] maze) {
		this.maze = new boolean [maze.length][maze[0].length];
		this.wasntHere =  new boolean [maze.length][maze[0].length];
		this.solution = new char [maze.length][maze[0].length];
		for (int y = 0 ; y < maze.length  ; y++ ) {
			for ( int x = 0 ; x < maze[y].length  ; x++ ) {
				this.wasntHere[y][x] = (maze[y][x] == ' ')?true:false;
				this.maze [y][x] = (maze[y][x] == ' ')?true:false;
			}
		}
	}
	//chained constructor for x and y start positions
	private MazeSolver(char [][] maze, int startX, int startY) {
		this(maze);
		this.startX = startX;
		this.startY = startY;
		this.wasntHere[startY][startX] = false;
	}
	//chained constructor for x and y end positions
	public MazeSolver(char [][] maze, int startX, int startY, int endX, int endY) {
		this(maze, startX,startY);
		this.endX = endX;
		this.endY = endY;
	}
	//the logic for solving the maze, all the movement
	public char[][] solve (){
		int xPos = startX;	//xPos to track the movement
		int yPos = startY;	//yPos to track the movement
		int checkForSolvable = 0; //to keep count if there is solution, 4 for no solution as it try out all sides
		while (isSolvable) {
			//down
			if (yPos < maze.length-1 ) {	//bottom movement and check
				if (isDownAvailable(yPos, xPos)) {	//check for availability in given direction
					if (wasntHere[yPos+1][xPos]) {	//check that it wasnt visited before for wrong direction
						wasntHere[yPos+1][xPos]= false;
						tracker.add(new Point(yPos, xPos));
						yPos++;
						isItEnd(yPos, xPos);
					}
				}
			} else { //wrapping movement
				if (isDownAvailable(0, xPos)) {	
					if (wasntHere[0][xPos]) {	
						wasntHere[0][xPos]= false;
						tracker.add(new Point(maze.length-1, xPos));
						yPos= 0;
						isItEnd(yPos, xPos);
					}
				}
			}
			//right
			if (xPos < maze[0].length-1) {
				if (isRightAvailable(yPos, xPos)) {	//check for availability in given direction
					if (wasntHere[yPos][xPos+1]) {	//check that it wasnt visited before for wrong direction
						wasntHere[yPos][xPos+1]= false;
						tracker.add(new Point(yPos, xPos));
						xPos++;
						isItEnd(yPos, xPos);
					}
				}
			} else {	// wrapping movement  
				if (isRightAvailable(yPos, 0)){
					if (wasntHere[yPos][0]) {
						wasntHere[yPos][0] = false;
						tracker.add(new Point(yPos, maze.length-1));
						xPos= 0;
						isItEnd(yPos, xPos);
					}
					
				}
			}
			// top
			if (yPos > 0) {
				if (isUpAvailable(yPos, xPos)) {	//check for availability in given direction
					if (wasntHere[yPos-1][xPos]) {	//check that it wasnt visited before for wrong direction
						wasntHere[yPos-1][xPos]= false;
						tracker.add(new Point(yPos, xPos));
						yPos--;
						isItEnd(yPos, xPos);
					}
				}
			} else {    // wrapping movement             
				if (isUpAvailable(maze.length, xPos)) {
					if (wasntHere[maze.length-1][xPos]) {
						wasntHere[maze.length-1][xPos] = false;
						tracker.add(new Point(0, xPos));
						yPos = maze.length-1;
						isItEnd(yPos, xPos);
					}
				}
				
			}
			//left
			if (xPos >0 ) {
				if (isLeftAvailable(yPos, xPos)) {	//check for availability in given direction
					if (wasntHere[yPos][xPos-1]) {	//check that it wasnt visited before for wrong direction
						wasntHere[yPos][xPos-1]= false;
						tracker.add(new Point(yPos, xPos));
						xPos--;
						isItEnd(yPos, xPos);
					}
				}
			} else { // wrapping movement                  
				if (isLeftAvailable(yPos, maze[0].length)) {
					if (wasntHere[yPos][maze[0].length-1]) {
						wasntHere[yPos][maze[0].length-1] = false;
						tracker.add(new Point(yPos, 0));
						xPos = maze[0].length-1;
						isItEnd(yPos, xPos);
					}
				}
			}
			
			/* check if it can move to different position, if no position is available it
			 * it goes back and delete the previous postion, marking the incorect path that
			 * way
			 */
			if (!isUpAvailable(yPos, xPos) && !isDownAvailable(yPos, xPos)
				&& !isLeftAvailable(yPos, xPos) && !isRightAvailable(yPos, xPos)) {
				if (tracker.size() > 1) {
					tracker.remove(tracker.size()-1);
					yPos = tracker.get(tracker.size()-1).getY();
					xPos = tracker.get(tracker.size()-1).getX();
				}
				/*
				 * checking if maze is solvable, once it returns into start position
				 * and there is no other movement available it will end searching for solution.
				 * Ends on 4 as thats 4 checks 1 per each side 
				 */
				if (tracker.size() == 1) { 
					yPos = tracker.get(tracker.size()-1).getY();
					xPos = tracker.get(tracker.size()-1).getX();
					checkForSolvable++;
					if (checkForSolvable >= 4) {
						System.out.println("maze cannot be solved");
						isSolvable=false;
					}
				}
			}
		}
		return solution;
	}
	// check if the space is available and checking for wrapping
	private boolean isUpAvailable(int y, int x) {
		if(y <=  0) {
			y = maze.length;
		}
		return wasntHere[y-1][x];
	}
	// check if the space is available and checking for wrapping
	private boolean isDownAvailable(int y, int x) {
		if (y >= maze.length-1) {
			y = -1;
		}
		return wasntHere[y+1][x];
	}
	// check if the space is available and checking for wrapping
	private boolean isRightAvailable(int y, int x) {
		if (x >= maze[0].length -1 ) {
			x = -1;
		}
		return wasntHere[y][x+1];
	}
	// check if the space is available and checking for wrapping
	private boolean isLeftAvailable(int y, int x) {
		if (x <= 0) {
			x = 1;
		}
		return wasntHere[y][x-1];
	}
	// checks if current position is the end. If so mark the correct path into the solution matrix
	private void isItEnd(int y, int x) {
		if(y == endY && x ==endX) {
			for (int i = 0; i < tracker.size(); i++) {
				solution[tracker.get(i).getY()][tracker.get(i).getX()] = 'X';
			}
			isSolvable = false;
		}
		
	}
}
