
import java.util.Scanner;

public class Board {
  static Scanner in = new Scanner(System.in);
  public char [] squares = new char[26]; // element zero not used
  int moveCount = 0;
  public int xWinFlag = 7;
  public int oWinFlag = -7;
  public int posAboutToWinFlag = 5;
  
  
  static final char freeChar = '_';  // to indicate the square is available.
  
  public Board() {
    for (int i = 1; i <= 25; i++) squares[i] = freeChar;  // all 25 squares are initially available
  }
  
  public boolean moveToSquare(int square) {
    if (squares[square] != freeChar) return false; // already and X or O at that location
    squares[square] = xturn() ? 'X' : 'O'; 
    moveCount++;
    return true;
  }
  boolean xturn() { return moveCount % 2 == 0;}  // X's turn always follows an even number of previous moves

  boolean isFreeSquare(int square) { return squares[square] == freeChar; }

  void unDo(int square){
    moveCount--;
    squares[square] = freeChar;
  }
  boolean boardFull() { return moveCount == 25; }

  int lineValue(int s1, int s2, int s3, int s4) {        
    if (squares[s1] == 'X' && squares[s2] == 'X' && squares[s3] == 'X' && squares[s4] == 'X') return xWinFlag;  // win for X
    else if (squares[s1] == 'O' && squares[s2] == 'O' && squares[s3] == 'O' && squares[s4] == 'O') return oWinFlag; // win for O
    
    else if (squares[s1] == 'O' && squares[s2] == 'O' && squares[s3] == 'O' && squares[s4] == freeChar) return -2; 
    else if (squares[s1] == 'O' && squares[s2] == 'O' && squares[s3] == freeChar && squares[s4] == 'O') return -2;
    else if (squares[s1] == 'O' && squares[s2] == freeChar && squares[s3] == 'O' && squares[s4] == 'O') return -2;
    else if (squares[s1] == freeChar && squares[s2] == 'O' && squares[s3] == 'O' && squares[s4] == 'O') return -2;
    
    else if (squares[s1] == 'X' && squares[s2] == 'X' && squares[s3] == 'X' && squares[s4] == freeChar) return 2; 
    else if (squares[s1] == 'X' && squares[s2] == 'X' && squares[s3] == freeChar && squares[s4] == 'X') return 2;
    else if (squares[s1] == 'X' && squares[s2] == freeChar && squares[s3] == 'X' && squares[s4] == 'X') return 2;
    else if (squares[s1] == freeChar && squares[s2] == 'X' && squares[s3] == 'X' && squares[s4] == 'X') return 2;
    
    else if (squares[s1] == 'O' && squares[s2] == 'O' && squares[s3] == freeChar && squares[s4] == freeChar) return -1; 
    else if (squares[s1] == 'O' && squares[s2] == freeChar && squares[s3] == 'O' && squares[s4] == freeChar) return -1; 
    else if (squares[s1] == freeChar && squares[s2] == 'O' && squares[s3] == 'O' && squares[s4] == freeChar) return -1;
    
    else if (squares[s1] == 'O' && squares[s2] == freeChar && squares[s3] == freeChar && squares[s4] == 'O') return -1;
    else if (squares[s1] == freeChar && squares[s2] == 'O' && squares[s3] == freeChar && squares[s4] == 'O') return -1;
    
    else if (squares[s1] == freeChar && squares[s2] == freeChar && squares[s3] == 'O' && squares[s4] == 'O') return -1;
    
    
    else if (squares[s1] == 'X' && squares[s2] == 'X' && squares[s3] == freeChar && squares[s4] == freeChar) return 1; 
    else if (squares[s1] == 'X' && squares[s2] == freeChar && squares[s3] == 'X' && squares[s4] == freeChar) return 1; 
    else if (squares[s1] == freeChar && squares[s2] == 'X' && squares[s3] == 'X' && squares[s4] == freeChar) return 1;
    
    else if (squares[s1] == 'X' && squares[s2] == freeChar && squares[s3] == freeChar && squares[s4] == 'X') return 1;
    else if (squares[s1] == freeChar && squares[s2] == 'X' && squares[s3] == freeChar && squares[s4] == 'X') return 1;
    
    else if (squares[s1] == freeChar && squares[s2] == freeChar && squares[s3] == 'X' && squares[s4] == 'X') return 1;
    return 0;  // nobody has won yet
  }
  
