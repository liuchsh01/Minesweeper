package minesweeper;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Mine extends JButton {

    private final int myValue;
    private final int myX, myY;
    static public ImageIcon myNumber[];
    static public ImageIcon myMineIcon, mySmileIcon, myOOIcon, myTimeIcon, myFlagIcon, myUnsureIcon, myWrongFlagIcon;

    Mine(int x, int y, int value) {
        myX = x;
        myY = y;
        myValue = value;
        this.setSize(20, 20);
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.black));
        this.setLocation(x * 20, y * 20);
        this.setFocusPainted(false);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (!Minesweeper.myMinesweeper.timerRunning) {
                    Minesweeper.myMinesweeper.timer = new Timer();
                    Minesweeper.myMinesweeper.timer.schedule(new myTask(), 1000, 1000);
                    Minesweeper.myMinesweeper.timerRunning = true;
                }
                Mine myMine = (Mine) me.getSource();
                int X = myMine.getMyX();
                int Y = myMine.getMyY();
                int clicked = MinePanel.myMinesClicked[X][Y];
                int value = myMine.getMyValue();
                if (me.getButton() == MouseEvent.BUTTON1) {
                    if (clicked == 0 || clicked == 2) {
                        //myMine.setEnabled(false);
                        MinePanel.myMinesClicked[X][Y] = -1;
                        myMine.setBackground(new Color(0xcc, 0xcc, 0xdd));
                        if (value == -1) {
                            //mine show all mine
                            int width = Minesweeper.myMinePanel.getMyWidth();
                            int height = Minesweeper.myMinePanel.getMyHeight();
                            Minesweeper.myMinesweeper.timerRunning = false;
                            Minesweeper.myMinesweeper.timer.cancel();
                            Minesweeper.myMinesweeper.newGameBt.setIcon(Minesweeper.myMinesweeper.newGameBtOO);
                            int i, j;
                            for (i = 0; i < width; i++) {
                                for (j = 0; j < height; j++) {
                                    if (MinePanel.myMinesValue[i][j] == -1 && MinePanel.myMinesClicked[i][j] != 1) {//is mine but without flag, show mine
                                        MinePanel.myMines[i][j].setIcon(myMineIcon);
                                    } else if (MinePanel.myMinesClicked[i][j] == 1 && MinePanel.myMinesValue[i][j] != -1) {//with a flag but isnt mine, show wrongFlag
                                        MinePanel.myMines[i][j].setIcon(myWrongFlagIcon);
                                    }
                                }
                            }
//                            Object[] options = {"new", "exit"};
//                            int response = JOptionPane.showOptionDialog(null, "You are lose, please choose to continue", "Gameover", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//                            if (response == 0) {
//                                Minesweeper.myMinesweeper.restart();
//                            } else if (response == 1) {
//                                System.exit(0);
//                            }
                        } else if (value == 0) {
                            //extend
                            int width = Minesweeper.myMinePanel.getMyWidth();
                            int height = Minesweeper.myMinePanel.getMyHeight();
                            if (X > 0) {
                                if (Y > 0 && (MinePanel.myMinesClicked[X - 1][Y - 1] == 0 || MinePanel.myMinesClicked[X - 1][Y - 1] == 2)) {
                                    MinePanel.myMines[X - 1][Y - 1].extendCilck();
                                }
                                if (MinePanel.myMinesClicked[X - 1][Y] == 0 || MinePanel.myMinesClicked[X - 1][Y] == 2) {
                                    MinePanel.myMines[X - 1][Y].extendCilck();
                                }
                                if (Y < height - 1 && (MinePanel.myMinesClicked[X - 1][Y + 1] == 0 || MinePanel.myMinesClicked[X - 1][Y + 1] == 2)) {
                                    MinePanel.myMines[X - 1][Y + 1].extendCilck();
                                }
                            }
                            if (Y > 0 && (MinePanel.myMinesClicked[X][Y - 1] == 0 || MinePanel.myMinesClicked[X][Y - 1] == 2)) {
                                MinePanel.myMines[X][Y - 1].extendCilck();
                            }
                            if (Y < height - 1 && (MinePanel.myMinesClicked[X][Y + 1] == 0 || MinePanel.myMinesClicked[X][Y + 1] == 2)) {
                                MinePanel.myMines[X][Y + 1].extendCilck();
                            }
                            if (X < width - 1) {
                                if (Y > 0 && (MinePanel.myMinesClicked[X + 1][Y - 1] == 0 || MinePanel.myMinesClicked[X + 1][Y - 1] == 2)) {
                                    MinePanel.myMines[X + 1][Y - 1].extendCilck();
                                }
                                if (MinePanel.myMinesClicked[X + 1][Y] == 0 || MinePanel.myMinesClicked[X + 1][Y] == 2) {
                                    MinePanel.myMines[X + 1][Y].extendCilck();
                                }
                                if (Y < height - 1 && (MinePanel.myMinesClicked[X + 1][Y + 1] == 0 || MinePanel.myMinesClicked[X + 1][Y + 1] == 2)) {
                                    MinePanel.myMines[X + 1][Y + 1].extendCilck();
                                }
                            }
                            MinePanel.winTest();
                        } else if (value > 0) {
                            //just show
                            myMine.setIcon(myNumber[value]);
                            MinePanel.winTest();
                        }
                    }
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    switch (clicked) {
                        case 0:
                            MinePanel.myMinesClicked[X][Y] = 1;
                            myMine.setIcon(myFlagIcon);
                            //show myLabel[2]
                            Minesweeper.myMinesweeper.myLabel[2].setText("" + (--Minesweeper.myMinesweeper.myMineCount));
                            break;
                        case 1:
                            MinePanel.myMinesClicked[X][Y] = 2;
                            myMine.setIcon(myUnsureIcon);
                            Minesweeper.myMinesweeper.myLabel[2].setText("" + (++Minesweeper.myMinesweeper.myMineCount));
                            break;
                        case 2:
                            MinePanel.myMinesClicked[X][Y] = 0;
                            myMine.setIcon(myNumber[0]);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
    }

    public int getMyValue() {
        return myValue;
    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }

    public void extendCilck() {
        setBackground(new Color(0xcc, 0xcc, 0xdd));
        MinePanel.myMinesClicked[myX][myY] = -1;
        setIcon(myNumber[myValue]);
        if (myValue == 0) {
            //continue extend
            int X = myX;
            int Y = myY;
            int width = Minesweeper.myMinePanel.getMyWidth();
            int height = Minesweeper.myMinePanel.getMyHeight();
            if (X > 0) {
                if (Y > 0 && (MinePanel.myMinesClicked[X - 1][Y - 1] == 0 || MinePanel.myMinesClicked[X - 1][Y - 1] == 2)) {
                    MinePanel.myMines[X - 1][Y - 1].extendCilck();
                }
                if (MinePanel.myMinesClicked[X - 1][Y] == 0 || MinePanel.myMinesClicked[X - 1][Y] == 2) {
                    MinePanel.myMines[X - 1][Y].extendCilck();
                }
                if (Y < height - 1 && (MinePanel.myMinesClicked[X - 1][Y + 1] == 0 || MinePanel.myMinesClicked[X - 1][Y + 1] == 2)) {
                    MinePanel.myMines[X - 1][Y + 1].extendCilck();
                }
            }
            if (Y > 0 && (MinePanel.myMinesClicked[X][Y - 1] == 0 || MinePanel.myMinesClicked[X][Y - 1] == 2)) {
                MinePanel.myMines[X][Y - 1].extendCilck();
            }
            if (Y < height - 1 && (MinePanel.myMinesClicked[X][Y + 1] == 0 || MinePanel.myMinesClicked[X][Y + 1] == 2)) {
                MinePanel.myMines[X][Y + 1].extendCilck();
            }
            if (X < width - 1) {
                if (Y > 0 && (MinePanel.myMinesClicked[X + 1][Y - 1] == 0 || MinePanel.myMinesClicked[X + 1][Y - 1] == 2)) {
                    MinePanel.myMines[X + 1][Y - 1].extendCilck();
                }
                if (MinePanel.myMinesClicked[X + 1][Y] == 0 || MinePanel.myMinesClicked[X + 1][Y] == 2) {
                    MinePanel.myMines[X + 1][Y].extendCilck();
                }
                if (Y < height - 1 && (MinePanel.myMinesClicked[X + 1][Y + 1] == 0 || MinePanel.myMinesClicked[X + 1][Y + 1] == 2)) {
                    MinePanel.myMines[X + 1][Y + 1].extendCilck();
                }
            }
            //continue extend
        }
    }

    public static void setIcon() {
        myNumber = new ImageIcon[9];
        myNumber[0] = new ImageIcon(Mine.class.getResource("../pic/num/0.png"));
        myNumber[1] = new ImageIcon(Mine.class.getResource("../pic/num/1.png"));
        myNumber[2] = new ImageIcon(Mine.class.getResource("../pic/num/2.png"));
        myNumber[3] = new ImageIcon(Mine.class.getResource("../pic/num/3.png"));
        myNumber[4] = new ImageIcon(Mine.class.getResource("../pic/num/4.png"));
        myNumber[5] = new ImageIcon(Mine.class.getResource("../pic/num/5.png"));
        myNumber[6] = new ImageIcon(Mine.class.getResource("../pic/num/6.png"));
        myNumber[7] = new ImageIcon(Mine.class.getResource("../pic/num/7.png"));
        myNumber[8] = new ImageIcon(Mine.class.getResource("../pic/num/8.png"));
        myMineIcon = new ImageIcon(Mine.class.getResource("../pic/mine.png"));
        mySmileIcon = new ImageIcon(Mine.class.getResource("../pic/smile.png"));
        myOOIcon = new ImageIcon(Mine.class.getResource("../pic/oo.png"));
        myTimeIcon = new ImageIcon(Mine.class.getResource("../pic/time.png"));
        myFlagIcon = new ImageIcon(Mine.class.getResource("../pic/flag.png"));
        myUnsureIcon = new ImageIcon(Mine.class.getResource("../pic/unsure.png"));
        myWrongFlagIcon = new ImageIcon(Mine.class.getResource("../pic/wrongFlag.png"));
    }
}
