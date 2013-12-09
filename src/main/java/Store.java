import java.awt.*;

public class Store {

    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 4;
    public static int awayFromRoom = 26;
    public static int iconSize = 20;
    public static int iconSpace = 5;
    public static int iconTextY = 15;
    public static int itemIn = 4;
    public static int heldId = -1;
    public static int realId = -1;
    public static int[] buttonId = {Value.airTowerLaser, Value.airTowerLaser2, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airTrashCan};
    public static int[] buttonPrice = {20, 50, 0, 0, 0, 0, 0, 0};

    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle health;
    public Rectangle coins;

    public boolean holdsItem = false;

    public Store() {
        init();
    }

    public void click(int mouseButton) {
        if (mouseButton == 1) {
            for (int i = 0; i < button.length; i++) {
                if (button[i].contains(Window.mouse)) {
                    if (buttonId[i] != Value.airAir){
                        if (buttonId[i] == Value.airTrashCan) {
                            holdsItem = false;
                        } else {
                            heldId = buttonId[i];
                            realId = i;
                            holdsItem = true;
                        }
                    }
                }
            }

            if (holdsItem) {
                if (Window.money >=  buttonPrice[realId]) {
                    for (int y = 0; y < Window.room.block.length; y++) {
                        for (int x = 0; x < Window.room.block[0].length; x++) {
                            if (Window.room.block[y][x].contains(Window.mouse)) {
                                if (Window.room.block[y][x].groundID != Value.groundRoad &&
                                        Window.room.block[y][x].airID == Value.airAir) {
                                    Window.room.block[y][x].airID = heldId;
                                    Window.money -= buttonPrice[realId];
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void init() {
        for (int i = 0; i < button.length; i++) {
            button[i] = new Rectangle(Window.myWidth/2 - ((shopWidth * (buttonSize+cellSpace))/2)
                + ((buttonSize+cellSpace) * i), Window.room.block[Window.room.worldHeight - 1][0].y
                + Window.room.blockSize + cellSpace + awayFromRoom, buttonSize, buttonSize);
        }

        health = new Rectangle(Window.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
        coins = new Rectangle(Window.room.block[0][0].x - 1, button[0].y + button[0].height-iconSize, iconSize, iconSize);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].contains(Window.mouse)) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            g.drawImage(Window.res_tile[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
            if (buttonId[i] != Value.airAir) {
                g.drawImage(Window.air_tile[buttonId[i]], button[i].x + itemIn, button[i].y + itemIn,
                            button[i].width - itemIn * 2, button[i].height - itemIn * 2, null);
            }
            if (buttonPrice[i] > 0) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("$" + buttonPrice[i] + "", button[i].x + itemIn, button[i].y + itemIn + 10);
            }
        }

        g.drawImage(Window.res_tile[1], health.x, health.y, health.width, health.height, null);
        g.drawImage(Window.res_tile[2], coins.x, coins.y, coins.width, coins.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + Window.health, health.x + health.width + iconSpace, health.y + iconTextY);
        g.drawString("" + Window.money, coins.x + coins.width + iconSpace, coins.y + iconTextY);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("" + Window.killed + "/" + Window.killsToWin, 590, 480);

        if (holdsItem) {
            g.drawImage(Window.air_tile[heldId], Window.mouse.x - (button[0].width - itemIn * 2)/2 + itemIn,
                        Window.mouse.y - (button[0].width - itemIn * 2)/2 + itemIn, button[0].width - itemIn * 2,
                        button[0].height - itemIn * 2, null);
        }
    }
}
