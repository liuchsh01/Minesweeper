package minesweeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinePanel extends JPanel {

    private int myWidth = 9;
    private int myHeight = 9;
    private int myMine = 10;
    public static Mine myMines[][];
    public static int myMinesValue[][];
    public static int myMinesClicked[][];//初始（0）、左击（-1）、右击（1插旗，2问题，0初始）

    MinePanel(int width, int height, int mine) {
        myWidth = width;
        myHeight = height;
        myMine = mine;
        this.setSize(myWidth * 20 + 1, myHeight * 20 + 1);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black));
        this.setLocation(30, 30);
        this.setLayout(null);
        initMine();
    }

    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    static public boolean winTest() {
        int width = Minesweeper.myMinePanel.getMyWidth();
        int height = Minesweeper.myMinePanel.getMyHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (myMinesValue[i][j] >= 0 && myMinesClicked[i][j] != -1) {
                    return false;
                }
            }
        }
        Minesweeper.myMinesweeper.timerRunning = false;
        Minesweeper.myMinesweeper.timer.cancel();
        int i, j;
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                if (MinePanel.myMinesValue[i][j] == -1 && MinePanel.myMinesClicked[i][j] != 1) {//is mine but without flag, show mine
                    MinePanel.myMines[i][j].setIcon(Mine.myMineIcon);
                }
            }
        }
        Object[] options = {"new", "exit"};
        int response = JOptionPane.showOptionDialog(null, "You are win, please choose to continue", "Winner", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response == 0) {
            Minesweeper.myMinesweeper.restart();
        } else if (response == 1) {
            System.exit(0);
        }
        return true;
    }

    private void initMine() {
        this.removeAll();

        int i, j, k;

        //number between 0 ~ (w*h-1)
        ArrayList<Integer> temp = new ArrayList<>();
        for (i = 0; i < myWidth * myHeight; i++) {
            temp.add(i);
        }

        myMines = new Mine[myWidth][];
        myMinesValue = new int[myWidth][];
        myMinesClicked = new int[myWidth][];
        for (i = 0; i < myWidth; i++) {
            myMines[i] = new Mine[myHeight];
            myMinesValue[i] = new int[myHeight];
            myMinesClicked[i] = new int[myHeight];
            for (j = 0; j < myHeight; j++) {
                myMinesValue[i][j] = 0;
                myMinesClicked[i][j] = 0;
            }
        }

        //get random number between 0 ~ (w*h-1)
        for (i = 0; i < myMine; i++) {
            j = temp.remove(new Random().nextInt(temp.size()));
            myMinesValue[j % myWidth][j / myWidth] = -1;
        }

        for (i = 0; i < myWidth; i++) {
            for (j = 0; j < myHeight; j++) {
                if (myMinesValue[i][j] != -1) {
                    //get mine num around this node
                    k = 0;
                    if (i > 0) {
                        if (j > 0 && myMinesValue[i - 1][j - 1] == -1) {
                            k++;
                        }
                        if (myMinesValue[i - 1][j] == -1) {
                            k++;
                        }
                        if (j < myHeight - 1 && myMinesValue[i - 1][j + 1] == -1) {
                            k++;
                        }
                    }
                    if (j > 0 && myMinesValue[i][j - 1] == -1) {
                        k++;
                    }
                    if (j < myHeight - 1 && myMinesValue[i][j + 1] == -1) {
                        k++;
                    }
                    if (i < myWidth - 1) {
                        if (j > 0 && myMinesValue[i + 1][j - 1] == -1) {
                            k++;
                        }
                        if (myMinesValue[i + 1][j] == -1) {
                            k++;
                        }
                        if (j < myHeight - 1 && myMinesValue[i + 1][j + 1] == -1) {
                            k++;
                        }
                    }
                    myMinesValue[i][j] = k;
                }
                myMines[i][j] = new Mine(i, j, myMinesValue[i][j]);
                this.add(myMines[i][j]);
            }
        }
    }
}
