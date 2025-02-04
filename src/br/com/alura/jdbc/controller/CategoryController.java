package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.util.List;

import br.com.alura.jdbc.dao.CategoryDAO;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Category;

public class CategoryController {
	CategoryDAO categoryDAO;

	public CategoryController() {
		Connection connection = new ConnectionFactory().getConnection();
		this.categoryDAO = new CategoryDAO(connection);
	}

	public List<Category> list() {
		return this.categoryDAO.getAll();
	}
}
