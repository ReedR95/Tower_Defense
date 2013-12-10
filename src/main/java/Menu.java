import com.google.bitcoin.core.Utils;
import com.google.bitcoin.core.Wallet;
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
import java.math.BigInteger;

public class Menu extends JPanel {

    public Menu(LayoutManager lm) {
        super(lm);

        setFocusable(true);

        final JButton start = new JButton("Play for free");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(Frame.cards.getLayout());
                cl.show(Frame.cards, "GAME");

                Frame.bitcoin.stopAndWait();
            }
        });

        final byte[] imageBytes = QRCode
                .from(uri())
                .withSize(250, 250)
                .to(ImageType.PNG)
                .stream()
                .toByteArray();

        BufferedImage qrImage;

        final BigInteger amount = Frame.bitcoin.wallet().getBalance(Wallet.BalanceType.ESTIMATED);
        String balance = "This game has earned " + Utils.bitcoinValueToFriendlyString(amount) + " btc";
        JLabel balanceLabel = new JLabel(balance);
        balanceLabel.setFont(new Font("Courier New", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(255, 255, 255));

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "\nPress \"Play for free\" or send some bitcoins using the " +
                        "qrcode to start the game! \n\nStrategically place towers in order to ward off " +
                        "enemies. \nEach time an enemy enters the cave at the end of the path you lose " +
                        "one unit of health. \nDon't let your health reach zero! \nYou can only place " +
                        "towers if you have enough money. \nEach time you kill an enemy you get more money." +
                        "\n\nFeatures:\n" +
                        "- Path detection\n" +
                        "- Multiple tower types\n" +
                        "- Easy level creation\n" +
                        "- Drag and drop\n" +
                        "- Health Bar\n" +
                        "- BitcoinJ Integration");
            }
        });

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
            qrImage = ImageIO.read(in);
            JLabel picLabel = new JLabel(new ImageIcon(qrImage));

            start.setAlignmentX(Component.CENTER_ALIGNMENT);
            start.setMaximumSize(instructions.getMinimumSize());
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel menu = new JPanel();
            menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
            menu.setBackground(new Color(45, 42, 58));
            menu.add(instructions);
            menu.add(start);
            menu.add(Box.createRigidArea(new Dimension(0,40)));
            menu.add(picLabel);

            this.add(balanceLabel, BorderLayout.PAGE_START);
            this.add(menu, BorderLayout.SOUTH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public String uri() {
        return BitcoinURI.convertToBitcoinURI(Frame.address, null, Frame.APP_NAME, null);
    }

    public void paintComponent(Graphics g) {
        Image bg = new ImageIcon("resources/menu.png").getImage();
        g.drawImage(bg, 0, 0, null); //no need for ImageObserver here
    }


}
