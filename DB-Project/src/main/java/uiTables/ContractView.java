package uiTables;

import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.ContractDAO;
import daoImpl.ContractDAOImpl;
import form.ContractInsertForm;
import form.ContractUpdateForm;
import model.Contract;
import uiComponents.MyComponents;

@Theme("mytheme")
public class ContractView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContractDAO contractDao = new ContractDAOImpl();
	private Grid grid = new Grid();
	private ContractInsertForm insertForm = new ContractInsertForm(this);
	private ContractUpdateForm updateForm = new ContractUpdateForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ContractView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Contract> contracts = contractDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Contract.class, contracts));

		grid.setColumnOrder("pharmacyId", "pharmaceuticalCompanyId", "supervisor", "startDate", "endDate");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(
					new BeanItemContainer<>(Contract.class, contractDao.findAllFilter(e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList();
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout main = new HorizontalLayout(grid, insertForm, updateForm);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		Button addNewContract = new Button("Add new contract");
		addNewContract.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewContract.addClickListener(e -> {
			if (insertForm.isVisible()) {
				insertForm.setVisible(false);
			} else {
				grid.select(null);
				insertForm.setContract(new Contract(), true);
				insertForm.init();
				updateForm.init();
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewContract);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);
		main.setHeight("15.5cm");

	}

	public void updateList() {
		List<Contract> contracts = contractDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Contract.class, contracts));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Contract Table");
		updateList();

		insertForm.init();
		updateForm.init();

		setMargin(true);
		setSpacing(true);

		insertForm.setVisible(false);
		updateForm.setVisible(false);
		
		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				updateForm.setVisible(false);
			} else {
				if (insertForm.isVisible())
					insertForm.setVisible(false);
				Contract contract = (Contract) e.getSelected().iterator().next();
				updateForm.setContract(contract);
			}
		});


	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
