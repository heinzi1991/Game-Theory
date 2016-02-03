import javax.swing.*;
import java.awt.*;

@SuppressWarnings("ALL")

/*
    Hier wird nur das JSwing Fenster erstellt, um
    die ersten Nimbers in einer Tabelle anzeigen zu lassen
 */
public class NimberCalc extends JFrame {

    private JPanel rootPanel;

    public NimberCalc(GameStates gameStates) {

        super("Nimber Calculate");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[][] showNimber = new String[gameStates.getNimbers().size()][2];

        for (int i = 0; i < gameStates.getNimbers().size(); i++) {

            showNimber[i][0] = Integer.toString(i);
            showNimber[i][1] = "*" + Integer.toString(gameStates.getNimbers().get(i));
        }

        String[] titel = {"Stapelhöhe", "Nimber"};

        JTable table = new JTable(showNimber, titel);
        table.setEnabled(false);
        table.setGridColor(Color.BLACK);

        add(new JScrollPane(table));

        setSize(200, 1000);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = 0;

        setLocation(x, y);

        setVisible(true);
    }
}
