import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
/*
   Applet version of the Earlier commane-line application. Uses the same Board object as
   a member variable of the Applet.
*/
public class TicTacToeApplet
    extends Applet implements ActionListener {
  SquareButton[] buttons = new SquareButton[26];
  Label computerScoreLbl = new Label();
  Label humanScoreLbl = new Label();
  Label tieScoreLbl = new Label();
  int humanScore = 0, computerScore = 0, tieScore = 0;
  Board board;
  boolean gameOver;
  public void init() {
      Panel boardPanel = new Panel(new GridLayout(5, 5, 1, 1));
    this.setLayout(new GridLayout(6, 1, 1, 1));
    for (int i = 1; i <= 25; i++) {
      (buttons[i] = new SquareButton(i)).addActionListener(this);
      this.add(buttons[i]);
    }
    this.add(boardPanel, BorderLayout.NORTH);
    this.add(computerScoreLbl,BorderLayout.SOUTH);
    this.add(humanScoreLbl, BorderLayout.SOUTH);
    this.add(tieScoreLbl, BorderLayout.SOUTH);
    startGame();
  }

  public void startGame() {
    int depth =1;
    gameOver = false;
    board = new Board();
    SquareButton.clearLast();
    addRandomMoves();
    if (Math.random() < 0.5) board.computerMove(depth);
    drawBoard();
  }
    public void addRandomMoves(){
        board.addRandomMoves();
        drawBoard();
    }
  public void endGame(boolean computerWin, boolean humanWin) {
    gameOver = true;
    String message =
        (computerWin ? "I Win." : (humanWin ? "Yow Win." : "Tie Game.")) +
        " Play again?";
    if(computerWin) computerScore++;
    else if(humanWin) humanScore++;
    else tieScore++;
    humanScoreLbl.setText("Human Score: " + humanScore);
    computerScoreLbl.setText("Computer Score: " + computerScore);
    tieScoreLbl.setText("Number of Ties: " + tieScore);
    if (JOptionPane.showOptionDialog(null, message, "", JOptionPane.YES_NO_OPTION, 0, null, null, null) == JOptionPane.YES_OPTION)    
        startGame();
  }

  public void drawBoard() {
    for (int i = 1; i < 26; i++)
      buttons[i].setLabel(Character.toString(board.squares[i]));   
    humanScoreLbl.setText("Human Score: " + humanScore);
    computerScoreLbl.setText("Computer Score: " + computerScore);
    tieScoreLbl.setText("Number of Ties: " + tieScore);
  }

  public void actionPerformed(ActionEvent e) {
    int depth = 3;  // how many times you recursively evaluate moves
    int squareNum = ( (SquareButton) e.getSource()).id;
    if (!board.isFreeSquare(squareNum) || gameOver) return; // can't move here
    if (move(squareNum,true)) return ; // human move
    try {Thread.sleep(500);} catch (InterruptedException e1) {} // a slight pause
    move(board.bestMove(depth).square,false);  // computer move
  }

// Make the move.
// If the game is determined to be over then call endGame() and return true.
// Otherwise return false.
boolean move(int squareNum, boolean humanMove) {
  board.moveToSquare(squareNum); // human move
  this.drawBoard();
  if (board.boardValue() == board.xWinFlag|| board.boardValue() == board.oWinFlag ) /* someone won */ {
    endGame(!humanMove, humanMove);
    return true;  // game over
  }
  if (board.boardFull())  /* tie game */ {
    endGame(false, false);
    return true;  // game over
  }
  return false;  // game continues
}


}
// Provides a colored button to represent one of the the nine squares.
class SquareButton extends Button implements ActionListener {
    private static SquareButton lastClicked = null;
    private static final Color defaultColor = new Color(80,180,255), clickedColor = new Color(80,230,220);
    int id;
    public SquareButton(int id) {
      super();
      this.id = id;
      this.setFont(new Font("Courier",Font.BOLD,54));
      this.setBackground(defaultColor);
      this.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
      String s = e.getActionCommand();
       if (s.equals("X") || s.equals("O")) return;
       clearLast();
       setLast(this);
    }
    public static void clearLast() {
      if (lastClicked != null) {
        lastClicked.setBackground(defaultColor);
        lastClicked = null;
      }
    }
    public static void setLast(SquareButton b) {
      b.setBackground(clickedColor);
      lastClicked = b;
    }
  }
