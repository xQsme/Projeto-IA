package gui;

import agent.Heuristic;
import agent.Solution;
import forkliftpuzzle.ForkliftPuzzleAgent;
import forkliftpuzzle.ForkliftPuzzleProblem;
import forkliftpuzzle.ForkliftPuzzleState;
import searchmethods.BeamSearch;
import searchmethods.DepthLimitedSearch;
import searchmethods.SearchMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MainFrame extends JFrame {

    private int[][] initialMatrix = {{0,0,0,0,0,0},{0,2,2,2,0,0},{1,0,0,3,0,-1},{0,3,0,2,0,2},{0,0,0,3,0,0},
            {0,0,0,0,0,0}};
    private ForkliftPuzzleAgent agent = new ForkliftPuzzleAgent(new ForkliftPuzzleState(initialMatrix, null));
    private JComboBox comboBoxSearchMethods;
    private JComboBox comboBoxHeuristics;
    private JLabel labelSearchParameter = new JLabel("limit/beam size:");
    private JTextField textFieldSearchParameter = new JTextField("0", 5);
    private PuzzleTableModel puzzleTableModel;
    private JTable tablePuzzle = new JTable();
    private JButton buttonInitialState = new JButton("Read initial state");
    private JButton buttonSolve = new JButton("Solve");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonShowSolution = new JButton("Show solution");
    private JButton buttonReset = new JButton("Reset to initial state");
    private JTextArea textArea;

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void jbInit() throws Exception {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Forklift Puzzle");

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(buttonInitialState);
        buttonInitialState.addActionListener(new ButtonInitialState_ActionAdapter(this));
        panelButtons.add(buttonSolve);
        buttonSolve.addActionListener(new ButtonSolve_ActionAdapter(this));
        panelButtons.add(buttonStop);
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new ButtonStop_ActionAdapter(this));
        panelButtons.add(buttonShowSolution);
        buttonShowSolution.setEnabled(false);
        buttonShowSolution.addActionListener(new ButtonShowSolution_ActionAdapter(this));
        panelButtons.add(buttonReset);
        buttonReset.setEnabled(false);
        buttonReset.addActionListener(new ButtonReset_ActionAdapter(this));

        JPanel panelSearchMethods = new JPanel(new FlowLayout());
        comboBoxSearchMethods = new JComboBox(agent.getSearchMethodsArray());
        panelSearchMethods.add(comboBoxSearchMethods);
        comboBoxSearchMethods.addActionListener(new ComboBoxSearchMethods_ActionAdapter(this));
        panelSearchMethods.add(labelSearchParameter);
        labelSearchParameter.setEnabled(false);
        panelSearchMethods.add(textFieldSearchParameter);
        textFieldSearchParameter.setEnabled(false);
        textFieldSearchParameter.setHorizontalAlignment(JTextField.RIGHT);
        textFieldSearchParameter.addKeyListener(new TextFieldSearchParameter_KeyAdapter(this));
        comboBoxHeuristics = new JComboBox(agent.getHeuristicsArray());
        panelSearchMethods.add(comboBoxHeuristics);
        comboBoxHeuristics.setEnabled(false);
        comboBoxHeuristics.addActionListener(new ComboBoxHeuristics_ActionAdapter(this));

        JPanel puzzlePanel = new JPanel(new FlowLayout());
        puzzlePanel.add(tablePuzzle);
        textArea = new JTextArea(15, 31);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        puzzlePanel.add(scrollPane);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelButtons, java.awt.BorderLayout.NORTH);
        mainPanel.add(panelSearchMethods, java.awt.BorderLayout.CENTER);
        mainPanel.add(puzzlePanel, java.awt.BorderLayout.SOUTH);
        contentPane.add(mainPanel);

        configureTabel(tablePuzzle);

        pack();
    }

    private void configureTabel(JTable table) {
        puzzleTableModel = new PuzzleTableModel(agent.getEnvironment());
        tablePuzzle.setModel(puzzleTableModel);
        table.setDefaultRenderer(Object.class, new PuzzleTileCellRenderer());
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(Properties.CELL_WIDTH);
        }
        table.setRowHeight(Properties.CELL_HEIGHT);
        table.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void buttonInitialState_ActionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(new java.io.File("."));
        try {
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                puzzleTableModel.setPuzzle(agent.readInitialStateFromFile(fc.getSelectedFile()));
                buttonSolve.setEnabled(true);
                buttonShowSolution.setEnabled(false);
                buttonReset.setEnabled(false);
            }
        } catch (IOException e1) {
            e1.printStackTrace(System.err);
        } catch (NoSuchElementException e2) {
            JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void comboBoxSearchMethods_ActionPerformed(ActionEvent e) {
        int index = comboBoxSearchMethods.getSelectedIndex();
        agent.setSearchMethod((SearchMethod) comboBoxSearchMethods.getItemAt(index));
        puzzleTableModel.setPuzzle(agent.resetEnvironment());
        buttonSolve.setEnabled(true);
        buttonShowSolution.setEnabled(false);
        buttonReset.setEnabled(false);
        textArea.setText("");
        comboBoxHeuristics.setEnabled(index > 4); //Informed serch methods
        textFieldSearchParameter.setEnabled(index == 3 || index == 7); // limited depth or beam search
        labelSearchParameter.setEnabled(index == 3 || index == 7); // limited depth or beam search
    }

    public void comboBoxHeuristics_ActionPerformed(ActionEvent e) {
        int index = comboBoxHeuristics.getSelectedIndex();
        agent.setHeuristic((Heuristic) comboBoxHeuristics.getItemAt(index));
        puzzleTableModel.setPuzzle(agent.resetEnvironment());
        buttonSolve.setEnabled(true);
        buttonShowSolution.setEnabled(false);
        buttonReset.setEnabled(false);
        textArea.setText("");
    }

    public void buttonSolve_ActionPerformed(ActionEvent e) {

        SwingWorker worker = new SwingWorker<Solution, Void>() {
            public Solution doInBackground() {
                textArea.setText("");
                buttonStop.setEnabled(true);
                buttonSolve.setEnabled(false);
                try {
                    prepareSearchAlgorithm();
                    ForkliftPuzzleProblem problem = new ForkliftPuzzleProblem((ForkliftPuzzleState) agent.getEnvironment().clone());
                    agent.solveProblem(problem);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                if (!agent.hasBeenStopped()) {
                    textArea.setText(agent.getSearchReport());
                    if (agent.hasSolution()) {
                        buttonShowSolution.setEnabled(true);
                        buttonSolve.setEnabled(false);
                    }
                }
                buttonStop.setEnabled(false);
            }
        };

        worker.execute();
    }

    public void buttonStop_ActionPerformed(ActionEvent e) {
        agent.stop();
        buttonShowSolution.setEnabled(false);
        buttonStop.setEnabled(false);
        buttonSolve.setEnabled(true);
    }

    public void buttonShowSolution_ActionPerformed(ActionEvent e) {
        buttonShowSolution.setEnabled(false);
        buttonStop.setEnabled(false);
        SwingWorker worker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
                agent.executeSolution();
                buttonShowSolution.setEnabled(false);
                buttonReset.setEnabled(true);
                return null;
            }
        };
        worker.execute();
    }

    public void buttonReset_ActionPerformed(ActionEvent e) {
        puzzleTableModel.setPuzzle(agent.resetEnvironment());
        buttonShowSolution.setEnabled(true);
        buttonReset.setEnabled(false);
    }

    private void prepareSearchAlgorithm() {
        if (agent.getSearchMethod() instanceof DepthLimitedSearch) {
            DepthLimitedSearch searchMethod = (DepthLimitedSearch) agent.getSearchMethod();
            searchMethod.setLimit(Integer.parseInt(textFieldSearchParameter.getText()));
        } else if (agent.getSearchMethod() instanceof BeamSearch) {
            BeamSearch searchMethod = (BeamSearch) agent.getSearchMethod();
            searchMethod.setBeamSize(Integer.parseInt(textFieldSearchParameter.getText()));
        }
    }
}

class ComboBoxSearchMethods_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ComboBoxSearchMethods_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.comboBoxSearchMethods_ActionPerformed(e);
    }
}

class ComboBoxHeuristics_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ComboBoxHeuristics_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.comboBoxHeuristics_ActionPerformed(e);
    }
}

class ButtonInitialState_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ButtonInitialState_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonInitialState_ActionPerformed(e);
    }
}

class ButtonSolve_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ButtonSolve_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonSolve_ActionPerformed(e);
    }
}

class ButtonStop_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ButtonStop_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonStop_ActionPerformed(e);
    }
}

class ButtonShowSolution_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ButtonShowSolution_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonShowSolution_ActionPerformed(e);
    }
}

class ButtonReset_ActionAdapter implements ActionListener {

    private MainFrame adaptee;

    ButtonReset_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonReset_ActionPerformed(e);
    }
}

class TextFieldSearchParameter_KeyAdapter implements KeyListener {

    private MainFrame adaptee;

    TextFieldSearchParameter_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
