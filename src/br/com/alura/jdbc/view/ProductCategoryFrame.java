package br.com.alura.jdbc.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.alura.jdbc.controller.CategoryController;
import br.com.alura.jdbc.controller.ProductController;
import br.com.alura.jdbc.modelo.Category;
import br.com.alura.jdbc.modelo.Product;

public class ProductCategoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel labelName, labelDescription, labelCategory;
	private JTextField textoName, textoDescription;
	private JComboBox<Category> comboCategory;
	private JButton botaoSalvar, botaoEditar, botaoLimpar, botarApagar;
	private JTable tabela;
	private DefaultTableModel modelo;
	private ProductController productController;
	private CategoryController categoryController;

	public ProductCategoryFrame() {
		super("Products");
		Container container = getContentPane();
		setLayout(null);

		this.categoryController = new CategoryController();
		this.productController = new ProductController();

		labelName = new JLabel("Nome do produto");
		labelDescription = new JLabel("Descrição do produto");
		labelCategory = new JLabel("Categoria do produto");

		labelName.setBounds(10, 10, 240, 15);
		labelDescription.setBounds(10, 50, 240, 15);
		labelCategory.setBounds(10, 90, 240, 15);

		labelName.setForeground(Color.BLACK);
		labelDescription.setForeground(Color.BLACK);
		labelCategory.setForeground(Color.BLACK);

		container.add(labelName);
		container.add(labelDescription);
		container.add(labelCategory);

		textoName = new JTextField();
		textoDescription = new JTextField();
		comboCategory = new JComboBox<Category>();

		comboCategory.addItem(new Category(0, "Selecione"));
		List<Category> categorys = this.listCategory();
		for (Category category : categorys) {
			comboCategory.addItem(category);
		}

		textoName.setBounds(10, 25, 265, 20);
		textoDescription.setBounds(10, 65, 265, 20);
		comboCategory.setBounds(10, 105, 265, 20);

		container.add(textoName);
		container.add(textoDescription);
		container.add(comboCategory);

		botaoSalvar = new JButton("Salvar");
		botaoLimpar = new JButton("Limpar");

		botaoSalvar.setBounds(10, 145, 80, 20);
		botaoLimpar.setBounds(100, 145, 80, 20);

		container.add(botaoSalvar);
		container.add(botaoLimpar);

		tabela = new JTable();
		modelo = (DefaultTableModel) tabela.getModel();

		modelo.addColumn("Identificador do Produto");
		modelo.addColumn("Nome do produto");
		modelo.addColumn("Descrição do produto");

		preencherTabela();

		tabela.setBounds(10, 185, 760, 300);
		container.add(tabela);

		botarApagar = new JButton("Excluir");
		botaoEditar = new JButton("Alterar");

		botarApagar.setBounds(10, 500, 80, 20);
		botaoEditar.setBounds(100, 500, 80, 20);

		container.add(botarApagar);
		container.add(botaoEditar);

		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);

		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		botarApagar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterar();
				limparTabela();
				preencherTabela();
			}
		});
	}

	private void limparTabela() {
		modelo.getDataVector().clear();
	}

	private void alterar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			String name = (String) modelo.getValueAt(tabela.getSelectedRow(), 1);
			String description = (String) modelo.getValueAt(tabela.getSelectedRow(), 2);
			this.productController.alterar(name, description, id);
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void deletar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			this.productController.deletar(id);
			modelo.removeRow(tabela.getSelectedRow());
			JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void preencherTabela() {
		List<Product> products = listProduct();
		try {
			for (Product product : products) {
				modelo.addRow(new Object[] { product.getId(), product.getName(), product.getDescription() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Category> listCategory() {
		return this.categoryController.list();
	}

	private void salvar() {
		if (!textoName.getText().equals("") && !textoDescription.getText().equals("")) {
			Product product = new Product(textoName.getText(), textoDescription.getText());
			Category category = (Category) comboCategory.getSelectedItem();
			product.setCategoryId(category.getId());
			this.productController.salvar(product);
			JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
			this.limpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nome e descrição devem ser informados.");
		}
	}

	private List<Product> listProduct() {
		return this.productController.listar();
	}

	private void limpar() {
		this.textoName.setText("");
		this.textoDescription.setText("");
		this.comboCategory.setSelectedIndex(0);
	}
}
