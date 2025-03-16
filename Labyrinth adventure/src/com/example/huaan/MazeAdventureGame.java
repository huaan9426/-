package com.example.huaan;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazeAdventureGame extends JFrame {
    private Maze maze;
    private JTextPane mazeTextPane;
    private JTextArea instructionTextArea;
    private int stepCount; // 新增：用于记录步数的变量

    public MazeAdventureGame() {
        maze = new Maze(10, 10);
        stepCount = 0; // 初始化步数为0
        setTitle("Maze Adventure Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JPanel gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setBackground(Color.BLACK);

        mazeTextPane = new JTextPane();
        mazeTextPane.setEditable(false);
        mazeTextPane.setOpaque(false);
        mazeTextPane.setMargin(new Insets(0, 0, 0, 0));
        mazeTextPane.setBorder(null);
        mazeTextPane.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        updateMazeDisplay();
        gamePanel.add(mazeTextPane, gbc);

        instructionTextArea = new JTextArea();
        instructionTextArea.setEditable(false);
        instructionTextArea.setBackground(Color.BLACK);
        instructionTextArea.setForeground(Color.WHITE);
        instructionTextArea.setText("游戏说明：鼠标点击迷宫任意处开始游戏\n\n" +
                "╔═══ 表示迷宫的上边界\n\n" +
                "╗ 表示迷宫的右上角\n\n" +
                "╠═══ 表示迷宫的中间竖墙（有门时）\n\n" +
                "╚═══ 表示迷宫的下边界（有门时）\n\n" +
                "╝ 表示迷宫的右下角\n\n" +
                "使用方向键（↑↓←→）控制 🐕 移动，找到 🚪 即为胜利。");
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamePanel, instructionTextArea);
        splitPane.setDividerLocation(800);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        mazeTextPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        maze.movePlayer("north");
                        break;
                    case KeyEvent.VK_DOWN:
                        maze.movePlayer("south");
                        break;
                    case KeyEvent.VK_LEFT:
                        maze.movePlayer("west");
                        break;
                    case KeyEvent.VK_RIGHT:
                        maze.movePlayer("east");
                        break;
                }
                stepCount++; // 每次移动后增加步数
                updateMazeDisplay();
                if (maze.isPlayerAtExit()) {
                    JOptionPane.showMessageDialog(null, "牛逼，这你能找到!");
                    System.exit(0);
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    private void updateMazeDisplay() {
        DefaultStyledDocument doc = new DefaultStyledDocument();
        SimpleAttributeSet wallAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(wallAttr, Color.BLUE.darker());

        SimpleAttributeSet pathAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(pathAttr, Color.GREEN.brighter());

        SimpleAttributeSet playerAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(playerAttr, Color.ORANGE);

        SimpleAttributeSet exitAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(exitAttr, Color.RED);

        int exitX = maze.getExitX();
        int exitY = maze.getExitY();

        mazeTextPane.setFont(new Font("Monospaced", Font.PLAIN, 12));

        try {
            for (int y = 0; y < maze.getHeight(); y++) {
                for (int x = 0; x < maze.getWidth(); x++) {
                    appendStyledText(doc, "╔═══", wallAttr);
                }
                appendStyledText(doc, "╗\n", wallAttr);
                for (int x = 0; x < maze.getWidth(); x++) {
                    if (x == maze.getPlayerX() && y == maze.getPlayerY()) {
                        appendStyledText(doc, "🐕  ", playerAttr);
                    } else if (x == exitX && y == exitY) {
                        appendStyledText(doc, "║ 🚪 ", exitAttr);
                    } else {
                        appendStyledText(doc, "    ", pathAttr);
                    }
                }
                appendStyledText(doc, "\n", pathAttr);
                for (int x = 0; x < maze.getWidth(); x++) {
                    if (maze.getRooms()[x][y].isSouth()) {
                        appendStyledText(doc, "╠═══", wallAttr);
                    } else {
                        appendStyledText(doc, "╚═══", wallAttr);
                    }
                }
                appendStyledText(doc, "╝\n", wallAttr);
            }
            // 在迷宫显示下方添加计步器信息
            appendStyledText(doc, "步数: " + stepCount, pathAttr);
            mazeTextPane.setDocument(doc);
            mazeTextPane.revalidate();
            mazeTextPane.repaint();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void appendStyledText(StyledDocument doc, String text, AttributeSet attrs) throws BadLocationException {
        doc.insertString(doc.getLength(), text, attrs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MazeAdventureGame game = new MazeAdventureGame();
            game.setVisible(true);
        });
    }
}
