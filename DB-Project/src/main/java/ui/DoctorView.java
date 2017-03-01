package ui;

import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.DoctorDAO;
import daoImpl.DoctorDAOImpl;
import form.DoctorForm;
import model.Doctor;
import uiComponents.MyComponents;
import uiComponents.Views;

@Theme("mytheme")
public class DoctorView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoctorDAO doctorDao = new DoctorDAOImpl();
	private Grid grid = new Grid();
	private DoctorForm form = new DoctorForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DoctorView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Doctor> doctors = doctorDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Doctor.class, doctors));
		// Order firstName, lastName first
		grid.setColumnOrder("doctorId", "firstName", "lastName");


		// setup grid
		// Order firstName, lastName first
		grid.setColumnOrder("firstName", "lastName");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctorDao.findAllFilter(e.getText())));
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

		main.setExpandRatio(grid, 1);


		Button addNewDoctor = new Button("Add new doctor");
		addNewDoctor.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewDoctor.addClickListener(e -> {
			if (form.isVisible()) {
				form.setVisible(false);
			} else {
				grid.select(null);
				form.setDoctor(new Doctor(), true);
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewDoctor);
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
				Doctor doctor = (Doctor) e.getSelected().iterator().next();
				form.setDoctor(doctor, false);
			}
		});

	}

	public void updateList() {
		List<Doctor> doctors = doctorDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctors));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Doctor Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
