import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Main {
	
	
	private static int numSticks = 10;
	private static int[][] knowledge = new int[3][10];
	private static int[] moves = new int[10];
	private static boolean game = true;
	private static int turn;
	private static boolean again = true;
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Welcome to the game of NIM.\n"
				+ "I guess you will be my opponent.\n"
				+ "Very well, let's begin.");
		File test = new File("C:/statsTxt.txt");
		if(test.exists()){
			Scanner fileReader = new Scanner(test);
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 10; j++)
					knowledge[i][j] = fileReader.nextInt();
			fileReader.close();
		}
		else{
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 10; j++)
					knowledge[i][j] = 0;
		
			knowledge[0][2] = 1;
			knowledge[1][1] = 1;
			knowledge[2][0] = 1;
		}
		for(int i = 0; i < 10; i++)
			moves[i] = 0;
		
		Scanner input = new Scanner(System.in);
		while(again){
			numSticks = 10;
			while(game){
		
			turn = 1;
			System.out.println("My turn.");
			int computerTake = determineMove();
			takeStick(computerTake);
			System.out.println("I took " + computerTake + " stick(s).");
			game = gameOver();
			System.out.println("There are " + numSticks + " remaining.\n"
					+ "Your turn.");
			if(game == false)
				break;
			turn = 2;
			int playerTake = input.nextInt();
			while(playerTake > 3 || playerTake < 0){
				System.out.println("That is not a valid input.\n"
						+ "Choose either 1, 2, 3.");
				playerTake = input.nextInt();
			}
			while(playerTake > numSticks){
				System.out.println("You cannot take that many sticks.\n"
						+ "You must choose to take no more than " + numSticks + ".\n"
						+ "Please enter a new number of sticks to take.");
				playerTake = input.nextInt();
			}
				
			takeStick(playerTake);
			System.out.println("You took " + playerTake + " stick(s).");
			game = gameOver();
			System.out.println("There are " + numSticks + " remaining.");
			}
			
			if(turn ==1)
				System.out.println("Looks like man is obsolete. Activating Skynet");
			else
				System.out.println("You may have one this time, but I have new data now.\n"
						+ "Next time will not be so easy.");
			adjustKnowledge();
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 10; j++){
					System.out.print(knowledge[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println("Would you like to play again? yes/no");
			String answer = input.next().toLowerCase();
			if(answer.equals("no"))
				again = false;
			game = true;
			
		}
		
		input.close();

	}
	
	public static void takeStick(int numTake){
		numSticks -= numTake;
	}
	
	public static boolean gameOver(){
		if(numSticks <= 0)
			return false;
		return true;
	}
	
	public static int determineMove(){
		int move = 1;
		moves[numSticks - 1] = 1;
		if(knowledge[1][numSticks - 1] > knowledge[2][numSticks - 1] ){
			move = 2;
			moves[numSticks - 1] = 2;
		}
		else if(knowledge[0][numSticks - 1] > knowledge[2][numSticks - 1] 
				&& knowledge[0][numSticks - 1] > knowledge[1][numSticks - 1] ){
			move = 3;
			moves[numSticks - 1] = 3;
		}
		return move;
	}
	
	public static void adjustKnowledge(){
		System.out.println("Storing game data for future use.");
		if(turn == 1){
			for(int i = 0; i < 10; i ++){
				if(moves[i] != 0){
					knowledge[3 - moves[i]][i]++;
					moves[i] = 0;
				}
			}
		}
		else{
			for(int i = 0; i < 10; i ++){
				if(moves[i] != 0){
					knowledge[3 - moves[i]][i]--;
					moves[i] = 0;
				}
			}
		}
		
		try {
            //Whatever the file path is.
            File statText = new File("C:/statsTxt.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            for(int i = 0; i < 3; i++){
    			for(int j = 0; j < 10; j++){
    				w.write(knowledge[i][j] + " ");
    			}
    			w.write("\n");
            }
    		
            
            w.close();
        } 
		catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }
		

}
