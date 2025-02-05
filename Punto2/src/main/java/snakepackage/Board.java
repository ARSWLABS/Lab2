package snakepackage;

import enums.GridSize;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Board extends JPanel implements Observer {

  private static final long serialVersionUID = 1L;
  public static final int NR_BARRIERS = 5;
  public static final int NR_JUMP_PADS = 2;
  public static final int NR_TURBO_BOOSTS = 2;
  public static final int NR_FOOD = 5;
  static Cell[] food = new Cell[NR_FOOD];
  static Cell[] barriers = new Cell[NR_BARRIERS];
  static Cell[] jump_pads = new Cell[NR_JUMP_PADS];
  static Cell[] turbo_boosts = new Cell[NR_TURBO_BOOSTS];
  static int[] result = new int[SnakeApp.MAX_THREADS];
  Random random = new Random();
  static Cell[][] gameboard = new Cell[GridSize.GRID_WIDTH][GridSize.GRID_HEIGHT];

  private Image foodImage;
  private Image barrierImage;
  private Image jumpPadImage;
  private Image turboBoostImage;

  public Board() {
    try {
      foodImage = ImageIO.read(getClass().getResource("/Img/food.png"));
      barrierImage = ImageIO.read(getClass().getResource("/Img/barrier.png"));
      jumpPadImage = ImageIO.read(getClass().getResource("/Img/jump_pad.png"));
      turboBoostImage =
        ImageIO.read(getClass().getResource("/Img/turbo_boost.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    GenerateBoard();
    GenerateFood();
    GenerateBarriers();
    GenerateJumpPads();
    GenerateTurboBoosts();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawFood(g);
    drawBarriers(g);
    drawJumpPads(g);
    drawTurboBoosts(g);
  }

  private void drawFood(Graphics g) {
    for (Cell cell : food) {
      if (cell != null && cell.isFood() && foodImage != null) {
        g.drawImage(
          foodImage,
          cell.getX() * GridSize.WIDTH_BOX,
          cell.getY() * GridSize.HEIGH_BOX,
          this
        );
      }
    }
  }

  private void drawBarriers(Graphics g) {
    for (Cell cell : barriers) {
      if (cell != null && cell.isBarrier() && barrierImage != null) {
        g.drawImage(
          barrierImage,
          cell.getX() * GridSize.WIDTH_BOX,
          cell.getY() * GridSize.HEIGH_BOX,
          this
        );
      }
    }
  }

  private void drawJumpPads(Graphics g) {
    for (Cell cell : jump_pads) {
      if (cell != null && cell.isJump_pad() && jumpPadImage != null) {
        g.drawImage(
          jumpPadImage,
          cell.getX() * GridSize.WIDTH_BOX,
          cell.getY() * GridSize.HEIGH_BOX,
          this
        );
      }
    }
  }

  private void drawTurboBoosts(Graphics g) {
    for (Cell cell : turbo_boosts) {
      if (cell != null && cell.isTurbo_boost() && turboBoostImage != null) {
        g.drawImage(
          turboBoostImage,
          cell.getX() * GridSize.WIDTH_BOX,
          cell.getY() * GridSize.HEIGH_BOX,
          this
        );
      }
    }
  }

  private void GenerateBoard() {
    for (int i = 0; i < GridSize.GRID_WIDTH; i++) {
      for (int j = 0; j < GridSize.GRID_HEIGHT; j++) {
        gameboard[i][j] = new Cell(i, j);
      }
    }
  }

  private void GenerateFood() {
    for (int i = 0; i != NR_FOOD; i++) {
      Cell tmp =
        gameboard[random.nextInt(GridSize.GRID_WIDTH)][random.nextInt(
            GridSize.GRID_HEIGHT
          )];
      if (!tmp.hasElements()) {
        food[i] = tmp;
        food[i].setFood(true);
      } else {
        i--;
      }
    }
  }

  private void GenerateBarriers() {
    for (int i = 0; i != NR_BARRIERS; i++) {
      Cell tmp =
        gameboard[random.nextInt(GridSize.GRID_WIDTH)][random.nextInt(
            GridSize.GRID_HEIGHT
          )];
      if (!tmp.hasElements()) {
        barriers[i] = tmp;
        barriers[i].setBarrier(true);
      } else {
        i--;
      }
    }
  }

  private void GenerateJumpPads() {
    for (int i = 0; i != NR_JUMP_PADS; i++) {
      Cell tmp =
        gameboard[random.nextInt(GridSize.GRID_WIDTH)][random.nextInt(
            GridSize.GRID_HEIGHT
          )];
      if (!tmp.hasElements()) {
        jump_pads[i] = tmp;
        jump_pads[i].setJump_pad(true);
      } else {
        i--;
      }
    }
  }

  private void GenerateTurboBoosts() {
    for (int i = 0; i != NR_TURBO_BOOSTS; i++) {
      Cell tmp =
        gameboard[random.nextInt(GridSize.GRID_WIDTH)][random.nextInt(
            GridSize.GRID_HEIGHT
          )];
      if (!tmp.hasElements()) {
        turbo_boosts[i] = tmp;
        turbo_boosts[i].setTurbo_boost(true);
      } else {
        i--;
      }
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    repaint();
  }
}
