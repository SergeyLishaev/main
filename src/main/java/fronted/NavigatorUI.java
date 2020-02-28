package fronted;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


public class NavigatorUI extends UI {
    Navigator navigator;
    private  final String DOCTORSVIEW = "doctor";
    private  final String PATIENTSVIEW = "patient";
    private  final String PRESCRIPTINVIEW = "prescription";
    public NavigatorUI(){
    	this.navigator = new Navigator(this, this);

    }
    @Override
    protected void init(VaadinRequest request) {	
        navigator.addView("", new StartPage());
        navigator.addView(DOCTORSVIEW, new DoctorPage());
        navigator.addView(PATIENTSVIEW, new PatientPage());
        try {
			navigator.addView(PRESCRIPTINVIEW, new PrescriptionPage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    

    
}
