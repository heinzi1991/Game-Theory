import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("ALL")

/*
    Hier wir einfach nur ein JSwing Fenster erstellt,
    wo man alle Einstellungen für das Spiel einstellen kann.
    Also wer ist der Startspieler, wieviele Stapel und natürlich
    die Startstapelhöhe
 */

public class GameInfo extends JFrame implements ActionListener, ChangeListener {
    private JPanel rootPanel;
    private JPanel labelPanel;
    private JLabel nameLabel;
    private JPanel playerPanel;
    private JPanel playerLabelPanel;
    private JPanel playerSelectionPanel;
    private JLabel playerLabel;
    private JPanel stackPanel;
    private JPanel stackLabelPanel;
    private JPanel stackSelectionPanel;
    private JLabel stackLabel;
    private JSlider stackSlider;
    private JPanel coinPanel;
    private JPanel coinLabelPanel;
    private JPanel coinSelectionPanel;
    private JLabel coinLabel;
    private JPanel stackOnePanel;
    private JPanel stackFourPanel;
    private JPanel stackThreePanel;
    private JPanel stackTwoPanel;
    private JLabel stackOneLabel;
    private JLabel stackTwoLabel;
    private JLabel stackThreeLabel;
    private JLabel stackFourLabel;
    private JPanel buttonPanel;
    private JButton resetButton;
    private JButton playButton;
    private JFormattedTextField stackOneTextField;
    private JFormattedTextField stackFourTextField;
    private JFormattedTextField stackThreeTextField;
    private JFormattedTextField stackTwoTextField;
    private JPanel selectPlayerPanel;
    private JComboBox playerOneDropDown;
    private JComboBox playerTwoDropDown;
    private JLabel playerOneLabel;
    private JLabel playerTwoLabel;
    private GameStates infoGameStates;

    private HashMap<Integer, String> coinsOnStacks = new HashMap<Integer, String>();

    public GameInfo(GameStates gameStates) {

        super("Game Settings");

        infoGameStates = gameStates;

        add(rootPanel);

        setSize(800, 300);

        playerOneDropDown.addItem("Human");
        playerOneDropDown.addItem("Computer");
        playerTwoDropDown.addItem("Human");
        playerTwoDropDown.addItem("Computer");

        playButton.addActionListener(this);
        resetButton.addActionListener(this);
        stackSlider.addChangeListener(this);


        nameLabel.setText(gameStates.getName());

        stackTwoTextField.setEnabled(false);
        stackThreeTextField.setEnabled(false);
        stackFourTextField.setEnabled(false);


        setVisible(true);



    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == playButton) {


            infoGameStates.setCountOfStacks(stackSlider.getValue());

            if (stackSlider.getValue() == 1) {

                coinsOnStacks.put(1, stackOneTextField.getText());
            }
            else if (stackSlider.getValue() == 2) {

                coinsOnStacks.put(1, stackOneTextField.getText());
                coinsOnStacks.put(2, stackTwoTextField.getText());
            }
            else if (stackSlider.getValue() == 3) {

                coinsOnStacks.put(1, stackOneTextField.getText());
                coinsOnStacks.put(2, stackTwoTextField.getText());
                coinsOnStacks.put(3, stackThreeTextField.getText());
            }
            else {

                coinsOnStacks.put(1, stackOneTextField.getText());
                coinsOnStacks.put(2, stackTwoTextField.getText());
                coinsOnStacks.put(3, stackThreeTextField.getText());
                coinsOnStacks.put(4, stackFourTextField.getText());
            }

            infoGameStates.setCoinsPerStack(coinsOnStacks);

            if (playerOneDropDown.getSelectedItem() == "Computer") {

                infoGameStates.setPlayerOne(0);
                infoGameStates.setPlayerToMove("computer");
            }
            else {

                infoGameStates.setPlayerOne(1);
                infoGameStates.setPlayerToMove("human");
            }

            if (playerTwoDropDown.getSelectedItem() == "Computer") {

                infoGameStates.setPlayerTwo(0);
            }
            else {

                infoGameStates.setPlayerTwo(1);
            }


            infoGameStates.setThePlayer(1);

            PlayGround playfield = new PlayGround(infoGameStates);

            setVisible(false);

            try {

                playfield.playTheGame();

            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }

        if (e.getSource() == resetButton) {

            stackSlider.setValue(1);

            stackOneTextField.setText("");
            stackTwoTextField.setText("");
            stackThreeTextField.setText("");
            stackFourTextField.setText("");

            stackTwoTextField.setEnabled(false);
            stackThreeTextField.setEnabled(false);
            stackFourTextField.setEnabled(false);

        }
    }

    public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider)e.getSource();

        if (!source.getValueIsAdjusting()) {

            if (source.getValue() == 1) {

                stackTwoTextField.setEnabled(false);
                stackThreeTextField.setEnabled(false);
                stackFourTextField.setEnabled(false);

            }
            else if (source.getValue() == 2) {

                stackTwoTextField.setEnabled(true);
                stackThreeTextField.setEnabled(false);
                stackFourTextField.setEnabled(false);
            }
            else if (source.getValue() == 3) {

                stackTwoTextField.setEnabled(true);
                stackThreeTextField.setEnabled(true);
                stackFourTextField.setEnabled(false);
            }
            else {

                stackTwoTextField.setEnabled(true);
                stackThreeTextField.setEnabled(true);
                stackFourTextField.setEnabled(true);
            }

        }
    }
}
