import com.google.bitcoin.uri.BitcoinURI;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Menu extends JPanel {

    public Menu() {

        setFocusable(true);

        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(Frame.cards.getLayout());
                cl.show(Frame.cards, "GAME");
            }
        });

        final byte[] imageBytes = QRCode
                .from(uri())
                .withSize(320, 240)
                .to(ImageType.PNG)
                .stream()
                .toByteArray();

        BufferedImage qrImage;

        this.add(start);

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
            qrImage = ImageIO.read(in);
            JLabel picLabel = new JLabel(new ImageIcon(qrImage));
            this.add(picLabel);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public String uri() {
        return BitcoinURI.convertToBitcoinURI(Frame.address, null, Frame.APP_NAME, null);
    }


}
