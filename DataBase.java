import java.sql.*;

public class DataBase {

	// JDBC URL, username and password of MySQL server
	private static final String url = "jdbc:mysql://127.0.0.1:3306/timetable_infosystem";
	private static final String user = "root";
	private static final String password = "1234";

	// JDBC variables for opening and managing connection
	private Connection con;
	private CallableStatement stmt;

	public DataBase() {
		try {
            Driver driver = new com.mysql.jdbc.Driver();
        } catch (SQLException e) {
            System.out.println("Unable to load driver class.");
            e.printStackTrace();
        }
		try {
			// opening database connection to MySQL server
			con = DriverManager.getConnection(url, user, password);

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}

	public ResultSet request(String procedure_name, String[] names, String[] types, String[] values) {
		String params = "?";
		if (names != null) {
			params = "";
			for (int i = 0; i < names.length; i++) {
				params += "?,";
			}
			params += "?";
		}
		String query = "call " + procedure_name + "(" + params + ")";
		try {
			stmt = con.prepareCall(query);
			if (names != null) {
				for (int i = 0; i < names.length; i++) {
					if (types[i] == "String")
						stmt.setString(names[i], values[i]);
					if (types[i] == "Int")
						stmt.setInt(names[i], Integer.parseInt(values[i]));
				}
			}
			stmt.registerOutParameter("result_status", Types.TINYINT);
			stmt.executeQuery();
			return stmt.getResultSet();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		return null;
	}

	String[] getHeaders(ResultSet resp) throws SQLException {
		ResultSetMetaData data = resp.getMetaData();
		int ncol = data.getColumnCount();
		String headers[] = new String[ncol];
		for (int i = 0; i < ncol; i++) {
			headers[i] = data.getColumnName(i + 1);
		}
		return headers;
	}

	String[][] getContent(ResultSet resp) throws SQLException {
		ResultSetMetaData data = resp.getMetaData();
		int ncol = data.getColumnCount();
		resp.last();
		int nrow = resp.getRow();
		resp.beforeFirst();
		String[][] content = new String[nrow][ncol];
		for (int i = 0; i < nrow; i++) {
			resp.next();
			for (int j = 0; j < ncol; j++) {
				content[i][j] = resp.getString(j+1);
			}
		}
		return content;
	}

	int getStatus() throws SQLException {
		int status = stmt.getInt("result_status");
		return status;
	}
}