package snakepackage;

import enums.Direction;
import enums.GridSize;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

public class Snake extends Observable implements Runnable {

  private volatile boolean paused = false;
  private int idt;
  private Cell head;
  private Cell newCell;
  private LinkedList<Cell> snakeBody = new LinkedList<Cell>();
  //private Cell objective = null;
  private Cell start = null;

  private boolean snakeEnd = false;

  private int direction = Direction.NO_DIRECTION;
  private final int INIT_SIZE = 3;

  private boolean hasTurbo = false;
  private int jumps = 0;
  private boolean isSelected = false;
  private int growing = 0;
  public boolean goal = false;

  public Snake(int idt, Cell head, int direction) {
    this.idt = idt;
    this.direction = direction;
    generateSnake(head);
  }

  public boolean isSnakeEnd() {
    return snakeEnd;
  }

  private void generateSnake(Cell head) {
    start = head;
    //Board.gameboard[head.getX()][head.getY()].reserveCell(jumps, idt);
    snakeBody.add(head);
    growing = INIT_SIZE - 1;
  }

  private synchronized void snakeCalc() {
    head = snakeBody.peekFirst();
    newCell = head;
    newCell = changeDirection(newCell);
    randomMovement(newCell);
    checkIfFood(newCell);
    checkIfJumpPad(newCell);
    checkIfTurboBoost(newCell);
    checkIfBarrier(newCell);
    snakeBody.push(newCell);
    if (growing <= 0) {
      newCell = snakeBody.peekLast();
      snakeBody.remove(snakeBody.peekLast());
      Board.getCell(newCell.getX(), newCell.getY()).freeCell();
    } else if (growing != 0) {
      growing--;
    }
  }

  private void checkIfBarrier(Cell newCell) {
    if (Board.getCell(newCell.getX(), newCell.getY()).isBarrier()) {
      // crash
      System.out.println(
        "[" + idt + "] " + "CRASHED AGAINST BARRIER " + newCell.toString()
      );
      snakeEnd = true;
    }
  }

  private Cell fixDirection(Cell newCell) {
    // revert movement
    if (direction == Direction.LEFT && head.getX() + 1 < GridSize.GRID_WIDTH) {
      newCell = Board.getCell(head.getX() + 1, head.getY());
    } else if (direction == Direction.RIGHT && head.getX() - 1 >= 0) {
      newCell = Board.getCell(head.getX() - 1, head.getY());
    } else if (
      direction == Direction.UP && head.getY() + 1 < GridSize.GRID_HEIGHT
    ) {
      newCell = Board.getCell(head.getX(), head.getY() + 1);
    } else if (direction == Direction.DOWN && head.getY() - 1 >= 0) {
      newCell = Board.getCell(head.getX(), head.getY() - 1);
    }
    randomMovement(newCell);
    return newCell;
  }

  private boolean checkIfOwnBody(Cell newCell) {
    for (Cell c : snakeBody) {
      if (newCell.getX() == c.getX() && newCell.getY() == c.getY()) {
        return true;
      }
    }
    return false;
  }

  private void randomMovement(Cell newCell) {
    Random random = new Random();
    int tmp = random.nextInt(4) + 1;
    if (tmp == Direction.LEFT && !(direction == Direction.RIGHT)) {
      direction = tmp;
    } else if (tmp == Direction.UP && !(direction == Direction.DOWN)) {
      direction = tmp;
    } else if (tmp == Direction.DOWN && !(direction == Direction.UP)) {
      direction = tmp;
    } else if (tmp == Direction.RIGHT && !(direction == Direction.LEFT)) {
      direction = tmp;
    }
  }

  private void checkIfTurboBoost(Cell newCell) {
    if (Board.getCell(newCell.getX(), newCell.getY()).isTurbo_boost()) {
      // get turbo_boost
      for (int i = 0; i != Board.NR_TURBO_BOOSTS; i++) {
        if (Board.turbo_boosts[i] == newCell) {
          Board.turbo_boosts[i].setTurbo_boost(false);
        }
      }
      System.out.println(
        "[" + idt + "] " + "GETTING TURBO BOOST " + newCell.toString()
      );
    }
  }

