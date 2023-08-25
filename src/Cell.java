import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cell extends JButton implements ActionListener{
    private boolean marked;
    private Grid grid;

    public Cell(Grid grid, int fontsize) {
        super();
        this.grid = grid;
        this.marked = false;
        super.setFont(new Font("Arial", Font.PLAIN, fontsize));
        super.setPreferredSize(new Dimension(100, 100));
        super.addActionListener(this);
    }

    public void mark(char ch) {
        super.setText(String.valueOf(ch));
        this.marked = true;
    }

    public void toggleDisable() {
        this.marked = !this.marked;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!marked) {
            grid.action(this);
        }
    }
}
