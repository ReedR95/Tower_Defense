import java.io.File;
import java.util.Scanner;

public class Save {

    public void loadSave(File path) {
        try {
            Scanner loadScanner = new Scanner(path);

            while (loadScanner.hasNext()) {
                Window.killsToWin = loadScanner.nextInt();
                Window.walkSpeed = loadScanner.nextInt();

                for (int y = 0; y < Window.room.block.length; y++) {
                    for (int x = 0; x < Window.room.block[0].length; x++) {
                        Window.room.block[y][x].groundID = loadScanner.nextInt();
                    }
                }

                for (int y = 0; y < Window.room.block.length; y++) {
                    for (int x = 0; x < Window.room.block[0].length; x++) {
                        Window.room.block[y][x].airID = loadScanner.nextInt();
                    }
                }
            }

            loadScanner.close();
        } catch(Exception e) { }
    }

}
