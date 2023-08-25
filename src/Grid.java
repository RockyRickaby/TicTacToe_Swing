import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Grid extends JPanel{
    private static final char[] play = {'X', 'O'};
    private Cell[][] grid;
    private int currplayer;
    private Stack<Cell> lastModified;
    private boolean halt;

    public Grid() {
        this.currplayer = 0;
        this.grid = new Cell[3][3];
        this.lastModified = new Stack<>();
        this.halt = false;
        this.setLayout(new GridLayout(3, 3));
        
        this.reset();
        this.setVisible(true);
    }

    public void reset() {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.currplayer = 0;
        this.halt = false;
        double screenSize = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int fontsize = 60;
        if (screenSize <= 1080) {
            fontsize = 90;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new Cell(this, fontsize);
                this.add(grid[i][j]);
            }
        }
    }

    public void action(Cell c) {
        if (halt) {
            return;
        }
        //System.out.println("Action listened!!");
        c.mark(play[currplayer]);
        lastModified.push(c);
        if (this.hasWinner()) {
            halt = true;
            this.disableGrid();
            JOptionPane.showMessageDialog(null, String.format("Player %d wins!", currplayer + 1), "Victory", JOptionPane.INFORMATION_MESSAGE, null);
        } else if (!this.hasEmptyCells()) {
            halt = true;
            this.disableGrid();
            JOptionPane.showMessageDialog(null, "Trie!", "Oh no!", JOptionPane.INFORMATION_MESSAGE, null);
        }
        currplayer = (currplayer + 1) % 2;
    }

    public boolean undoLastAction() {
        if (lastModified.isEmpty() || halt) {
            return false;
        }
        Cell old = lastModified.pop();
        old.toggleDisable();
        old.setText("");
        if (currplayer == 0) {
            currplayer = 1;
        } else {
            currplayer = 0;
        }
        return true;
    }

    private void disableGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].toggleDisable();
            }
        }
    }

    private boolean checkCol(String ch, int i) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            count += grid[j][i].getText().equals(ch) ? 1 : 0;
        }
        return count == 3;
    }

    private boolean checkRow(String ch, int i) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            count += grid[i][j].getText().equals(ch) ? 1 : 0;
        }
        return count == 3;
    }

    private boolean checkDiags(String ch) {
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < 3; i++) {
            count += grid[i][i].getText().equals(ch) ? 1 : 0;
            count2 += grid[i][2 - i].getText().equals(ch) ? 1 : 0;
        }
        return count == 3 || count2 == 3;
    }

    private boolean check(String ch) {
        boolean win = checkDiags(ch);
        for (int i = 0; i < 3 && !win; i++) {
            win = checkCol(ch, i);
        }
        for (int i = 0; i < 3 && !win; i++) {
            win = checkRow(ch, i);
        }
        return win;
    }

    private boolean hasEmptyCells() {
        int counter = 0;
        for (int i = 0; i < 3 && counter == 0; i++) {
            for (int j = 0; j < 3 && counter == 0; j++) {
                counter += grid[i][j].getText().equals("") ? 1 : 0;
            }
        }
        return counter > 0;
    }

    private boolean hasWinner() {
        //System.out.println(check("X"));
        //System.out.println(check("O"));
        return check("X") || check("O");
    }

    
}
