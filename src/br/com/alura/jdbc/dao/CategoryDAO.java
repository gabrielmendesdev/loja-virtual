package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Category;
import br.com.alura.jdbc.modelo.Product;

public class CategoryDAO {

	private Connection connection;

	public CategoryDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Category> getAll() {
		try {
			List<Category> categorys = new ArrayList<>();
			String sql = "SELECT * FROM category";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Category category = new Category(rst.getInt(1), rst.getString(2));

						categorys.add(category);
					}
				}
			}
			return categorys;
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}

	public List<Category> categoryWithProduct() {
		try {
			Category ultima = null;
			List<Category> categorys = new ArrayList<>();

			String sql = "SELECT C.id, C.name, P.id, P.name, P.description" + "FROM category C "
					+ "INNER JOIN products P ON C.ID = P.category_id";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						if (ultima == null || !ultima.getName().equals(rst.getString(2))) {
							Category category = new Category(rst.getInt(1), rst.getString(2));

							categorys.add(category);
							ultima = category;
						}
						Product product = new Product(rst.getInt(3), rst.getString(4), rst.getString(5));
						ultima.adicionar(product);
					}
				}
				return categorys;
			}
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}
}
