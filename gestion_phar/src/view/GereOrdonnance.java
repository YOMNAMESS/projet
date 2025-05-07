package view;

import DAO.OrdonnanceDAO;
import model.Ordonnance;
import model.Medicament;
import model.LigneMedicament;


import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GereOrdonnance extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textDescription, textIdMedicament, textQuantite;
    private List<Ordonnance> listeOrdonnances = new ArrayList<>();
    private OrdonnanceDAO dao = new OrdonnanceDAO();

    private Image backgroundImage;

    public GereOrdonnance() {
        setTitle("Gestion des Ordonnances");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("images/background.jpg")); // chemin à adapter
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel contentPane = new JPanel() {
            
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Ajout du logo ordonnance
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 10, 64, 64);
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\Messaoudi\\Desktop\\téléchargement.jpeg"); // chemin à adapter
            iconLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentPane.add(iconLabel);

        JLabel labelDescription = new JLabel("Description :");
        labelDescription.setBounds(30, 90, 100, 25);
        contentPane.add(labelDescription);

        textDescription = new JTextField();
        textDescription.setBounds(130, 90, 200, 25);
        contentPane.add(textDescription);

        JLabel labelIdMed = new JLabel("ID Médicament :");
        labelIdMed.setBounds(30, 130, 100, 25);
        contentPane.add(labelIdMed);

        textIdMedicament = new JTextField();
        textIdMedicament.setBounds(130, 130, 200, 25);
        contentPane.add(textIdMedicament);

        JLabel labelQuantite = new JLabel("Quantité :");
        labelQuantite.setBounds(30, 170, 100, 25);
        contentPane.add(labelQuantite);

        textQuantite = new JTextField();
        textQuantite.setBounds(130, 170, 200, 25);
        contentPane.add(textQuantite);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(30, 220, 80, 30);
        contentPane.add(btnAjouter);
        btnAjouter.addActionListener(e -> ajouterOrdonnance());

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBounds(120, 220, 100, 30);
        contentPane.add(btnSupprimer);
        btnSupprimer.addActionListener(e -> supprimerOrdonnance());

        JButton btnEnregistrer = new JButton("Enregistrer");
        btnEnregistrer.setBounds(230, 220, 110, 30);
        contentPane.add(btnEnregistrer);
        btnEnregistrer.addActionListener(e -> enregistrerOrdonnances());

        JButton btnFermer = new JButton("Fermer");
        btnFermer.setBounds(150, 270, 100, 30);
        contentPane.add(btnFermer);
        btnFermer.addActionListener(e -> dao.fermer());
    }

    private void ajouterOrdonnance() {
        try {
            String desc = textDescription.getText().trim();
            int idMed = Integer.parseInt(textIdMedicament.getText().trim());
            int quantite = Integer.parseInt(textQuantite.getText().trim());

            Ordonnance ordonnance = new Ordonnance(1, "deux fois par jour");
            Medicament medicament = new Medicament(1,"aspirine",200);
            LigneMedicament ligne = new LigneMedicament(1000);

            dao.ajouter(ordonnance, medicament, ligne);
            listeOrdonnances.add(ordonnance);

            JOptionPane.showMessageDialog(this, "Ordonnance ajoutée.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + ex.getMessage());
        }
    }

    private void supprimerOrdonnance() {
        try {
            String desc = textDescription.getText().trim();
            int idMed = Integer.parseInt(textIdMedicament.getText().trim());
            int quantite = Integer.parseInt(textQuantite.getText().trim());
            int idOrd = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez l'ID de l'ordonnance à supprimer :"));

            Ordonnance ordonnance = new Ordonnance(1, desc);
            Medicament medicament = new Medicament(1, "aspirine", 250);
            LigneMedicament ligne = new LigneMedicament(1000);

            dao.supprimer(ordonnance, medicament, ligne);
            JOptionPane.showMessageDialog(this, "Ordonnance supprimée.");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void enregistrerOrdonnances() {
        try {
            dao.enregistrer(listeOrdonnances);
            JOptionPane.showMessageDialog(this, "Ordonnances enregistrées.");
            listeOrdonnances.clear();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + ex.getMessage());
        }
    }

    private void clearFields() {
        textDescription.setText("");
        textIdMedicament.setText("");
        textQuantite.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GereOrdonnance().setVisible(true));
    }
}
