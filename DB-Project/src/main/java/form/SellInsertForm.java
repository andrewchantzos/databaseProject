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

public class SellInsertForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private TextField price = new TextField("Price");
	private TextField companyId = new TextField("Company Id");
	private TextField drugId = new TextField("Drug Id");
	private TextField pharmacyId = new TextField("Pharmacy Id");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private SellDAO sellDao = new SellDAOImpl();
	private Sell sell;
	private SellView myUI;
	private FieldGroup fieldGroup;

	public SellInsertForm(SellView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();
		fieldGroup.bind(pharmacyId, pharmacyId);
		fieldGroup.bind(price, price);
		fieldGroup.bind(companyId, companyId);
		fieldGroup.bind(drugId, drugId);

		companyId.addValidator(CustomValidators.idValidator());
		pharmacyId.addValidator(CustomValidators.idValidator());
		drugId.addValidator(CustomValidators.idValidator());
		price.addValidator(CustomValidators.priceValidator());

		price.setConverter(Integer.class);
		price.setInputPrompt("Price");
		companyId.setConverter(Integer.class);
		drugId.setConverter(Integer.class);
		pharmacyId.setConverter(Integer.class);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(companyId, drugId, pharmacyId, price, buttons);

	}

	public void setSell(Sell sell, boolean insert) {
		this.sell = sell;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(sell, this);

		// Show delete button only for persisted clients
		delete.setVisible(false);
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
		if (insert) {

			try {
				sellDao.insert(sell);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {
				Notification.show("ADD FAILED", "Add with Invalid ID", Notification.Type.WARNING_MESSAGE);
			}
		} else
			try {
				sellDao.update(sell);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {
				Notification.show("UPDATE FAILED", "Update with Invalid ID", Notification.Type.WARNING_MESSAGE);
			}

	}

	private void delete() {
		sellDao.delete(sell.getPharmacyId(), sell.getDrugId(), sell.getCompanyId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
