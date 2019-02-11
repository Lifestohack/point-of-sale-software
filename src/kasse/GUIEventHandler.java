package kasse;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class GUIEventHandler implements Initializable {
	KasseManager km = new KasseManager();
	ObservableList<KasseTable> kasseTable = FXCollections.observableArrayList();

	@FXML
	private JFXTextField articleId;

	@FXML
	private JFXButton startstop;

	@FXML
	private Label labelstartstop;

	@FXML
	private Label labelInfo;

	@FXML
	private StackPane stackPane;

	@FXML
	private StackPane stackPaneStartStop;

	@FXML
	private JFXTreeTableView<KasseTable> treetablelistview;

	@FXML
	private Label saldo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		JFXTreeTableColumn<KasseTable, String> an = new JFXTreeTableColumn<>("Artikelnummer");
		an.setPrefWidth(100);
		an.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<KasseTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<KasseTable, String> param) {
						return param.getValue().getValue().articleNumber;
					}
				});

		JFXTreeTableColumn<KasseTable, String> name = new JFXTreeTableColumn<>("Name");
		name.setPrefWidth(100);
		name.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<KasseTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<KasseTable, String> param) {
						return param.getValue().getValue().articleName;
					}
				});

		JFXTreeTableColumn<KasseTable, String> qa = new JFXTreeTableColumn<>("Menge");
		qa.setPrefWidth(70);
		qa.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<KasseTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<KasseTable, String> param) {
						return param.getValue().getValue().quantity;
					}
				});

		JFXTreeTableColumn<KasseTable, String> discount = new JFXTreeTableColumn<>("%");
		discount.setPrefWidth(70);
		discount.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<KasseTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<KasseTable, String> param) {
						return param.getValue().getValue().discount;
					}
				});

		JFXTreeTableColumn<KasseTable, String> price = new JFXTreeTableColumn<>("Preis");
		price.setPrefWidth(70);
		price.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<KasseTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<KasseTable, String> param) {
						return param.getValue().getValue().price;
					}
				});

		final TreeItem<KasseTable> root = new RecursiveTreeItem<>(kasseTable, RecursiveTreeObject::getChildren);

		treetablelistview.getColumns().setAll(an, name, qa, discount, price);
		treetablelistview.setRoot(root);
		treetablelistview.setShowRoot(false);
	}

	@FXML
	private void deleteLastNumber(ActionEvent event) {
		String textfieldvalue = articleId.getText();
		if (textfieldvalue != null) {
			if (!textfieldvalue.isEmpty()) {
				textfieldvalue = textfieldvalue.substring(0, textfieldvalue.length() - 1);
				articleId.setText(textfieldvalue);
			}
		}
	}

	private void showDialog(String heading, String button) {
		stackPane.toFront();
		stackPaneStartStop.toFront();
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
		dialog.setOverlayClose(false);
		dialogContent.setHeading(new Text(heading));

		JFXButton ok = new JFXButton(button);
		ok.getStyleClass().add("JFXButton");

		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
				stackPane.toBack();
				stackPaneStartStop.toBack();
			}
		});

		dialogContent.setActions(ok, new Label("   "));
		dialog.show();
	}

	@FXML
	private void searchArticle(ActionEvent event) {
		String id = articleId.getText();
		if (!id.isEmpty()) {
			String[] article = km.addArticleToReceipt(id);

			if (article == null) {
				showDialog("Artikel nicht gefunden.", "OK");
			} else {
				boolean addNew = true;
				for (int i = 0; i < kasseTable.size(); i++) {
					StringProperty articlenumber = kasseTable.get(i).articleNumber;
					if (articlenumber.getValue().equals(article[0])) {
						int totalQuantity = Integer.parseInt(kasseTable.get(i).quantity.getValue().toString()) + 1;
						kasseTable.get(i).quantity = new SimpleStringProperty(Integer.toString(totalQuantity));
						double totalPrice = Double.parseDouble(kasseTable.get(i).price.getValue().toString())
								+ Double.parseDouble(article[3]);
						kasseTable.get(i).price = new SimpleStringProperty(Double.toString(totalPrice));
						addNew = false;
						treetablelistview.getSelectionModel().select(i);
					}
				}
				if (addNew) {
					kasseTable.add(new KasseTable(article[0], article[1], article[2], "0.0", article[3]));
					treetablelistview.getSelectionModel().select(kasseTable.size() - 1);
				}
				treetablelistview.refresh();
				updateSaldo();

			}
		}
		articleId.clear();

	}

	private void updateSaldo() {
		double saldoMoney = 0;
		for (int i = 0; i < kasseTable.size(); i++) {
			saldoMoney = saldoMoney + Double.parseDouble(kasseTable.get(i).price.getValue().toString());
		}
		saldo.setText(Double.toString(saldoMoney));
	}

	@FXML
	private void addDiscount(ActionEvent event) {
		int index = treetablelistview.getSelectionModel().getSelectedIndex();
		String discountString = kasseTable.get(index).discount.getValue().toString();

		if (!discountString.equals("0.0")) {
			showDialog("Rabatt ist schon eingetragen.", "OK");
			articleId.clear();
			return;
		}

		String value = ((Button) event.getSource()).getText().trim().replace("%", "");
		if (!value.isEmpty()) {

			if (kasseTable.size() > 0) {
				double discount = Double.parseDouble(value);
				TreeItem<KasseTable> treeitemkassetable = treetablelistview.getSelectionModel().getSelectedItem();
				if (treeitemkassetable != null) {
					StringProperty price = treeitemkassetable.getValue().price;
					double pricetoDiscount = Double.parseDouble(price.getValue().toString());
					double finalPrice = pricetoDiscount - pricetoDiscount * discount / 100;
					kasseTable.get(index).price = new SimpleStringProperty(
							Double.toString((double) Math.round(finalPrice * 100) / 100));
					kasseTable.get(index).discount = new SimpleStringProperty(
							Double.toString((double) Math.round(discount * 100) / 100));
				}
			} else {
				showDialog("Keine Artikel gefunden.", "OK");
			}

		} else {
			if (kasseTable.size() > 0) {
				String discount = articleId.getText();
				if (!discount.isEmpty()) {
					double discount1 = Double.parseDouble(discount);
					TreeItem<KasseTable> treeitemkassetable = treetablelistview.getSelectionModel().getSelectedItem();
					if (treeitemkassetable != null) {
						StringProperty price = treeitemkassetable.getValue().price;
						double pricetoDiscount = Double.parseDouble(price.getValue().toString());
						double finalPrice = pricetoDiscount - pricetoDiscount * discount1 / 100;
						kasseTable.get(index).price = new SimpleStringProperty(
								Double.toString((double) Math.round(finalPrice * 100) / 100));
						kasseTable.get(index).discount = new SimpleStringProperty(
								Double.toString((double) Math.round(discount1 * 100) / 100));

					}
				} else {
					showDialog("Keine Rabatt wert eingegeben.", "OK");
				}
			} else {
				showDialog("Keine Artikel gefunden.", "OK");
			}

		}
		updateSaldo();
		articleId.clear();
		treetablelistview.refresh();

	}

	@FXML
	private void processNumpad(ActionEvent event) {
		String value = ((Button) event.getSource()).getText();
		String textfieldvalue = articleId.getText();

		StringBuilder sb1 = new StringBuilder(textfieldvalue);
		sb1.append(value);
		articleId.setText(sb1.toString());

	}

	@FXML
	private void OnStartStop(ActionEvent event) {
		String labelstartstopvalue = labelstartstop.getText();
		String label = "Bitte klicken Sie hier Verkaufsprozess zu starten. →";

		if (!labelstartstopvalue.isEmpty()) {
			km.startNewSale();
			stackPane.toBack();
			labelstartstop.setText("");
			startstop.setText("Stop");
		} else {
			stackPane.toFront();
			stackPaneStartStop.toFront();
			JFXDialogLayout dialogContent = new JFXDialogLayout();
			JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
			dialog.setOverlayClose(false);
			dialogContent.setHeading(new Text("Are you sure?"));

			JFXButton yes = new JFXButton("Yes");
			yes.getStyleClass().add("JFXButton");

			yes.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					km.dispose();
					articleId.clear();
					saldo.setText("0.0");
					kasseTable.clear();
					treetablelistview.refresh();
					dialog.close();
					labelstartstop.setText(label);
					startstop.setText("Start");
					stackPane.toFront();
					stackPaneStartStop.toBack();
				}
			});

			JFXButton no = new JFXButton("No");
			no.getStyleClass().add("JFXButton");

			no.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					dialog.close();
					stackPane.toBack();
					stackPaneStartStop.toBack();
				}
			});
			dialogContent.setActions(yes, new Label("   "), no);
			dialog.show();
		}
	}

	@FXML
	private void onPay(ActionEvent event) {
		String label = "Bitte klicken Sie hier Verkaufsprozess zu starten. →";
		String money = articleId.getText();
		if (money.isEmpty()) {
			showDialog("Hat dein Kunde schon bezahlt? ", "Nein");
			return;
		}
		double moneyInt = Double.parseDouble(money);
		double saldoPrice = Double.parseDouble(saldo.getText());

		double moneyback = moneyInt - saldoPrice;

		stackPane.toFront();
		stackPaneStartStop.toFront();
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
		dialog.setOverlayClose(false);
		dialogContent.setHeading(new Text("Rückgeld: " + moneyback));

		JFXButton ok = new JFXButton("Fertig");
		ok.getStyleClass().add("JFXButton");

		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				km.endSale(kasseTable, saldo.getText());
				articleId.clear();
				saldo.setText("0.0");
				kasseTable.clear();
				treetablelistview.refresh();
				dialog.close();
				labelstartstop.setText(label);
				startstop.setText("Start");
				stackPane.toFront();
				stackPaneStartStop.toBack();
			}
		});

		dialogContent.setActions(ok, new Label("   "));
		dialog.show();

	}

	@FXML
	private void onDeleteArticle(ActionEvent event) {
		int index = treetablelistview.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			showDialog("Keine Artikel gefunden.", "OK");
			return;
		}
		String articleNumberString = treetablelistview.getSelectionModel().getSelectedItem().getValue().articleNumber
				.getValue().toString();
		String quantityString = treetablelistview.getSelectionModel().getSelectedItem().getValue().quantity.getValue()
				.toString();
		int quantityInt = Integer.parseInt(quantityString);
		if (quantityInt > 1) {
			int totalQuantity = quantityInt - 1;
			kasseTable.get(index).quantity = new SimpleStringProperty(Integer.toString(totalQuantity));

			double priceOnTree = Double.parseDouble(kasseTable.get(index).price.getValue().toString());
			double priceOnDB = Double.parseDouble(
					km.getArticleInformation(kasseTable.get(index).articleNumber.getValue().toString(), "price"));
			double totalPrice = priceOnTree - priceOnDB;
			kasseTable.get(index).price = new SimpleStringProperty(Double.toString(totalPrice));
			treetablelistview.refresh();
			updateSaldo();

		} else if (quantityInt == 1) {

			double priceOnTree = Double.parseDouble(kasseTable.get(index).price.getValue().toString());

			double saldoPrice = Double.parseDouble(saldo.getText());
			double totalSaldo = saldoPrice - priceOnTree;
			saldo.setText(Double.toString(totalSaldo));
			kasseTable.remove(index);
			treetablelistview.refresh();
		}
		km.changeArticle(articleNumberString, "quantity", "1");

	}

	@FXML
	private void onUp(ActionEvent event) {
		int index = treetablelistview.getSelectionModel().getSelectedIndex();
		treetablelistview.getSelectionModel().select(index - 1);
	}

	@FXML
	private void onDown(ActionEvent event) {
		int index = treetablelistview.getSelectionModel().getSelectedIndex();
		treetablelistview.getSelectionModel().select(index + 1);
	}

}
