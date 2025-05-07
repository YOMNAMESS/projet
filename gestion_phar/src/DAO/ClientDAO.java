package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;

public class ClientDAO implements IDAOClientMedicament <Client>{

	@Override
	public List<Client> getAll() throws SQLException {
		List<Client> clients = new ArrayList<>();
		Connection cx=SingletonConnection.getInstance();
		PreparedStatement ps=cx.prepareStatement("select * from client");
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			Client c= new Client (1,"Benali", "Sami", 1500.75);
			c.setId_cl(rs.getInt("id_cl"));
			c.setNom_cli(rs.getString("nom_cli"));
			c.setPrenom(rs.getString("prenom"));
			c.setCredit(rs.getDouble("credit"));
			clients.add(c);
			}
		ps.close();
		return clients;
	
	}
	

}
