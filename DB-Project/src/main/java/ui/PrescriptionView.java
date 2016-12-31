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

import dao.PrescriptionDAO;
import daoImpl.PrescriptionDAOImpl;
import form.PrescriptionForm;
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
	private PrescriptionForm form = new PrescriptionForm(this);
	private Navigator navigator;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PrescriptionView(Navigator navigator) {

		this.setNavigator(navigator);

        
		List<Prescription> prescriptions = prescriptionDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Prescription.class, prescriptions));

		grid.setColumnOrder("patientId", "doctorId", "drugId", "quantity");

		CssLayout filtering = new CssLayout();

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


		
		Button addNewPrescription = new Button("Add new prescription");
		addNewPrescription.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewPrescription.addClickListener(e -> {
			grid.select(null);
			form.setPrescription(new Prescription(), true);
		});



		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, addNewPrescription);
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
				Prescription prescription = (Prescription) e.getSelected().iterator().next();
				form.setPrescription(prescription, false);
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

