package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.ContractDAO;
import daoImpl.ContractDAOImpl;
import model.Contract;
import ui.ContractView;

public class ContractUpdateForm extends FormLayout {
	
	private static final long serialVersionUID = 1L;
	private PopupDateField startDate = new PopupDateField("Start Date");
	private PopupDateField endDate = new PopupDateField("End Date");

	private TextField supervisor = new TextField("Supervisor");
	private TextArea text = new TextArea("Description");
	
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private ContractDAO contractDao = new ContractDAOImpl();
	private Contract contract;
	private ContractView myUI;
	
	
	public ContractUpdateForm(ContractView myUI) {
		this.myUI = myUI;


		
		supervisor.setInputPrompt("Supervisor");
		text.setCaption("Contract Description");
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		
		buttons.setSpacing(true);
		addComponents(supervisor, startDate, endDate, text, buttons);
	}
	
	public void setContract(Contract contract) {
		this.contract = contract;
		BeanFieldGroup.bindFieldsUnbuffered(contract, this);
		
		
		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		supervisor.selectAll();
	}
	
	private void save() {

		contractDao.update(contract);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		contractDao.delete(contract.getPharmacyId(), contract.getPharmaceuticalCopmanyId());
		myUI.updateList();
		setVisible(false);
	}
}
