package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class Minesweeper extends JFrame {

    public static Minesweeper myMinesweeper;
    public static MinePanel myMinePanel;

    public JButton newGameBt;
    public ImageIcon newGameBtSmile, newGameBtOO;

    public Timer timer;
    public boolean timerRunning = false;

    public int showTime;
    public JLabel myLabel[];

    private int myWidth = 9;
    private int myHeight = 9;
    private int myMine = 10;
    public int myMineCount = 10;

    Minesweeper(String name) {
        super(name);
        this.setLayout(null);
        timer = null;
        showTime = 0;

        newGameBt = new JButton();
        newGameBtSmile = new ImageIcon(this.getClass().getResource("../pic/smile.png"));
        newGameBtOO = new ImageIcon(this.getClass().getResource("../pic/oo.png"));
        newGameBt.setLocation(myWidth * 10 + 20, 5);
        newGameBt.setSize(20, 20);
        newGameBt.setIcon(newGameBtSmile);
        newGameBt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Minesweeper.myMinesweeper.newGameBt.setIcon(Minesweeper.myMinesweeper.newGameBtSmile);
                Minesweeper.myMinesweeper.restart();
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
        newGameBt.setFocusPainted(false);
        newGameBt.setContentAreaFilled(false);
        newGameBt.setOpaque(false);
        newGameBt.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.getContentPane().add(newGameBt);

        myLabel = new JLabel[4];
        myLabel[0] = new JLabel(new ImageIcon(this.getClass().getResource("../pic/time.png")));
        myLabel[1] = new JLabel("" + showTime);
        myLabel[2] = new JLabel("" + myMineCount);
        myLabel[3] = new JLabel(new ImageIcon(this.getClass().getResource("../pic/mine.png")));
        myLabel[1].setFont(new Font("微软雅黑", Font.PLAIN, 18));
        myLabel[2].setFont(new Font("微软雅黑", Font.PLAIN, 18));
        myLabel[0].setSize(20, 20);
        myLabel[1].setSize((myWidth * 20 - 48) / 2, 20);
        myLabel[2].setSize((myWidth * 20 - 48) / 2, 20);
        myLabel[3].setSize(20, 20);
        myLabel[0].setLocation(30, 36 + myHeight * 20);
        myLabel[1].setLocation(54, 36 + myHeight * 20);
        myLabel[2].setLocation(31 + myWidth * 10, 36 + myHeight * 20);
        myLabel[3].setLocation(11 + myWidth * 20, 36 + myHeight * 20);
        myLabel[1].setHorizontalAlignment(SwingConstants.LEFT);
        myLabel[2].setHorizontalAlignment(SwingConstants.RIGHT);
        this.getContentPane().add(myLabel[0]);
        this.getContentPane().add(myLabel[1]);
        this.getContentPane().add(myLabel[2]);
        this.getContentPane().add(myLabel[3]);

        this.setJMenuBar(getMyJMenuBar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mine.setIcon();
        myMinePanel = new MinePanel(myWidth, myHeight, myMine);
        this.setSize(myMinePanel.getSize().width + 67, myMinePanel.getSize().height + 113);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((displaySize.width - this.getSize().width) / 2, (displaySize.height - this.getSize().height) / 2);
        this.getContentPane().add(myMinePanel);
        this.getContentPane().setBackground(new Color(0xdd, 0xdd, 0xee));
    }

    public Minesweeper setProperty(int width, int height, int mine) {
        myWidth = width;
        myHeight = height;
        myMine = mine;
        myMineCount = mine;
        return this;
    }

    public void restart() {
        this.setVisible(false);
        if (timerRunning) {
            timer.cancel();
            timerRunning = false;
        }
        
        newGameBt.setLocation(myWidth * 10 + 20, 5);
        
        myLabel[1].setSize((myWidth * 20 - 48) / 2, 20);
        myLabel[2].setSize((myWidth * 20 - 48) / 2, 20);
        myLabel[0].setLocation(30, 36 + myHeight * 20);
        myLabel[1].setLocation(54, 36 + myHeight * 20);
        myLabel[2].setLocation(31 + myWidth * 10, 36 + myHeight * 20);
        myLabel[3].setLocation(11 + myWidth * 20, 36 + myHeight * 20);
        timer = null;
        showTime = 0;
        myMineCount = myMine;
        myLabel[1].setText("" + showTime);
        myLabel[2].setText("" + myMineCount);
        this.remove(myMinePanel);
        myMinePanel = new MinePanel(myWidth, myHeight, myMine);
        this.setSize(myMinePanel.getSize().width + 67, myMinePanel.getSize().height + 113);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((displaySize.width - this.getSize().width) / 2, (displaySize.height - this.getSize().height) / 2);
        this.getContentPane().add(myMinePanel);
        this.setVisible(true);
    }

    private JMenuBar getMyJMenuBar() {
        JMenuBar myJMenuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem newGame, item9multi9, item16multi16, item16multi40, exit;
        newGame = new JMenuItem("new");
        item9multi9 = new JMenuItem("  9*  9");
        item16multi16 = new JMenuItem("16*16");
        item16multi40 = new JMenuItem("16*30");
        exit = new JMenuItem("exit");

        MenuListener myMenuListener = new MenuListener();
        newGame.addActionListener(myMenuListener);
        item9multi9.addActionListener(myMenuListener);
        item16multi16.addActionListener(myMenuListener);
        item16multi40.addActionListener(myMenuListener);
        exit.addActionListener(myMenuListener);

        game.add(newGame);
        game.addSeparator();
        game.add(item9multi9);
        game.add(item16multi16);
        game.add(item16multi40);
        game.addSeparator();
        game.add(exit);

        myJMenuBar.add(game);

        return myJMenuBar;
    }

    public static void main(String[] args) {
        myMinesweeper = new Minesweeper("Minesweeper");
        myMinesweeper.setVisible(true);
        myMinesweeper.setResizable(false);
    }
}

class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "exit":
                System.exit(0); //如果选择了exit 菜单项，退出程序
            case "new":
                Minesweeper.myMinesweeper.restart();
                break;
            case "  9*  9":
                Minesweeper.myMinesweeper.setProperty(9, 9, 10).restart();
                break;
            case "16*16":
                Minesweeper.myMinesweeper.setProperty(16, 16, 40).restart();
                break;
            case "16*30":
                Minesweeper.myMinesweeper.setProperty(30, 16, 99).restart();
                break;
            default:
                break;
        }
    }
}

class myTask extends TimerTask {

    @Override
    public void run() {
        Minesweeper.myMinesweeper.myLabel[1].setText("" + (++Minesweeper.myMinesweeper.showTime));
    }

}
