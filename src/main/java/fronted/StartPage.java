package fronted;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.*;

@Title("Clinic")
@Theme(ValoTheme.THEME_NAME)
public class StartPage extends VerticalLayout implements View{
	
	private Button doctorButton = new Button("Список врачей");
	private Button patientButton = new Button("Список пациентов");
	private Button prescriptionButton = new Button("Список рецептов");
	
	public StartPage(){
		showStartPage();
	}

	void showStartPage(){
    	HorizontalLayout mainLayout = new HorizontalLayout();
        doctorButton.addClickListener(e -> {
        	getUI().getNavigator().navigateTo("doctor");
        });
        patientButton.addClickListener(e -> {
        	getUI().getNavigator().navigateTo("patient");
        });
        prescriptionButton.addClickListener(e -> {
        	getUI().getNavigator().navigateTo("prescription");
        });
    	mainLayout.addComponents(doctorButton, patientButton, prescriptionButton);
    	mainLayout.setMargin(true);
    	mainLayout.setWidthFull();
    	addComponent(mainLayout);
    	setComponentAlignment(mainLayout, getDefaultComponentAlignment());
    }



}
