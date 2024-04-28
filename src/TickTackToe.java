import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class TickTackToe implements ActionListener {
    private static final int FRAME_SIZE = 800;
    private static final Color FRAME_BACKGROUND_COLOR = new Color(50, 50, 50);
    private static final String FRAME_TITLE = "Tic Tac Toe";
    private static final Color TEXT_FIELD_BACKGROUND_COLOR = new Color(25, 25, 25);
    private static final Color TEXT_FIELD_FOREGROUND_COLOR = new Color(25, 255, 0);
    private static final Font TEXT_FIELD_FONT = new Font("Ink Free", Font.BOLD, 75);
    private static final String TEXT_FIELD_TEXT = "Tic Tac Toe";
    private static final Color REPLAY_BUTTON_COLOR = Color.RED;
    private static final String REPLAY_BUTTON_TEXT = "Replay";
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(150, 150, 150); 

	JFrame frame = new JFrame();
	JPanel titlePanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JButton[] buttons = new JButton[9];
	Random random = new Random();
	JLabel textField = new JLabel();
	JButton replayButton = new JButton();
	JPanel replayPanel = new JPanel();
	boolean player1Turn;

	TickTackToe() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_SIZE, FRAME_SIZE);
		frame.getContentPane().setBackground(FRAME_BACKGROUND_COLOR);
		frame.setLayout(new BorderLayout());
		frame.setTitle(FRAME_TITLE);
		frame.setVisible(true);

		textField.setBackground(TEXT_FIELD_BACKGROUND_COLOR);
		textField.setForeground(TEXT_FIELD_BACKGROUND_COLOR);
		textField.setFont(TEXT_FIELD_FONT);
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setText(TEXT_FIELD_TEXT);
		textField.setOpaque(true);

		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBounds(0, 0, FRAME_SIZE, 100);

		replayButton.setText(REPLAY_BUTTON_TEXT);
		replayButton.setFocusable(false);
		replayButton.setBackground(REPLAY_BUTTON_COLOR);
		replayButton.addActionListener(this);

		replayPanel.setBackground(Color.BLUE);
		replayPanel.add(replayButton);

		buttonPanel.setLayout(new GridLayout(3, 3));
		buttonPanel.setBackground(BUTTON_BACKGROUND_COLOR);

		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			buttonPanel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
			buttons[i].addActionListener(this);
			buttons[i].setFocusable(false);
		}

		titlePanel.add(textField);
		frame.add(buttonPanel);
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(replayPanel, BorderLayout.WEST);

		firstTurn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 9; i++) {
			if (e.getSource() == buttons[i]) {
				if (player1Turn) {
					if (buttons[i].getText().equals("")) {
						buttons[i].setForeground(new Color(255, 0, 0));
						buttons[i].setText("X");
						player1Turn = false;
						textField.setText("O turn");
						check();
					}
				} else {
					if (buttons[i].getText().equals("")) {
						buttons[i].setForeground(new Color(0, 0, 255));
						buttons[i].setText("O");
						player1Turn = true;
						textField.setText("X turn");
						check();
					}
				}
			}
		}

		if (e.getSource() == replayButton) {
			resetGame();
		}
	}

	public void firstTurn() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (random.nextInt(2) == 0) {
			player1Turn = true;
			textField.setText("X turn");
		} else {
			player1Turn = false;
			textField.setText("O turn");
		}
	}

	public void check() {
		int[][] winConditions = {
				{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
				{0, 3, 6}, {1, 4, 7}, {2, 5, 8},
				{0, 4, 8}, {2, 4, 6}
		};

		for (int[] winCondition : winConditions) {
			if (checkWinCondition(winCondition)) {
				return;
			}
		}
		checkForDraw();
	}

	public boolean checkWinCondition(int[] winCondition) {
		if (!buttons[winCondition[0]].getText().equals("") &&
				buttons[winCondition[0]].getText().equals(buttons[winCondition[1]].getText()) &&
				buttons[winCondition[1]].getText().equals(buttons[winCondition[2]].getText())) {

			if (buttons[winCondition[0]].getText().equals("X")) {
				xWins(winCondition[0], winCondition[1], winCondition[2]);
			} else {
				oWins(winCondition[0], winCondition[1], winCondition[2]);
			}
			return true;
		}
		return false;
	}

	public void xWins(int a, int b, int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);

		for (JButton button : buttons) {
			button.setEnabled(false);
		}
		textField.setText("X Wins!");
	}

	public void oWins(int a, int b, int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);

		for (JButton button : buttons) {
			button.setEnabled(false);
		}
		textField.setText("O Wins!");
	}

	public void checkForDraw() {
		boolean allFilled = true;
		for (JButton button : buttons) {
			if (button.getText().equals("")) {
				allFilled = false;
				break;
			}
		}
		if (allFilled) {
			draw();
		}
	}

	public void draw() {
		for (JButton button : buttons) {
			button.setEnabled(false);
		}
		textField.setText("Draw!");
	}

	public void resetGame() {
		for (JButton button : buttons) {
			button.setText("");
			button.setEnabled(true);
			button.setBackground(new JButton().getBackground());
		}
		firstTurn();
	}
}
//