  int boardValue() { // 1 = win for X, -1 = win for O
    int[][] wins = {{1,2,3,4},{2,3,4,5},{6,7,8,9},{7,8,9,10},{11,12,13,14},{12,13,14,15},{16,17,18,19},{17,18,19,20},{21,22,23,24},{22,23,24,25},{1,6,11,16},{6,11,16,21},{2,7,12,17},{7,12,17,22},{3,8,13,18},{8,13,18,23},{4,9,14,19},{9,14,19,24},{5,10,15,20},{10,15,20,25},{6,12,18,24},{1,7,13,19},{7,13,19,25},{2,8,14,20},{4,8,12,16},{5,9,13,17},{9,13,17,21},{10,14,18,22}};
    int min = 0, max = 0;
    int numberOfTwoXInARow = 0;
    int numberOfThreeXInARow = 0;
    int numberOfTwoOInARow = 0;
    int numberOfThreeOInARow = 0;
    for (int i = 0; i < wins.length; i++) {
      int v = lineValue(wins[i][0], wins[i][1], wins[i][2], wins[i][3]);
      if (v == xWinFlag || v == oWinFlag) return v;  // a winning line of X's or O's has been found
      // count how many 2s in a row and how many 3s in a row
      if(v == 2) numberOfThreeXInARow++;
      if(v == 1) numberOfTwoXInARow++;
      
      if(v == -2) numberOfThreeOInARow++;
      if(v == -1) numberOfTwoOInARow++;
      
      //if(v < min) min = v;
      //if (v > max) max = v;
    }
    // 1 = 2 X's in a row
    // 2 = two sets of 2 X's in a row
    // 3 = three sets of 2 X's in a row
    // 4 = four sets of 2 X's in a row
    // 5 = 3 X's in a row
    // 6 = two sets of 3 X's in a row    
    if(numberOfThreeXInARow > 0){
        if(numberOfThreeXInARow > 1)
            max = 6;        
        else max = 5;
    }
    else {  // no 3 Xs in a row
        if(numberOfTwoXInARow > 0){
            switch(numberOfTwoXInARow){
                case 1: max = 1;
                break;
                case 2: max = 2;
                break;
                case 3: max = 3;
                break;
                case 4: max = 4;
                break;
            }     
        }        
    }
     if(numberOfThreeOInARow > 0){
        if(numberOfThreeOInARow > 1)
            min = -6;        
        else min = -5;
    }
    else {  // no 3 Os in a row
        if(numberOfTwoOInARow > 0){            
            switch(numberOfTwoOInARow){
                case 1: min = -1;
                break;
                case 2: min = -2;
                break;
                case 3: min = -3;
                break;
                case 4: min = -4;
                break;
            }     
        }        
    }
            System.out.println("XTurn: " + xturn() + "min: " + min + " max: " + max);
    if(!xturn()) { // X just moved. calculating value of X's move
        if((min == 0 && max > 0) || (Math.abs(min) < 5 && max > 4)) // if X has a better move ie. more X's in a row than O (not just more combinations).
            return max;
        else return min;
    }
    else { // O just moved. calculating value of O's move
        if((max == 0 && Math.abs(min) > 0) || (Math.abs(min) > 4 && max < 5)) // if O has a better move ie. more O's in a row than X (not just more combos).
            return min;
        else return max;
    }
    
    // return 0; // nobody has won so far
  }
  
