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

public class ContractInsertForm  extends FormLayout {
	

	private static final long serialVersionUID = 1L;
	private TextField pharmaceuticalCopmanyId = new TextField("Company Id");
	private TextField pharmacyId = new TextField("Pharmacy Id");
	private PopupDateField startDate = new PopupDateField("Start Date");
	private PopupDateField endDate = new PopupDateField("End Date");

	//private TextField startDate = new TextField("Lastname");
	//private TextField endDate = new TextField("Speciality");
	private TextField supervisor = new TextField("Supervisor");
	private TextArea text = new TextArea("Description");
	
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;
	
	private ContractDAO contractDao = new ContractDAOImpl();
	private Contract contract;
	private ContractView myUI;
	
	
	public ContractInsertForm(ContractView myUI) {
		this.myUI = myUI;

		// Set input prompts
		pharmaceuticalCopmanyId.setConverter(Integer.class);
		pharmacyId.setConverter(Integer.class);
		pharmaceuticalCopmanyId.setInputPrompt("Company Id");
		pharmacyId.setInputPrompt("Pharmacy Id");
		
		supervisor.setInputPrompt("Supervisor");
		text.setCaption("Contract Description");
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		
		buttons.setSpacing(true);
		addComponents(pharmaceuticalCopmanyId, pharmacyId, supervisor, startDate, endDate, text, buttons);
	}
	
	public void setContract(Contract contract, boolean insert) {
		this.contract = contract;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(contract, this);
		
		
		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		pharmacyId.selectAll();
	}
	
	private void save() {
		if (insert)
			contractDao.insert(contract);
		else
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
