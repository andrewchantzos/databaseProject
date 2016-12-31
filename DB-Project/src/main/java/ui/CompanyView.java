package ui;


import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.PharmaceuticalCompanyDAO;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import form.CompanyForm;
import model.PharmaceuticalCompany;
import uiComponents.MyComponents;

@Theme("mytheme")
public class CompanyView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	private Grid grid = new Grid();
	private CompanyForm form = new CompanyForm(this);
	private Navigator navigator;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompanyView(Navigator navigator) {

		this.setNavigator(navigator);

        
		List<PharmaceuticalCompany> companies = companyDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(PharmaceuticalCompany.class, companies));
		// Order firstName, lastName first
		grid.setColumnOrder("pharmaceuticalCompanyId");

		CssLayout filtering = new CssLayout();

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


		
		Button addNewDoctor = new Button("Add new Company");
		addNewDoctor.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewDoctor.addClickListener(e -> {
			grid.select(null);
			form.setCompany(new PharmaceuticalCompany(), true);
		});



		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, addNewDoctor);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		setMargin(true);
		setSpacing(true);

		form.setVisible(false);
		
		
		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				PharmaceuticalCompany company = (PharmaceuticalCompany) e.getSelected().iterator().next();
				form.setCompany(company, false);
			}
		});
	
	}

	public void updateList() {
		List<PharmaceuticalCompany> companies = companyDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(PharmaceuticalCompany.class, companies));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Company Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}

