package ui;

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

import form.SelectDoctorForm;
import model.Patient;
import sqlQueries.Queries;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PatientsOfDoctorView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Queries queries = new Queries();
	private Grid grid = new Grid();
	private Navigator navigator;
	private int doctorId;
	private List<Patient> patients;


	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	private TextField filterText = new TextField();
	private SelectDoctorForm form = new SelectDoctorForm(this);

	private HorizontalLayout toolbar;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PatientsOfDoctorView(Navigator navigator) {

		this.setNavigator(navigator);

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Patient.class, patients));
		grid.setColumnOrder("patientId", "firstName", "lastName");
		grid.removeColumn("doctorId");
		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, queries.doctorPatientsFilter(doctorId, e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList(doctorId);
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		VerticalLayout main = new VerticalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		setMargin(true);
		setSpacing(true);

		Button home = MyComponents.homeButton(navigator);
		toolbar = new HorizontalLayout(home, filtering);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);
	}

	public void updateList(int id) {
		List<Patient> patients = queries.doctorPatients(id);

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, patients));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Patients Of Doctor Table");
		form.init();
		grid.setVisible(false);
		toolbar.setVisible(false);
		form.setVisible(true);
	}

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries queries) {
		this.queries = queries;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public TextField getFilterText() {
		return filterText;
	}

	public void setFilterText(TextField filterText) {
		this.filterText = filterText;
	}

	public SelectDoctorForm getForm() {
		return form;
	}

	public void setForm(SelectDoctorForm form) {
		this.form = form;
	}

	public HorizontalLayout getToolbar() {
		return toolbar;
	}

	public void setToolbar(HorizontalLayout toolbar) {
		this.toolbar = toolbar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public List<Patient> getPatients() {
		return patients;
	}
	
	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
}