  private void checkIfJumpPad(Cell newCell) {
    if (Board.getCell(newCell.getX(), newCell.getY()).isJump_pad()) {
      // get jump_pad
      for (int i = 0; i != Board.NR_JUMP_PADS; i++) {
        if (Board.jump_pads[i] == newCell) {
          Board.jump_pads[i].setJump_pad(false);
        }
      }
      System.out.println(
        "[" + idt + "] " + "GETTING JUMP PAD " + newCell.toString()
      );
    }
  }

  private void checkIfFood(Cell newCell) {
    Random random = new Random();
    if (Board.getCell(newCell.getX(), newCell.getY()).isFood()) {
      // eat food
      growing += 3;
      int x = random.nextInt(GridSize.GRID_HEIGHT);
      int y = random.nextInt(GridSize.GRID_WIDTH);
      System.out.println("[" + idt + "] " + "EATING " + newCell.toString());
      for (int i = 0; i != Board.NR_FOOD; i++) {
        if (
          Board.food[i].getX() == newCell.getX() &&
          Board.food[i].getY() == newCell.getY()
        ) {
          Board.food[i].setFood(false);
        }
      }
    }
  }

  private Cell changeDirection(Cell newCell) {
    // Avoid out of bounds
    while (direction == Direction.UP && (newCell.getY() - 1) < 0) {
      if ((head.getX() - 1) < 0) {
        this.direction = Direction.RIGHT;
      } else if ((head.getX() + 1) == GridSize.GRID_WIDTH) {
        this.direction = Direction.LEFT;
      } else {
        this.direction = Direction.DOWN;
      }
    }
    while (
      direction == Direction.DOWN && (head.getY() + 1) == GridSize.GRID_HEIGHT
    ) {
      if ((head.getX() - 1) < 0) {
        this.direction = Direction.RIGHT;
      } else if ((head.getX() + 1) == GridSize.GRID_WIDTH) {
        this.direction = Direction.LEFT;
      } else {
        this.direction = Direction.UP;
      }
    }
    while (direction == Direction.LEFT && (head.getX() - 1) < 0) {
      if ((newCell.getY() - 1) < 0) {
        this.direction = Direction.DOWN;
      } else {
        this.direction = Direction.UP;
      }
    }
    while (
      direction == Direction.RIGHT && (head.getX() + 1) == GridSize.GRID_WIDTH
    ) {
      if ((newCell.getY() - 1) < 0) {
        this.direction = Direction.DOWN;
      } else {
        this.direction = Direction.UP;
      }
    }
    switch (direction) {
      case Direction.UP:
        newCell = Board.getCell(head.getX(), head.getY() - 1);
        break;
      case Direction.DOWN:
        newCell = Board.getCell(head.getX(), head.getY() + 1);
        break;
      case Direction.LEFT:
        newCell = Board.getCell(head.getX() - 1, head.getY());
        break;
      case Direction.RIGHT:
        newCell = Board.getCell(head.getX() + 1, head.getY());
        break;
    }
    return newCell;
  }

  public LinkedList<Cell> getBody() {
    return this.snakeBody;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public int getIdt() {
    return idt;
  }

  public synchronized void pause() {
    paused = true;
  }

  public synchronized void resume() {
    paused = false;
    notify(); // Desbloquea el hilo si estaba esperando
  }

  public synchronized void suspend() {
    paused = true;
  }

  @Override
  public void run() {
    while (!snakeEnd) {
      synchronized (this) {
        while (paused) {
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

      snakeCalc();
      setChanged();
      notifyObservers();

      try {
        Thread.sleep(hasTurbo ? 100 : 200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    fixDirection(head);
  }
}
