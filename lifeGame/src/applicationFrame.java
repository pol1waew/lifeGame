import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class applicationFrame extends JFrame {
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JLabel currentTurnLabel;
    private JLabel toCreateLabel;
    private JLabel toRemoveLabel;
    private JLabel iteractionLabel;

    private JButton evolutionButton;
    private JButton turnEndButton;

    public applicationFrame(game g) {
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createPanels(g);

        setupTopPanel(g);
        setupCenterPanel(g);
        setupBottomPanel(g);

        setVisible(true);
    }

    private void createPanels(game g) {
        topPanel = new JPanel(new GridLayout(4, 2, 0, 5));
        centerPanel = new JPanel(new GridLayout(g.getField().getSize()[0], g.getField().getSize()[1], 0,0));
        bottomPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupTopPanel(game g) {
        currentTurnLabel = new JLabel("Ход игрока " + (g.getCurrentPlayer() + 1));
        topPanel.add(currentTurnLabel);
        topPanel.add(new JLabel(""));
        g.setCurrentTurnLabel(currentTurnLabel);

        toCreateLabel = new JLabel("Необходимо создать существ:");
        topPanel.add(toCreateLabel);
        JTextField toCreateText = new JTextField("" + g.getCreaturesToCreate());
        toCreateText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setCreaturesToCreate(Integer.valueOf(toCreateText.getText()));
            }
        });
        topPanel.add(toCreateText);

        toRemoveLabel = new JLabel("Можно удалить существ:");
        topPanel.add(toRemoveLabel);
        JTextField toRemoveText = new JTextField("" + g.getCreaturesToRemove());
        toRemoveText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setCreaturesToCreate(Integer.valueOf(toRemoveText.getText()));
            }
        });
        topPanel.add(toRemoveText);

        iteractionLabel = new JLabel("Количество эволюций:");
        topPanel.add(iteractionLabel);
        JTextField evolutionIterationsText = new JTextField("" + g.getEvolutionIterationNumber());
        evolutionIterationsText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setEvolutionIterationNumber(Integer.valueOf(evolutionIterationsText.getText()));
            }
        });
        topPanel.add(evolutionIterationsText);
    }

    private void setupCenterPanel(game g) {
        for (int i = 0; i < g.getField().getSize()[0] * g.getField().getSize()[1]; i++) {
            int x = i % g.getField().getSize()[1];
            int y = i / g.getField().getSize()[1];

            if (g.getField().getCell(x, y) == null) {
                centerPanel.add(new JLabel(""));
                continue;
            }

            JButton b = new JButton(" ");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    g.interactWithCell(x, y);
                }
            });

            centerPanel.add(b);
            g.getField().getCell(x, y).setButton(b);
        }
    }

    private void setupBottomPanel(game g) {
        evolutionButton = new JButton("Эволюция");
        evolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.initiateEvolution();
            }
        });
        bottomPanel.add(evolutionButton);

        turnEndButton = new JButton("Передать ход");
        turnEndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.switchPlayer();
            }
        });
        bottomPanel.add(turnEndButton);
    }
}
