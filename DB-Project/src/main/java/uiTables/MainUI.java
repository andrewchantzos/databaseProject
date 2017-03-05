package uiTables;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import uiComponents.Views;
import uiQueries.DoctorsWithOldPatientsView;
import uiQueries.DrugCountPharmacyView;
import uiQueries.DrugCountPrescriptionView;
import uiQueries.DrugPriceQueryView;
import uiQueries.PatientsOfDoctorView;
import uiQueries.PharmaciesWithAllDrugsSameCityView;
import uiQueries.PharmaciesWithDrugInCityView;
import uiQueries.PharmacyTownPercentageView;
import uiQueries.SpecialityQueryView;
import uiQueries.ValidContractQueryView;
import uiViews.ElderPatientsView;

@Theme("mytheme")
public class MainUI extends UI {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Navigator navigator;

    protected static final String MAINVIEW = "main";

    @Override
    protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		//layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		
		
        navigator.addView(Views.StartingView.toString(), new StartingView(navigator));
        navigator.addView(Views.DoctorView.toString(), new DoctorView(navigator));
        navigator.addView(Views.DrugView.toString(), new DrugView(navigator));
        navigator.addView(Views.PatientView.toString(), new PatientView(navigator));
        navigator.addView(Views.CompanyView.toString(), new CompanyView(navigator));
        navigator.addView(Views.SellView.toString(), new SellView(navigator));
        navigator.addView(Views.PharmacyView.toString(), new PharmacyView(navigator));
        navigator.addView(Views.PrescriptionView.toString(), new PrescriptionView(navigator));
        navigator.addView(Views.ContractView.toString(), new ContractView(navigator));
        
        navigator.addView(Views.SpecialityQueryView.toString(), new SpecialityQueryView(navigator));
        navigator.addView(Views.ValidContractQueryView.toString(), new ValidContractQueryView(navigator));
        navigator.addView(Views.DrugPriceQueryView.toString(), new DrugPriceQueryView(navigator));
        navigator.addView(Views.PharmaciesWithAllDrugsSameCityView.toString(), new PharmaciesWithAllDrugsSameCityView(navigator));
        navigator.addView(Views.DoctorsWithOldPatientsView.toString(), new DoctorsWithOldPatientsView(navigator));
        navigator.addView(Views.DrugCountPrescriptionView.toString(), new DrugCountPrescriptionView(navigator));
        navigator.addView(Views.PatientsOfDoctorView.toString(), new PatientsOfDoctorView(navigator));
        navigator.addView(Views.DrugCountPharmacyView.toString(), new DrugCountPharmacyView(navigator));
        navigator.addView(Views.PharmaciesWithDrugInCityView.toString(), new PharmaciesWithDrugInCityView(navigator));
        navigator.addView(Views.PharmacyTownPercentageView.toString(), new PharmacyTownPercentageView(navigator));

        
        navigator.addView(Views.ElderPatientsView.toString(), new ElderPatientsView(navigator));

        navigator.navigateTo(Views.StartingView.toString());
        
    }

	@WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MainUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
}

		