 // draw the board
public void draw() {
  for (int i = 1; i < 26; i++){
    if (isFreeSquare(i)) {
        if(i<10) System.out.print(" " + i);
        else System.out.print(i);
    }
    else 
        System.out.print(" " + squares[i]);    
    System.out.print(" ");
    if (i % 5 == 0) System.out.println();
  }
}
// adds random moves at the beginning of the game
public void addRandomMoves(){
    int randomMovesCount = 1 + (int)(Math.random()*3);
    for(int i = 0;i<randomMovesCount*2;){
        int randomMove = 1 + (int)(Math.random()*25);
        if(moveToSquare(randomMove)) i++;
    }
}
// get next move from the user.
public boolean userMove(int numberOfEvals) {    
    boolean legalMove;
  int s;
  System.out.print("\nEnter a square number: ");
  do {
	  s = in.nextInt();
      legalMove = squares[s] == freeChar;
      if (!legalMove) System.out.println("Try again: ");
  } while (!legalMove);
  Move m = new Move(s,evaluateMove(s,numberOfEvals));
  moveToSquare(s);
  System.out.println("Human move: " + m);
  this.draw();
  if (this.boardValue() == xWinFlag || this.boardValue() == oWinFlag) return true; // a winning move
  return false;
}

public boolean computerMove(int numberOfEvals) {     
    // number of recursive iterations to find the best move
  try {Thread.sleep(600);} catch (InterruptedException e) {}
  Move m = this.bestMove(numberOfEvals);
  moveToSquare(m.square);
  System.out.println("\nComputer move: " + m);
  draw();
  if (this.boardValue() == xWinFlag || this.boardValue() == oWinFlag) return true; // a winning move
  return false;
}

// get a random number from min to max inclusive
static int rand(int min, int max) {
	return (int) (Math.random() * (max - min + 1) + min);
}


// randomize order of squares to look through
static void randomizeOrder(int[] squareList) {
	for (int i = 1; i < 26; i++)
		squareList[i] = i;
	for (int i = 1; i < 26; i++) {
			int index1 = rand(1,25);
			int index2 = rand(1,25);
			int temp = squareList[index1];
			squareList[index1] = squareList[index2];
			squareList[index2] = temp;			
	}
}

/* 
 *  Return a Move object representing a best move for the current player.
 *  Use minimax strategy, meaning out of all possible moves, choose the one that is the worst for your opponent.
 *  Provisionally make a move, then recursively evaluate your opponent's possible responses. 
 *  Your best move is the one that "minimizes" the value of your opponent's best response.
*/
public Move bestMove(int numberOfEvals) {
    numberOfEvals--;
    Move bestSoFar = new Move();  // an impossibly "bad" move.
    
    int [] squares = new int[26];
    randomizeOrder(squares);
    for (int i = 1; i < 26; i++) {  // consider the possible moves in some random order    
        int s = squares[i];
        if (isFreeSquare(s)) {
            bestSoFar = new Move(squares[i],evaluateMove(s,numberOfEvals));
            break;
        }
    
    }
    if(xturn()) System.out.println("X move:");
        else System.out.println("O move:");
    for (int i = 1; i < 26; i++) {  // consider the possible moves in some random order    
        int s = squares[i];
        if (isFreeSquare(s)) {                    
            Move m = new Move(squares[i],evaluateMove(s,numberOfEvals)); 
            if (m.betterThan(bestSoFar))  bestSoFar = m;
            System.out.println("BestMove: " + bestSoFar + " CurrentMove: " + m);
        }
    }
    return bestSoFar;
}

public int evaluateMove(int square, int numberOfEvals) {

    moveToSquare(square);
    
    int val = boardValue(); // if this is != 0 then it's a winning move

     if (!boardFull() && val != xWinFlag && val != oWinFlag && numberOfEvals > 0)   val = bestMove(numberOfEvals).value;
    
    unDo(square);

    return val;

    /*
     * The numerical value of my move is equal to the value of opponent's best response. 
     * For example, suppose I'm X and I want to evaluate a move to a certain square.
     * We determine that O's best response (to some other square) has value -1. 
     * That's a good number for O. (In fact, it means a win for O) but a bad number for me.  
     * When comparing moves, we prefer small numbers for O and big numbers for X.
     * The Move.betterThan() method makes this determination.
     */
}


  // Move is an inner class and allows us to wrap a square and a value together. 
  // It's an inner class so we have access to the xturn() method of Board.
  class Move {
    int square, value;
    public Move(int square, int value) {
      this.square = square;
      this.value = value;
    }
    public Move() {
      this(0, Board.this.xturn() ? oWinFlag -1 : xWinFlag + 1);  // give this impossible move an impossibly bad value
    }
  
    boolean betterThan(Move m) {
      if (Board.this.xturn()) return this.value > m.value;
      else return this.value < m.value;
    }
    public String toString() {return "[ square=" + square + ", value=" + value + " ]";
    }
  }
  
  
public static void main(String [] args) {
  Board b = new Board();
  int numberOfEvals = 2;
  b.draw();
  if (Math.random() < 0.5) b.computerMove(numberOfEvals);
 // else  b.draw();  // human will move first
  while (!b.boardFull()) {
    if (b.userMove(numberOfEvals)) {
      System.out.println("Congratulations! You win!");
      break;
    }
    if (!b.boardFull() && b.computerMove(numberOfEvals)) {
      System.out.println("Computer wins this one.");
      break;
    }
  }
  if (b.boardValue() == 0)
    System.out.println("Tie!");
}
}
