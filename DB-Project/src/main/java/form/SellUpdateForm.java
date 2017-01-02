package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.SellDAO;
import daoImpl.SellDAOImpl;
import model.Sell;
import ui.SellView;

public class SellUpdateForm extends FormLayout {
	
	private static final long serialVersionUID = 1L;
	private TextField price = new TextField("Price");


	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private SellDAO sellDao = new SellDAOImpl();
	private Sell sell;
	private SellView myUI;

	public SellUpdateForm(SellView myUI) {
		this.myUI = myUI;

		// Set input prompts

		price.setConverter(Integer.class);
		price.setInputPrompt("Price");
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(price, buttons);

	}

	public void setSell(Sell sell) {
		this.sell = sell;
		BeanFieldGroup.bindFieldsUnbuffered(sell, this);

		// Show delete button only for persisted clients
		setVisible(true);
		price.selectAll();
	}

	private void save() {

		sellDao.update(sell);
		myUI.updateList();
		setVisible(false);
	}

	private void delete() {
		sellDao.delete(sell.getPharmacyId(), sell.getDrugId(), sell.getCompanyId());
		myUI.updateList();
		setVisible(false);
	}

}
