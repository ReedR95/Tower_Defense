import java.awt.*;

public class Enemy extends Rectangle {

    public int xC, yC;
    public int health;
    public int healthSpace = 3;
    public int healthHeight = 6;
    public int size = 52;
    public int enemyWalk = 0;
    public int upward = 0, downward = 1, right = 2, left = 3;
    public int walkFrame = 0, walkSpeed = 30;
    public int direction = right;
    public int enemyId = Value.enemyAir;
    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasLeft = false;
    public boolean hasRight = false;

    public Enemy() {

    }

    public void spawnEnemy(int enemyId) {
        for (int y = 0; y < Window.room.block.length; y++) {
            if (Window.room.block[y][0].groundID == Value.groundRoad) {
                setBounds(Window.room.block[y][0].x, Window.room.block[y][0].y, size, size);
                xC = 0;
                yC = y;
            }
        }

        this.enemyId = enemyId;
        this.health = size;

        inGame = true;
    }

    public void deleteEnemy() {
        inGame = false;
        direction = right;
        enemyWalk = 0;

        Window.room.block[0][0].getMoney(enemyId);
    }

    public void loseHealth() {
        Window.health--;
    }

    public void move() {
        if (walkFrame >= walkSpeed) {
            if (direction == right) { x++; }
            else if (direction == upward) { y--; }
            else if (direction == downward) { y++; }
            else if (direction == left) { x--; }

            enemyWalk++;

            if (enemyWalk == Window.room.blockSize) {
                if (direction == right) {
                    xC++;
                    hasRight = true;
                }
                else if (direction == upward) {
                    yC--;
                    hasUpward = true;
                }
                else if (direction == downward) {
                    yC++;
                    hasDownward = true;
                } else if (direction == left) {
                    xC--;
                    hasLeft = true;
                }

                if (!hasUpward) {
                    try {
                        if (Window.room.block[yC + 1][xC].groundID == Value.groundRoad) {
                            direction = downward;
                        }
                    } catch(Exception e) {}
                }

                if (!hasDownward) {
                    try {
                        if (Window.room.block[yC - 1][xC].groundID == Value.groundRoad) {
                            direction = upward;
                        }
                    } catch(Exception e) {} }


                if (!hasLeft) {
                    try {
                        if (Window.room.block[yC][xC + 1].groundID == Value.groundRoad) {
                            direction = right;
                        }
                    } catch(Exception e) {}
                }

                if (!hasRight) {
                    try {
                        if (Window.room.block[yC][xC - 1].groundID == Value.groundRoad) {
                            direction = left;
                        }
                    } catch(Exception e) {}
                }


                if (Window.room.block[yC][xC].airID == Value.airCave) {
                    deleteEnemy();
                    loseHealth();
                }

                hasUpward = false;
                hasDownward = false;
                hasRight = false;
                hasLeft = false;
                enemyWalk = 0;
            }

            walkFrame = 0;
        } else {
            walkFrame++;
        }
    }

    public void loseHealth(int x) {
        health -= x;
        checkDeath();
    }

    public void checkDeath() {
        if (health < 1) {
            deleteEnemy();
        }
    }

    public boolean isDead() {
        if(inGame) {
            return false;
        } else {
            return true;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(Window.enemy_tile[enemyId], x, y, width, height, null);

        //Health Bar
        g.setColor(new Color(180, 50, 50));
        g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

        g.setColor(new Color(50, 180, 50));
        g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);

        g.setColor(new Color(0, 0, 0));
        g.drawRect(x, y - (healthSpace + healthHeight), health -1, healthHeight - 1);
    }

}
