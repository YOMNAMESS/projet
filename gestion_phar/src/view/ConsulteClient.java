package view;
import DAO.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;

public class ConsulteClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private Image backgroundImage;

    public ConsulteClient() {
        setTitle("Consultation des Clients");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Chargement de l'image de fond
        try {
            backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création du panneau principal avec fond personnalisé
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

        // Chargement de l'icône du client
        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(64, 64));
        try {
            ImageIcon icon = new ImageIcon("images/client_icon.png");
            iconLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentPane.add(iconLabel, BorderLayout.NORTH);

        // Création du modèle de tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Crédit"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Chargement des données des clients
        loadClients();
    }

    private void loadClients() {
        ClientDAO dao = new ClientDAO();
        try {
            List<Client> clients = dao.getAll();
            for (Client c : clients) {
                tableModel.addRow(new Object[]{c.getId_cl(), c.getNom_cli(), c.getPrenom(), c.getCredit()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des clients : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsulteClient().setVisible(true));
    }
}

