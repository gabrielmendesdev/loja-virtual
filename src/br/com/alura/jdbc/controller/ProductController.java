package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.util.List;

import br.com.alura.jdbc.dao.ProductDAO;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Product;

public class ProductController {

	ProductDAO productDAO;

	public ProductController() {
		Connection connection = new ConnectionFactory().getConnection();
		this.productDAO = new ProductDAO(connection);
	}

	public void deletar(Integer id) {
		System.out.println("Deletando");
		productDAO.deletar(id);
	}

	public void salvar(Product product) {
		System.out.println("Salvando product");
		productDAO.save(product);
	}

	public List<Product> listar() {
		return productDAO.listar();
	}

	public void alterar(String nome, String descricao, Integer id) {
		System.out.println("Alterando product");
		productDAO.alterar(nome, descricao, id);
	}
}
