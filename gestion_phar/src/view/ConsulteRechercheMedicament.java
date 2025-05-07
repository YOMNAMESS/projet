package view;

import DAO.MedicamentDAO;
import model.Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class ConsulteRechercheMedicament extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private Image backgroundImage;
    private JTextField searchField;
    private JLabel iconLabel;

    public ConsulteRechercheMedicament() {
        setTitle("Consultation des Médicaments");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Chargement du fond
        try {
            backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Logo médicament
        iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(64, 64));
        try {
            ImageIcon icon = new ImageIcon("images/logo_med.png");
            iconLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentPane.add(iconLabel, BorderLayout.NORTH);

        // Panel du haut pour recherche
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> rechercherMedicament());

        topPanel.add(new JLabel("Nom médicament :"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        contentPane.add(topPanel, BorderLayout.SOUTH);

        // Table des médicaments
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Stock"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Chargement initial
        loadMedicaments();
    }

    private void loadMedicaments() {
        MedicamentDAO dao = new MedicamentDAO();
        try {
            List<Medicament> medicaments = dao.getAll();
            tableModel.setRowCount(0); // Nettoyer avant de remplir
            for (Medicament m : medicaments) {
                tableModel.addRow(new Object[]{m.getId_med(), m.getNom_med(), m.getStock()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des médicaments : " + e.getMessage());
        }
    }

    private void rechercherMedicament() {
        String nomRecherche = searchField.getText().trim();
        if (nomRecherche.isEmpty()) {
            loadMedicaments(); // Affiche tout
            return;
        }

        MedicamentDAO dao = new MedicamentDAO();
        try {
            List<Medicament> medicaments = dao.getAll();
            List<Medicament> filtrés = medicaments.stream()
                    .filter(m -> m.getNom_med().equalsIgnoreCase(nomRecherche))
                    .collect(Collectors.toList());

            tableModel.setRowCount(0); // Réinitialiser la table

            if (filtrés.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun médicament trouvé avec le nom : " + nomRecherche);
            } else {
                for (Medicament m : filtrés) {
                    tableModel.addRow(new Object[]{m.getId_med(), m.getNom_med(), m.getStock()});
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsulteRechercheMedicament().setVisible(true));
    }
}
