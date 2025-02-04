package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Category;
import br.com.alura.jdbc.modelo.Product;

public class ProductDAO {

	private Connection connection;

	public ProductDAO(Connection connection) {
		this.connection = connection;
	}

	public void save(Product product) {
		try {
			String sql = "INSERT INTO products (name, description) VALUES (?, ?)";
			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, product.getName());
				pstm.setString(2, product.getDescription());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						product.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException error) {
			throw new RuntimeException();
		}
	}

	public void saveWithCategory(Product product) {
		try {
			String sql = "INSERT INTO products (name, description, category_id) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, product.getName());
				pstm.setString(2, product.getDescription());
				pstm.setInt(3, product.getCategoryId());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						product.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException error) {
			throw new RuntimeException();
		}
	}

	public List<Product> listar() {
		try {
			List<Product> products = new ArrayList<Product>();
			String sql = "SELECT id, name, description FROM products";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				trasformarResultSetEmProduct(products, pstm);
			}
			return products;
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}

	public List<Product> buscar(Category ct) {
		try {
			List<Product> products = new ArrayList<Product>();
			String sql = "SELECT id, name, description FROM products WHERE category_id = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, ct.getId());
				pstm.execute();

				trasformarResultSetEmProduct(products, pstm);
			}
			return products;
		} catch (SQLException error) {
			throw new RuntimeException();
		}
	}

	public void deletar(Integer id) {
		try {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM products WHERE ID = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		} catch (SQLException error) {
			throw new RuntimeException();
		}
	}

	public void alterar(String name, String description, Integer id) {
		try {
			try (PreparedStatement stm = connection
					.prepareStatement("UPDATE products P SET P.name = ?, P.description = ? WHERE id = ?")) {
				stm.setString(1, name);
				stm.setString(2, description);
				stm.setInt(3, id);
				stm.execute();
			}
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}

	private void trasformarResultSetEmProduct(List<Product> products, PreparedStatement pstm) {
		try {
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Product product = new Product(rst.getInt(1), rst.getString(2), rst.getString(3));

					products.add(product);
				}
			}
		} catch (SQLException error) {
			throw new RuntimeException(error);
		}
	}
}
