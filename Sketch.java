import processing.core.PApplet;

public class Sketch extends PApplet {
	
	
  int numSnowflakes = 100;
  float[] snowflakesX;
  float[] snowflakesY;
  float[] snowflakesSpeed;

  float playerX;
  float playerY;
  int playerLives = 3;

  boolean[] ballHideStatus;

  public void settings() {
	// put your size call here
    size(400, 400);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(210, 255, 173);
    snowflakesX = new float[numSnowflakes];
        snowflakesY = new float[numSnowflakes];
        snowflakesSpeed = new float[numSnowflakes];
        ballHideStatus = new boolean[numSnowflakes];

        for (int i = 0; i < numSnowflakes; i++) {
            snowflakesX[i] = random(width);
            snowflakesY[i] = random(height);
            snowflakesSpeed[i] = random(1, 3);
            ballHideStatus[i] = false;
          }

          playerX = width / 2;
          playerY = height - 50;

        }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(255);

        moveSnowflakes();
        drawSnowflakes();

        movePlayer();
        drawPlayer();

        drawLives();
  }

  private void drawLives() {
    fill(255, 0, 0);
    for (int i = 0; i < playerLives; i++) {
    rect(width - 30 - i * 40, 10, 30, 30);
    }
  }

  private void drawPlayer() {
    fill(0, 0, 255);
    ellipse(playerX, playerY, 30, 30);
  }

  private void movePlayer() {
    if (keyPressed) {
      if (keyCode == LEFT) {
          playerX -= 5;
      } else if (keyCode == RIGHT) {
          playerX += 5;
      }
  }
    playerX = constrain(playerX, 0, width - 1);
  }

  private void drawSnowflakes() {
    fill(255);
        stroke(0);

        for (int i = 0; i < numSnowflakes; i++) {
            if (!ballHideStatus[i]) {
                ellipse(snowflakesX[i], snowflakesY[i], 10, 10);
            }
        }
  }

  private void moveSnowflakes() {
    for (int i = 0; i < numSnowflakes; i++) {
      snowflakesY[i] += snowflakesSpeed[i];

      // Wrap snowflakes around the screen
      if (snowflakesY[i] > height) {
          snowflakesY[i] = 0;
          snowflakesX[i] = random(width);
      }

      // Check for collision with player
      if (dist(playerX, playerY, snowflakesX[i], snowflakesY[i]) < 15 && !ballHideStatus[i]) {
          ballHideStatus[i] = true;
          playerLives--;

          // Check if player is out of lives
          if (playerLives <= 0) {
              gameOver();
          }
      }
  }
  }

  private void gameOver() {
    background(255);
        textSize(36);
        fill(0);
        text("Game Over", width / 2, height / 2);
  }
  
  public void mousePressed() {
    for (int i = 0; i < numSnowflakes; i++) {
        if (dist(mouseX, mouseY, snowflakesX[i], snowflakesY[i]) < 15) {
            ballHideStatus[i] = true;
        }
    }
  }
}