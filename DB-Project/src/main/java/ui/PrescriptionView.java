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

import dao.PrescriptionDAO;
import daoImpl.PrescriptionDAOImpl;
import form.PrescriptionInsertForm;
import form.PrescriptionUpdateForm;
import model.Prescription;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PrescriptionView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrescriptionDAO prescriptionDao = new PrescriptionDAOImpl();
	private Grid grid = new Grid();
	private PrescriptionInsertForm insertForm = new PrescriptionInsertForm(this);
	private PrescriptionUpdateForm updateForm = new PrescriptionUpdateForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PrescriptionView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Prescription> prescriptions = prescriptionDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Prescription.class, prescriptions));

		grid.setColumnOrder("patientId", "doctorId", "drugId", "quantity");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(
					new BeanItemContainer<>(Prescription.class, prescriptionDao.findAllFilter(e.getText())));
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

		Button addNewPrescription = new Button("Add new prescription");
		addNewPrescription.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewPrescription.addClickListener(e -> {
			if (insertForm.isVisible()) {
				insertForm.setVisible(false);
			} else {
				grid.select(null);
				insertForm.setPrescription(new Prescription(), true);
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewPrescription);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

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
				Prescription prescription = (Prescription) e.getSelected().iterator().next();
				updateForm.setPrescription(prescription);
			}
		});

	}

	public void updateList() {
		List<Prescription> prescriptions = prescriptionDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Prescription.class, prescriptions));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Prescription Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
