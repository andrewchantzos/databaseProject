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

import dao.PharmacyDAO;
import daoImpl.PharmacyDAOImpl;
import form.PharmacyForm;
import model.Pharmacy;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PharmacyView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PharmacyDAO pharmacyDao = new PharmacyDAOImpl();
	private Grid grid = new Grid();
	private PharmacyForm form = new PharmacyForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PharmacyView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Pharmacy> pharmacies = pharmacyDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Pharmacy.class, pharmacies));
		// Order firstName, lastName first
		grid.setColumnOrder("pharmacyId");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(
					new BeanItemContainer<>(Pharmacy.class, pharmacyDao.findAllFilter(e.getText())));
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

		Button addNewDoctor = new Button("Add new Pharmacy");
		addNewDoctor.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewDoctor.addClickListener(e -> {
			if (form.isVisible()) {
				form.setVisible(false);
			} else {
				grid.select(null);
				form.setPharmacy(new Pharmacy(), true);
				form.init();
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewDoctor);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		main.setHeight("15.5cm");

	}

	public void updateList() {
		List<Pharmacy> pharmacies = pharmacyDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Pharmacy.class, pharmacies));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Pharmacy Table");
		updateList();

		form.init();
		
		setMargin(true);
		setSpacing(true);

		form.setVisible(false);

		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				Pharmacy Pharmacy = (Pharmacy) e.getSelected().iterator().next();
				form.setPharmacy(Pharmacy, false);
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
