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

import dao.DrugDAO;
import daoImpl.DrugDAOImpl;
import form.DrugForm;
import model.Drug;
import uiComponents.MyComponents;

@Theme("mytheme")
public class DrugView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrugDAO drugDao = new DrugDAOImpl();
	private Grid grid = new Grid();
	private DrugForm form = new DrugForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DrugView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Drug> drugs = drugDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Drug.class, drugs));
		// Order firstName, lastName first
		grid.setColumnOrder("drugId", "name");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Drug.class, drugDao.findAllFilter(e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList();
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		Button addNewDrug = new Button("Add new drug");
		addNewDrug.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewDrug.addClickListener(e -> {
			if (form.isVisible()) {
				form.setVisible(false);
			} else {
				grid.select(null);
				form.setDrug(new Drug(), true);
				form.init();
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewDrug);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		
	}

	public void updateList() {
		List<Drug> drugs = drugDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Drug.class, drugs));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Drug Table");
		
		form.init();
		
		setMargin(true);
		setSpacing(true);

		form.setVisible(false);

		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				Drug drug = (Drug) e.getSelected().iterator().next();
				form.setDrug(drug, false);
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
