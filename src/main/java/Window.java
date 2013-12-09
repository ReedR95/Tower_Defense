import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

public class Window extends JPanel {

    public static Image[] ground_tile = new Image[100];
    public static Image[] air_tile = new Image[100];
    public static Image[] res_tile = new Image[100];
    public static Image[] enemy_tile = new Image[100];

    public static Point mouse = new Point(0, 0);

    public static int myWidth, myHeight;
    public static int money = 20, health = 10;
    public static int killed = 0, killsToWin = 0, level = 1, maxLevel = 3;
    public static int winTime = 2000, winFrame = 0;
    public static int walkSpeed = 30;
    public static int wave = 0;

    public static boolean isFirst = true;
    public static boolean isDebug = false;
    public static boolean isWon = false;

    public static Room room;
    public static Save save;
    public static Store store;

    public static Enemy[] enemies = new Enemy[100];

    public Window(Frame frame) {

        this.addMouseListener(new KeyHandler());
        this.addMouseMotionListener(new KeyHandler());

        Timer timer = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tick();
            }
        });
        timer.start();
    }

    public static void hasWon() {
        if (killed == killsToWin) {
            isWon = true;
            killed = 0;
            money = 0;
        }
    }

    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();

        money = 20;
        health = 10;

        for (int i = 0; i < ground_tile.length; i++) {
            ground_tile[i] = new ImageIcon("resources/ground_tile.png").getImage();
            ground_tile[i] = createImage(new FilteredImageSource(ground_tile[i].getSource(),
                                         new CropImageFilter(0, 26 * i, 26, 26)));
        }

        for (int i = 0; i < air_tile.length; i++) {
            air_tile[i] = new ImageIcon("resources/air_tile.png").getImage();
            air_tile[i] = createImage(new FilteredImageSource(air_tile[i].getSource(),
                    new CropImageFilter(0, 26 * i, 26, 26)));
        }

        res_tile[0] = new ImageIcon("resources/cell.png").getImage();
        res_tile[1] = new ImageIcon("resources/heart.png").getImage();
        res_tile[2] = new ImageIcon("resources/coin.png").getImage();

        enemy_tile[0] = new ImageIcon("resources/enemy.png").getImage();

        save.loadSave(new File("save/level" + level + ".data"));

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();

            define();

            isFirst = false;
        }

        g.setColor(new Color(70, 70, 70));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0, 0, 0));
        g.drawLine(room.block[0][0].x - 1,
                   0,
                   room.block[0][0].x - 1,
                   room.block[room.worldHeight-1][0].y + room.blockSize); //left line

        g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize + 1,
                   0,
                   room.block[0][room.worldWidth-1].x + room.blockSize + 1,
                   room.block[room.worldHeight-1][0].y + room.blockSize); //right line

        g.drawLine(room.block[0][0].x - 1,
                   room.block[room.worldHeight-1][0].y + room.blockSize + 1,
                   room.block[0][room.worldWidth-1].x + room.blockSize + 1,
                   room.block[room.worldHeight-1][0].y + room.blockSize + 1); //bottom line

        room.draw(g);

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].inGame) {
                enemies[i].draw(g);
            }
        }

        store.draw(g);

        if (health < 1) {
            g.setColor(new Color(240, 20, 20));
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("Game Over", 10, 20);
        }

        if (isWon) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            if (level == maxLevel) {
                g.drawString("You won the whole game! Please wait and the window will close", 10, 20);
            } else {
                g.drawString("You won! Next level loading...", 10, 20);
            }
        }
    }

    public int spawnTime = 2400, spawnFrame = 0;
    public void enemySpawner() {
        if (spawnFrame >= spawnTime) {
            for (int i = 0; i < enemies.length; i++) {
                if (!enemies[i].inGame) {
                    enemies[i].spawnEnemy(Value.enemyGreen);

                    enemies[i].walkSpeed = walkSpeed;
                    break;
                }
            }

            spawnFrame = 0;
        } else {
            spawnFrame++;
        }
    }

    public void tick() {
        if (!isFirst && health > 0 && !isWon) {
            room.physic();
            enemySpawner();

            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].inGame) {
                    enemies[i].move();
                }
            }
        } else {
            if (isWon) {
                if(winFrame >= winTime) {
                    if (level == maxLevel) {
                        System.exit(0);
                    } else {
                        level++;
                        define();
                        isWon = false;
                    }

                    winFrame = 0;
                } else {
                    winFrame +=1;
                }
            }
        }

        repaint();
    }

}
