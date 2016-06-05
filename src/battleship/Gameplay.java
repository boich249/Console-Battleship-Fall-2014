package battleship;

import java.util.Scanner;

public class Gameplay {
	
	
	public static void main (String[] args){
		Scanner userIn = new Scanner(System.in);
		final int NB_PLAYERS = 2;
		Player[] players = new Player[NB_PLAYERS];
		System.out.println("Enter your name:");
		players[0] = new Human(userIn.nextLine());

		players[1] = new Comp();
		
		do
		{
			players[0].turn(players[1]);
			if(players[1].isLoser())
				break;
			players[1].turn(players[0]);
			if(players[0].isLoser())
				break;
				
			
			
		}while (true);
		System.out.println("Game over!!");
		System.out.println(players[1].toStringAlt() + "\n" + players[0]);
		if(players[1].isLoser())
			System.out.println(players[0].getName() + " is the winner!!");
		else
			System.out.print("Comp is the winner!!");
			
	}
	

}
