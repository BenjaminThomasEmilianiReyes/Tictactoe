import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends Frame implements ActionListener {
    Button[] buttons = new Button[9];
    boolean xTurn = true; // true = X's turn, false = O's turn
    Label statusLabel;

    public TicTacToe() {
        super("Tic Tac Toe - AWT");
        setLayout(new BorderLayout());

        Panel gamePanel = new Panel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].addActionListener(this);
            gamePanel.add(buttons[i]);
        }

        Panel controlPanel = new Panel(new FlowLayout());
        Button resetButton = new Button("New Game");
        resetButton.addActionListener(e -> resetGame());
        controlPanel.add(resetButton);

        statusLabel = new Label("Player X's turn");
        controlPanel.add(statusLabel);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(400, 450);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        Button clicked = (Button) e.getSource();
        if (!clicked.getLabel().equals("")) return;

        clicked.setLabel(xTurn ? "X" : "O");
        if (checkWinner()) {
            statusLabel.setText("Player " + (xTurn ? "X" : "O") + " wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("Draw!");
        } else {
            xTurn = !xTurn;
            statusLabel.setText("Player " + (xTurn ? "X" : "O") + "'s turn");
        }
    }

    private boolean checkWinner() {
        String[][] grid = new String[3][3];
        for (int i = 0; i < 9; i++) {
            grid[i / 3][i % 3] = buttons[i].getLabel();
        }

        for (int i = 0; i < 3; i++) {
            if (!grid[i][0].equals("") && grid[i][0].equals(grid[i][1]) && grid[i][1].equals(grid[i][2])) return true;
            if (!grid[0][i].equals("") && grid[0][i].equals(grid[1][i]) && grid[1][i].equals(grid[2][i])) return true;
        }
        if (!grid[0][0].equals("") && grid[0][0].equals(grid[1][1]) && grid[1][1].equals(grid[2][2])) return true;
        if (!grid[0][2].equals("") && grid[0][2].equals(grid[1][1]) && grid[1][1].equals(grid[2][0])) return true;

        return false;
    }

    private boolean isDraw() {
        for (Button b : buttons) {
            if (b.getLabel().equals("")) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (Button b : buttons) {
            b.setEnabled(false);
        }
    }

    private void resetGame() {
        for (Button b : buttons) {
            b.setLabel("");
            b.setEnabled(true);
        }
        xTurn = true;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
