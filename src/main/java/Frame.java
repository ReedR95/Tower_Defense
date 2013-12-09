import com.google.bitcoin.core.*;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.params.RegTestParams;
import com.google.bitcoin.utils.BriefLogFormatter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigInteger;

public class Frame extends JFrame {

    public static String title = "Tower Defense";
    public static Dimension size = new Dimension(700, 550);
    public static JPanel cards;

    public static String APP_NAME = "Tower_Defense";
    public static String address;
    public static NetworkParameters params = MainNetParams.get();
    public static WalletAppKit bitcoin;

    public Frame() {
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BriefLogFormatter.init();

        bitcoin = new WalletAppKit(params, new File("."), APP_NAME) {
            @Override
            protected void onSetupCompleted() {
                if (wallet().getKeychainSize() < 1)
                    wallet().addKey(new ECKey());
            }
        };

        if (params == RegTestParams.get()) {
            bitcoin.connectToLocalHost();
        }
//        else if (params == MainNetParams.get()) {
//            bitcoin.setCheckpoints(getClass().getResourceAsStream("/Developer/CIS 120/Tower_Defense/resources/checkpoints"));
//        }

        bitcoin.startAndWait();

        bitcoin.wallet().allowSpendingUnconfirmedTransactions();
        bitcoin.peerGroup().setMaxConnections(11);
        System.out.println(bitcoin.wallet());

        address = bitcoin.wallet().getKeys().get(0).toAddress(Frame.params).toString();

        bitcoin.wallet().addEventListener(new AbstractWalletEventListener() {
            @Override
            public void onCoinsReceived(Wallet wallet, Transaction tx, BigInteger prevBalance, BigInteger newBalance) {
                System.out.println("Coins Received");
                System.out.println(newBalance);

                CardLayout cl = (CardLayout)(Frame.cards.getLayout());
                cl.show(Frame.cards, "GAME");
            }
        });

        init();
    }

    public void init() {
        setLayout(new GridLayout(1, 1, 0, 0));

        Menu menu = new Menu();
        Window window = new Window(this);

        cards = new JPanel(new CardLayout());
        cards.add(menu, "MENU");
        cards.add(window, "GAME");

        this.add(cards);

        setVisible(true);


    }


    public static void main(String args[]) {
        Frame frame = new Frame();
    }

}
