import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

public class Game extends JFrame{
    private Grid grid;

    public Game() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        JButton restart = new JButton("restart");
        restart.setPreferredSize(new Dimension(75, 25));
        restart.addActionListener(e -> {
           // System.out.println("Restarting grid!!!");
            grid.reset();
        });

        JButton undo = new JButton("undo");
        undo.setPreferredSize(new Dimension(75, 25));
        undo.addActionListener(e -> {
            //System.out.println("Undoing");
            grid.undoLastAction();
        });

        buttons.add(restart);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(undo);

        grid = new Grid();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        //c.anchor = GridBagConstraints.PAGE_START;
        panel.add(buttons, c);
        c.weightx = .5;
        c.weighty = .5;
        c.fill = GridBagConstraints.BOTH;
        panel.add(grid, c);

        JScrollPane scrollpane = new JScrollPane(panel);
        double screenSize = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        scrollpane.setMaximumSize(new Dimension((int)screenSize, (int) screenSize));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(scrollpane);
        
        
        this.getContentPane().add(wrapper);
        //this.pack();
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
