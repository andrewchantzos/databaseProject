package form;

import java.sql.SQLIntegrityConstraintViolationException;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.SellDAO;
import daoImpl.SellDAOImpl;
import model.Sell;
import ui.SellView;
import validators.CustomValidators;

public class SellUpdateForm extends FormLayout {
	
	private static final long serialVersionUID = 1L;
	private TextField price = new TextField("Price");


	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private SellDAO sellDao = new SellDAOImpl();
	private Sell sell;
	private SellView myUI;
	private FieldGroup fieldGroup;

	public SellUpdateForm(SellView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();

		fieldGroup.bind(price, price);
		
		price.addValidator(CustomValidators.priceValidator());		

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

	@SuppressWarnings("unchecked")
	private void save() {

		delete.setVisible(false);
		try {
			for (Field<?> field : fieldGroup.getFields())
				((AbstractField<String>) field).setValidationVisible(true);
			this.fieldGroup.commit();
		} catch (CommitException e) {
			// Show all the validate errors:
			Notification.show("Invalid input", Notification.Type.WARNING_MESSAGE);

			return;
		}
		try {
			sellDao.update(sell);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("UPDATE FAILED", "Update with Invalid ID", Notification.Type.WARNING_MESSAGE);
		}
	}

	private void delete() {
		sellDao.delete(sell.getPharmacyId(), sell.getDrugId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
