package br.com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Category {

	private Integer id;
	private String name;
	private List<Product> producties = new ArrayList<Product>();

	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void adicionar(Product product) {
		producties.add(product);
	}

	public List<Product> getProducties() {
		return producties;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
