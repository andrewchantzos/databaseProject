package uiTables;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import uiComponents.Descriptions;
import uiComponents.Views;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class StartingView extends VerticalLayout implements View {


	private static final long serialVersionUID = 1L;

	protected static final String MAINVIEW = "main";

	public StartingView(Navigator navigator) {

		MenuBar menu = new MenuBar();

		
		ThemeResource resource = new ThemeResource("images/pill3.png");
		Image image = new Image("",resource);

		addComponent(image);

		// Define a common menu command for all the menu items.
		@SuppressWarnings("serial")
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String view = selectedItem.getText() + "View";
				navigator.navigateTo(view);
			}
		};

		MenuBar.Command validContractCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("ValidContractQueryView");
			}
		};
		
		
		MenuBar.Command drugPriceInfoCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.DrugPriceQueryView.toString());
			}
		};
		
		
		MenuBar.Command pharmacyPatientCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.PharmaciesWithAllDrugsSameCityView.toString());
			}
		};
		
		MenuBar.Command specialityCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("SpecialityQueryView/" + selectedItem.getText());
			}
		};
		
		
		MenuBar.Command doctorWithOldPatientCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.DoctorsWithOldPatientsView.toString());
			}
		};
		
		MenuBar.Command drugPrescriptionCountCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.DrugCountPrescriptionView.toString());
			}
		};
		
		MenuBar.Command patientsOfDoctorCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.PatientsOfDoctorView.toString());
			}
		};
		
		MenuBar.Command pharmaciesWithDrugInCity = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.PharmaciesWithDrugInCityView.toString());
			}
		};
		
		MenuBar.Command drugCountCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.DrugCountPharmacyView.toString());
			}
		};
		
		MenuBar.Command elderPatientsCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.ElderPatientsView.toString());
			}
		};
		
		MenuBar.Command pharmacyTownPercentageCommand = new MenuBar.Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(Views.PharmacyTownPercentageView.toString());
			}
		};
		
		MenuItem tables = menu.addItem("Tables", null);
		tables.addItem("Doctor", mycommand);
		tables.addItem("Patient", mycommand);
		tables.addItem("Prescription", mycommand);
		tables.addItem("Contract", mycommand);
		tables.addItem("Drug", mycommand);
		tables.addItem("Pharmacy", mycommand);
		tables.addItem("Sell", mycommand);
		tables.addItem("Company", mycommand);

		MenuItem queries = menu.addItem("Queries", null);
		
		MenuItem validContracts = queries.addItem("Valid Contracts", validContractCommand);
		MenuItem drugPriceInfo = queries.addItem("Drug Price Info", drugPriceInfoCommand);
		MenuItem pharmaciesWithAllDrugs = queries.addItem("Pharmacies in City with all Drugs", pharmacyPatientCommand);
		MenuItem doctorsWithHighAverage = queries.addItem("Doctors with average Patient age over 60", doctorWithOldPatientCommand);
		MenuItem drugsSortedPrescCount = queries.addItem("Drugs sorted by prescription count", drugPrescriptionCountCommand);
		MenuItem drugCountInPharmacies = queries.addItem("Number Of Drugs in Pharmacies", drugCountCommand);
		MenuItem patientsOfDoctor = queries.addItem("Patients of Doctor", patientsOfDoctorCommand);
		MenuItem pharmaciesWithDrugInCityItem = queries.addItem("Pharmacies with Drug in City", pharmaciesWithDrugInCity);
		MenuItem pharmacyTownPercentageView = queries.addItem("Percentage of Pharmacies in City", pharmacyTownPercentageCommand);
		
		validContracts.setDescription(Descriptions.validContracts);
		drugPriceInfo.setDescription(Descriptions.drugPriceInfo);
		pharmaciesWithAllDrugs.setDescription(Descriptions.pharmaciesWithAllDrugs);
		doctorsWithHighAverage.setDescription(Descriptions.doctorsWithHighAverage);
		drugsSortedPrescCount.setDescription(Descriptions.drugsSortedPrescCount);
		drugCountInPharmacies.setDescription(Descriptions.drugCountInPharmacies);
		pharmaciesWithDrugInCityItem.setDescription(Descriptions.pharmaciesWithDrugInCityItem);
		patientsOfDoctor.setDescription(Descriptions.patientsOfDoctor);
		pharmacyTownPercentageView.setDescription(Descriptions.pharmacyTownPercentage);

		

		MenuItem doctorsBySpeciality = menu.addItem("Find Doctors", null);
		doctorsBySpeciality.setDescription(Descriptions.doctorsBySpeciality);
	
		/* 
		 * Add doctors to doctorBySpeciality
		 */
		
		doctorsBySpeciality.addItem("Pathology", specialityCommand);
		doctorsBySpeciality.addItem("Cardiology", specialityCommand);
		doctorsBySpeciality.addItem("Gastroenterology", specialityCommand);
		doctorsBySpeciality.addItem("General practice", specialityCommand);
		doctorsBySpeciality.addItem("Neurology", specialityCommand);
		doctorsBySpeciality.addItem("Orthopaedics", specialityCommand);
		doctorsBySpeciality.addItem("Ophthalmology", specialityCommand);
		doctorsBySpeciality.addItem("Otorhinolaryngology", specialityCommand);
		doctorsBySpeciality.addItem("Surgeon", specialityCommand);
		doctorsBySpeciality.addItem("Paediatrics", specialityCommand);
		doctorsBySpeciality.addItem("Psychiatry", specialityCommand);
		doctorsBySpeciality.addItem("Urology", specialityCommand);

		MenuItem views = menu.addItem("Views", null);
		views.addItem("Elder Patients", elderPatientsCommand);

		
		addComponent(menu);
		setComponentAlignment(menu, Alignment.MIDDLE_CENTER);
		setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		setSizeFull();
		setSpacing(true);
		
		setMargin(new MarginInfo(false, true, true, true));

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
