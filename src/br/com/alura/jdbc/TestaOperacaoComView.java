package br.com.alura.jdbc;

import javax.swing.JFrame;

import br.com.alura.jdbc.view.ProductCategoryFrame;

public class TestaOperacaoComView {

	public static void main(String[] args) {
		ProductCategoryFrame productCategoryFrame = new ProductCategoryFrame();
		productCategoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
