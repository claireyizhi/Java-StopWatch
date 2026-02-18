package ca.lastname.stopwatch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class StopwatchApp extends JFrame {
    private final Timer timer;

    private int totalTenthsElapsed;
    private int lapTenthsElapsed;
    private int totalSecondsDisplay;
    private int totalTenthsDisplay;
    private int lapSecondsDisplay;
    private int lapTenthsDisplay;
    private int lapCounter;

    private boolean running;

    private final JTextField timeSecondsField;
    private final JTextField timeTenthsField;
    private final JTextField lapSecondsField;
    private final JTextField lapTenthsField;
    private final JTextArea lapsArea;
    private final JButton startStopButton;
    private final JButton lapResetButton;

    public StopwatchApp() {
        super("Stopwatch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timeSecondsField = createNumberField();
        timeTenthsField = createNumberField();
        lapSecondsField = createNumberField();
        lapTenthsField = createNumberField();
        lapsArea = new JTextArea(7, 24);
        startStopButton = new JButton("Start");
        lapResetButton = new JButton("Reset");

        timer = new Timer(100, event -> onTick());
        buildUi();
        bindEvents();

        resetState();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Stopwatch");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        header.add(title, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        center.add(new JLabel("Time"), gbc);

        gbc.gridx = 1;
        center.add(timeSecondsField, gbc);

        gbc.gridx = 2;
        center.add(new JLabel("."), gbc);

        gbc.gridx = 3;
        center.add(timeTenthsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        center.add(new JLabel("Lap"), gbc);

        gbc.gridx = 1;
        center.add(lapSecondsField, gbc);

        gbc.gridx = 2;
        center.add(new JLabel("."), gbc);

        gbc.gridx = 3;
        center.add(lapTenthsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        center.add(startStopButton, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        center.add(lapResetButton, gbc);

        root.add(center, BorderLayout.CENTER);

        lapsArea.setEditable(false);
        lapsArea.setLineWrap(true);
        lapsArea.setWrapStyleWord(true);
        lapsArea.setBorder(BorderFactory.createLineBorder(new Color(120, 76, 167), 2));
        JScrollPane scrollPane = new JScrollPane(lapsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Previous Laps"));
        root.add(scrollPane, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void bindEvents() {
        startStopButton.addActionListener(event -> toggleRunning());
        lapResetButton.addActionListener(event -> {
            if (running) {
                recordLap();
            } else {
                resetState();
            }
        });
    }

    private JTextField createNumberField() {
        JTextField field = new JTextField(4);
        field.setEditable(false);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setFont(field.getFont().deriveFont(Font.BOLD, 24f));
        return field;
    }

    private void toggleRunning() {
        if (running) {
            timer.stop();
            running = false;
            startStopButton.setText("Start");
            lapResetButton.setText("Reset");
        } else {
            timer.start();
            running = true;
            startStopButton.setText("Stop");
            lapResetButton.setText("Lap");
        }
    }

    private void onTick() {
        totalTenthsElapsed++;
        lapTenthsElapsed++;
        updateDisplayValues();
        refreshDisplayFields();
    }

    private void recordLap() {
        updateDisplayValues();
        lapCounter++;
        lapsArea.append(
            String.format(
                "Lap %d: %d.%d%n",
                lapCounter,
                lapSecondsDisplay,
                lapTenthsDisplay
            )
        );
        lapTenthsElapsed = 0;
        updateDisplayValues();
        refreshDisplayFields();
    }

    private void resetState() {
        timer.stop();
        running = false;

        totalTenthsElapsed = 0;
        lapTenthsElapsed = 0;
        totalSecondsDisplay = 0;
        totalTenthsDisplay = 0;
        lapSecondsDisplay = 0;
        lapTenthsDisplay = 0;
        lapCounter = 0;

        lapsArea.setText("");
        startStopButton.setText("Start");
        lapResetButton.setText("Reset");
        refreshDisplayFields();
    }

    private void updateDisplayValues() {
        totalSecondsDisplay = totalTenthsElapsed / 10;
        totalTenthsDisplay = totalTenthsElapsed % 10;
        lapSecondsDisplay = lapTenthsElapsed / 10;
        lapTenthsDisplay = lapTenthsElapsed % 10;
    }

    private void refreshDisplayFields() {
        timeSecondsField.setText(String.format("%02d", totalSecondsDisplay));
        timeTenthsField.setText(String.valueOf(totalTenthsDisplay));
        lapSecondsField.setText(String.format("%02d", lapSecondsDisplay));
        lapTenthsField.setText(String.valueOf(lapTenthsDisplay));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StopwatchApp().setVisible(true));
    }
}
