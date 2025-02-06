package snakepackage;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakeApp {

  private static SnakeApp app;
  public static final int MAX_THREADS = 8;
  public final Snake[] snakes = new Snake[MAX_THREADS];
  public final Thread[] threads = new Thread[MAX_THREADS];

  private static final Cell[] spawn = {
    new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
    new Cell(GridSize.GRID_WIDTH - 2, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
    new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
    new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
    new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
    new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
    new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
    new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
  };

  private JFrame frame;
  private static Board board;
  private JButton startButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JLabel longestSnakeLabel;
  private JLabel worstSnakeLabel;

  public SnakeApp() {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    frame = new JFrame("The Snake Race");
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(
      GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
      GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40
    );
    frame.setLocation(
      dimension.width / 2 - frame.getWidth() / 2,
      dimension.height / 2 - frame.getHeight() / 2
    );
    board = new Board();
    frame.add(board, BorderLayout.CENTER);

    JPanel actionsPanel = new JPanel();
    actionsPanel.setLayout(new FlowLayout());
    startButton = new JButton("Start");
    pauseButton = new JButton("Pause");
    resumeButton = new JButton("Resume");
    longestSnakeLabel = new JLabel("Longest Snake: ");
    worstSnakeLabel = new JLabel("Worst Snake: ");
    actionsPanel.add(startButton);
    actionsPanel.add(pauseButton);
    actionsPanel.add(resumeButton);
    actionsPanel.add(longestSnakeLabel);
    actionsPanel.add(worstSnakeLabel);
    frame.add(actionsPanel, BorderLayout.SOUTH);

    startButton.addActionListener(e -> startGame());
    pauseButton.addActionListener(e -> pauseGame());
    resumeButton.addActionListener(e -> resumeGame());
  }

  public List<Snake> getSnakes() {
    return new ArrayList<>(Arrays.asList(snakes)); // Crea una nueva lista a partir del array
  }

  private synchronized void startGame() {
    if (app == null) {
      app = new SnakeApp();
      app.init();
    }
  }

  private synchronized void pauseGame() {
    for (Snake s : snakes) {
      s.suspend();
    }
    updateLabels();
  }

  private synchronized void resumeGame() {
    for (Snake s : snakes) {
      s.resume();
    }
  }

  private void updateLabels() {
    Snake longestSnake = null;
    Snake worstSnake = null;
    for (Snake s : snakes) {
      if (longestSnake == null || s.getIdt() > longestSnake.getIdt()) {
        longestSnake = s;
      }
      if (worstSnake == null || s.isSnakeEnd()) {
        worstSnake = s;
      }
    }
    longestSnakeLabel.setText(
      "Longest Snake: " + (longestSnake != null ? longestSnake.getIdt() : "N/A")
    );
    worstSnakeLabel.setText(
      "Worst Snake: " + (worstSnake != null ? worstSnake.getIdt() : "N/A")
    );
  }

  public static void main(String[] args) {
    app = new SnakeApp();
    app.init();
  }

  private synchronized void init() {
    for (int i = 0; i < MAX_THREADS; i++) {
      snakes[i] = new Snake(i + 1, spawn[i], i + 1);
      snakes[i].addObserver(board);
      threads[i] = new Thread(snakes[i]);
      threads[i].start();
    }
    frame.setVisible(true);

    while (true) {
      int finished = 0;
      for (Snake s : snakes) {
        if (s.isSnakeEnd()) {
          finished++;
        }
      }
      if (finished == MAX_THREADS) {
        break;
      }
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.println("Thread (snake) status:");
    for (int i = 0; i < MAX_THREADS; i++) {
      System.out.println("[" + i + "] :" + threads[i].getState());
    }
  }

  public static SnakeApp getApp() {
    return app;
  }
}
