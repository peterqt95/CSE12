/** 
 * Class RockPaperScissors.  Plays repeated games of Rock Paper Scissors with a user 
 * A computer determine's its moves based on the player's history (see systemMoves).
 * 
 * Name: Peter Tran
 * ID: A11163016
 * LOGIN: cs12sho
 *
 * */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {

	/**
	 * Computer chooses move depending on the following conditions: 1. If player
	 * enters in the same move within the past 3 moves, plays counter 2. Chooses
	 * the highest win/tie rate the player has on a certain move and returns
	 * counter
	 * 
	 * @param user
	 * @param history
	 * @param turns
	 * @return RPS counter move
	 */
	public static String systemMove(String[] user, ArrayList<String> history,
			int turns) {

		int paperWins = 0;
		int rockWins = 0;
		int scissorWins = 0;
		int paperTies = 0;
		int rockTies = 0;
		int scissorTies = 0;

		// default moves to rock because rock is the best
		if (turns == 0) {
			return "rock";
		}

		// if occurance of same move twice within the last 3 turns, return that
		// counter to that move
		if (turns >= 3
				&& (user[turns - 1].equals(user[turns - 2]) || user[turns - 1]
						.equals(user[turns - 3]))) {
			if (user[turns - 1].equals("paper")) {
				return "scissors";
			} else if (user[turns - 1].equals("rock")) {
				return "paper";
			} else if (user[turns - 1].equals("scissors")) {
				return "rock";
			}
		}

		// determines the number of wins/ties the user has on each move
		for (int i = 0; i < turns; i++) {
			if (user[i].equals("paper")) {
				if (history.get(i).equals("user")) {
					paperWins++;
				} else if (history.get(i).equals("tie")) {
					paperTies++;
				}
			} else if (user[i].equals("rock")) {
				if (history.get(i).equals("user")) {
					rockWins++;
				} else if (history.get(i).equals("tie")) {
					rockTies++;
				}
			} else if (user[i].equals("scissors")) {
				if (history.get(i).equals("user")) {
					scissorWins++;
				} else if (history.get(i).equals("tie")) {
					scissorTies++;
				}
			}

		}

		// returns the counter to the user's overall highest win/tie rate move
		if ((paperWins + paperTies) > (rockWins + rockTies)
				&& (paperWins + paperTies) > (scissorWins + scissorTies)) {
			return "scissors";
		} else if ((rockWins + rockTies) > (paperWins + paperTies)
				&& (rockWins + rockTies) > (scissorWins + scissorTies)) {
			return "paper";
		} else {
			return "rock";
		}

	}

	/**
	 * Generates the win, lose and tie rate of the last 10 matches played
	 * 
	 * @param user
	 *            player move history
	 * @param system
	 *            computer move history
	 * @param result
	 *            win/lose/tie history
	 * @param turns
	 *            the current number of matches played
	 */
	public static void generateStats(String[] user, LinkedList<String> system,
			ArrayList<String> result, int turns) {

		double userWins = 0;
		double systemWins = 0;
		double ties = 0;
		int userPercent = 0;
		int systemPercent = 0;
		int tiePercent = 0;

		System.out.println("Our most recent games (in reverse order) were: ");

		// prints and calculates match history if more than 10 games played
		if (turns >= 10) {
			for (int i = 0; i < 10; i++) {
				System.out.printf("Me: %-13sYou: %s\n",
						system.get(turns - 1 - i), user[turns - 1 - i]);
			}

			System.out.println("Our overall stats are:");
			for (int i = 0; i < 10; i++) {
				if (result.get(turns - 1 - i).equals("user")) {
					userWins++;
				} else if (result.get(turns - 1 - i).equals("system")) {
					systemWins++;
				} else {
					ties++;
				}
			}

			userPercent = (int) ((userWins / 10) * 100);
			systemPercent = (int) ((systemWins / 10) * 100);
			tiePercent = (int) ((ties / 10) * 100);
			System.out.println("I won: " + systemPercent + "%   You won: "
					+ userPercent + "%   We tied: " + tiePercent + "%");
		}
		// prints and calculates match history for less than 10 games played
		else if (turns > 0 && turns < 10) {
			for (int i = turns - 1; i >= 0; i--) {
				System.out.printf("Me: %-13sYou: %s\n", system.get(i), user[i]);
			}

			System.out.println("Our overall stats are:");
			for (int i = 0; i < turns; i++) {
				if (result.get(i).equals("user")) {
					userWins++;
				} else if (result.get(i).equals("system")) {
					systemWins++;
				} else {
					ties++;
				}
			}

			userPercent = (int) ((userWins / turns) * 100);
			systemPercent = (int) ((systemWins / turns) * 100);
			tiePercent = (int) ((ties / turns) * 100);
			System.out.println("I won: " + systemPercent + "%   You won: "
					+ userPercent + "%   We tied: " + tiePercent + "%");
		}
		// prints message if no matches played
		else {
			System.out.println("We didn't even play!");
		}

	}

	/**
	 * Doubles the size of the array once the array reaches its last index
	 * 
	 * @param original
	 *            user's array
	 * @return new array with array size double of original
	 */
	public static String[] increaseArray(String[] original) {
		String[] updated = new String[original.length * 2];
		for (int i = 0; i < original.length; i++) {
			updated[i] = original[i];
		}
		return updated;
	}

	/**
	 * Loops a game of RPS until the user enters "q" to exit the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int initialCapacity = 5;
		// Store the user's move history
		String[] userMoves = new String[initialCapacity];
		// Store the System's move history
		LinkedList<String> systemMoves = new LinkedList<String>();
		// Store match history
		ArrayList<String> matchHistory = new ArrayList<String>();

		int i = 0;
		int skip = 0;
		Scanner scnr = new Scanner(System.in);
		String move = "";
		boolean play = true;

		// Loops game of RPS until quit played
		while (play) {
			
			//skips choosing a new move if an invalid move was chosen before
			if (skip == 0) {
				String sysMove = systemMove(userMoves, matchHistory, i);
				systemMoves.add(sysMove);
				System.out
						.println("Let's play! What's your move? (r=rock, p=paper, s=scissors or q to quit)");
			}
			else{
				move = scnr.next();
				skip = 0;
			}

			// quits game when 'q' entered
			if (move.equals("q")) {
				System.out.println("Thanks for playing!");
				generateStats(userMoves, systemMoves, matchHistory, i);
				play = false;
			}
			// determines outcome if player chooses 'r'
			else if (move.equals("r")) {
				userMoves[i] = "rock";
				if (systemMoves.get(i).equals("paper")) {
					System.out.println("I choose paper. I win!");
					matchHistory.add("system");
				} else if (systemMoves.get(i).equals("scissors")) {
					System.out.println("I choose scissors. You win.");
					matchHistory.add("user");
				} else {
					System.out.println("I choose rock. It's a tie");
					matchHistory.add("tie");
				}
			}
			// determines outcome if player chooses 'p'
			else if (move.equals("p")) {
				userMoves[i] = "paper";
				if (systemMoves.get(i).equals("scissors")) {
					System.out.println("I choose scissors. I win!");
					matchHistory.add("system");
				} else if (systemMoves.get(i).equals("rock")) {
					System.out.println("I choose rock. You win.");
					matchHistory.add("user");
				} else {
					System.out.println("I choose paper. It's a tie");
					matchHistory.add("tie");
				}
			}
			// determines outcome if player chooses 's'
			else if (move.equals("s")) {
				userMoves[i] = "scissors";
				if (systemMoves.get(i).equals("rock")) {
					System.out.println("I choose rock. I win!");
					matchHistory.add("system");
				} else if (systemMoves.get(i).equals("paper")) {
					System.out.println("I choose paper. You win.");
					matchHistory.add("user");
				} else {
					System.out.println("I choose scissors. It's a tie");
					matchHistory.add("tie");
				}
			}
			// skips turn if invalid input chosen and prompts user for correct
			// input
			else {
				System.out
						.println("That is not a valid move. Please try again.");
				System.out
						.println("(r=rock, p=paper, s=scissors or q to quit)");
				skip++;
				continue;
			}
			i++;

			// increases userMoves array size if it reaches the array's size
			// length
			if (userMoves.length == i) {
				userMoves = increaseArray(userMoves);
			}
		}
		scnr.close();

	}

}