//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package CodSoft_Project.src;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Task_1 extends JFrame implements ActionListener {
    private int Random_Number;
    private int Remaining_Attempts;
    private int TotalRoundWon = 0;
    private int Attempts = 0;
    private final JTextField Guess_Field;
    private final JButton Guess_Button;
    private final JButton New_Button;
    private final JLabel Info_Label;
    private JLabel Attempts_Label;
    private final JLabel rounds_won_Label;
    private JLabel Total_Attempts_Label;
    private final JLabel average_attempts;

    public Task_1() {
        this.setTitle("----Number Game----");
        this.setSize(800, 400);
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        JPanel main = new JPanel(new GridLayout(6, 1));
        this.Info_Label = new JLabel("Guess a number...", 0);
        this.Info_Label.setFont(new Font("xyz", 0, 25));
        this.Guess_Field = new JTextField();
        this.Guess_Field.setFont(new Font("xyz", 0, 30));
        this.Guess_Field.setHorizontalAlignment(0);
        this.Guess_Button = new JButton("Guess");
        this.Guess_Button.setFont(new Font("xyz", 0, 21));
        this.New_Button = new JButton("Want to play new game");
        this.New_Button.setFont(new Font("xyz", 0, 21));
        this.Attempts_Label = new JLabel("You have remaining Attempts : 10", 0);
        this.Attempts_Label.setFont(new Font("xyz", 0, 25));
        this.Guess_Button.addActionListener(this);
        this.New_Button.addActionListener((e) -> this.startNewGame());
        this.New_Button.setEnabled(false);
        main.add(this.Info_Label);
        main.add(this.Guess_Field);
        main.add(this.Guess_Button);
        main.add(this.New_Button);
        main.add(this.Attempts_Label);
        JPanel Score = new JPanel(new GridLayout(1, 3));
        this.rounds_won_Label = new JLabel("Rounds won : 0", 0);
        this.rounds_won_Label.setFont(new Font("xyz", 0, 19));
        this.Attempts_Label = new JLabel("Total Attempts : 0", 0);
        this.Attempts_Label.setFont(new Font("xyz", 0, 19));
        this.average_attempts = new JLabel("Average Attempts : 0.0", 0);
        this.average_attempts.setFont(new Font("xyz", 0, 19));
        Score.add(this.rounds_won_Label);
        Score.add(this.Attempts_Label);
        Score.add(this.average_attempts);
        this.add(main, "Center");
        this.add(Score, "South");
        this.startNewGame();
        this.setVisible(true);
    }

    private void startNewGame() {
        this.Random_Number = (int)(Math.random() * (double)100.0F + (double)1.0F);
        this.Remaining_Attempts = 10;
        this.Guess_Button.setEnabled(true);
        this.New_Button.setEnabled(false);
        this.Guess_Field.setText("");
        this.Info_Label.setText("Guess a number between 1 and 100 . You have 10 attempts.");
        this.Attempts_Label.setText("Remaining Attempts : " + this.Remaining_Attempts);
    }

    public void actionPerformed(ActionEvent e) {
        String input = this.Guess_Field.getText();

        try {
            int guess = Integer.parseInt(input);
            --this.Remaining_Attempts;
            if (guess == this.Random_Number) {
                ++this.TotalRoundWon;
                int attemptsUsed = 10 - this.Remaining_Attempts;
                this.Attempts += attemptsUsed;
                this.Info_Label.setText("Congratulations , You guess correct in " + attemptsUsed + "attempts\n If you want to play again then press New game.");
                this.Guess_Button.setEnabled(false);
                this.New_Button.setEnabled(true);
                this.updateScorePanel();
            } else if (guess > this.Random_Number) {
                this.Info_Label.setText("oops sorry !!! you guess too high ... try again.");
            } else {
                this.Info_Label.setText("oops sorry !!! You guess too low ... try again.");
            }

            if (this.Remaining_Attempts == 0 && guess != this.Random_Number) {
                this.Info_Label.setText("Game over ! Try again ... The correct number was " + this.Random_Number);
                this.Guess_Button.setEnabled(false);
                this.New_Button.setEnabled(true);
            }

            this.Attempts_Label.setText("Remaining attempts : " + this.Remaining_Attempts);
            this.Guess_Field.setText("");
        } catch (NumberFormatException var5) {
            this.Info_Label.setText("Please enter a Valid number !!!!");
        }

    }

    private void updateScorePanel() {
        if (this.TotalRoundWon > 0) {
            double var10000 = (double)this.Attempts / (double)this.TotalRoundWon;
        } else {
            double var3 = (double)0.0F;
        }

        this.rounds_won_Label.setText("Rounds won : " + this.TotalRoundWon);
        this.Total_Attempts_Label.setText("Total Attempts : " + this.Attempts);
        JLabel var4 = this.average_attempts;
        Object[] var10002 = new Object[]{this.average_attempts};
        var4.setText("Avg attempts/Win : " + String.format("%.1f", var10002));
    }

    public static void main(String[] args) {
        new Task_1();
    }
}
