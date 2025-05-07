package DAO;

import java.sql.SQLException;
import java.util.List;

public interface IDAOClientMedicament<T> {
	List<T> getAll() throws SQLException;

}
