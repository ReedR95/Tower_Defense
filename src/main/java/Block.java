import java.awt.*;

public class Block extends Rectangle {
    public Rectangle towerSquare;
    public int towerSquareSize = 130;
    public int groundID;
    public int airID;
    public int loseTime = 100, loseFrame = 0;

    public int shotEnemy = -1;
    public boolean isShooting = false;

    public Block(int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x, y, width, height);
        towerSquare = new Rectangle(x - towerSquareSize/2, y - towerSquareSize/2, width + towerSquareSize, height + towerSquareSize);
        this.groundID = groundID;
        this.airID = airID;
    }

    public void draw(Graphics g) {
        g.drawImage(Window.ground_tile[groundID], x, y, width, height, null);

        if (airID != Value.airAir) {
            g.drawImage(Window.air_tile[airID], x, y, width, height, null);
        }
    }

    public void physics() {
        if (shotEnemy != -1 && towerSquare.intersects(Window.enemies[shotEnemy])) {
            isShooting = true;
        } else {
            isShooting = false;
        }

        if (!isShooting) {
            if (airID == Value.airTowerLaser || airID == Value.airTowerLaser2) {
                for (int i = 0; i < Window.enemies.length; i++) {
                    if (Window.enemies[i].inGame) {
                        if (towerSquare.intersects(Window.enemies[i])) {
                            isShooting = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
        }

        if (isShooting) {
            int healthLoss = 1;
            if (airID == Value.airTowerLaser2) {
                healthLoss = 2;
            }

            if (loseFrame >= loseTime) {
                Window.enemies[shotEnemy].loseHealth(healthLoss);

                loseFrame = 0;
            } else {
                loseFrame++;
            }

            if (Window.enemies[shotEnemy].isDead()) {

                isShooting = false;
                shotEnemy = -1;

                Window.killed++;

                Window.hasWon();
            }
        }

    }

    public void getMoney(int enemyId) {
        Window.money += Value.deathReward[enemyId];
    }

    public void attack(Graphics g) {

        Color laserColor = new Color(255, 255, 0);
        if (airID == Value.airTowerLaser2) {
            laserColor = new Color(255, 0, 2);
        }

        if (Window.isDebug) {
            if (airID == Value.airTowerLaser) {
                g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
            }
        }

        if (isShooting) {
            g.setColor(laserColor);
            g.drawLine(x + width/2, y + height/2,
                    Window.enemies[shotEnemy].x + (Window.enemies[shotEnemy].width/2),
                    Window.enemies[shotEnemy].y + (Window.enemies[shotEnemy].height/2));
        }
    }

}
