package fronted;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import com.vaadin.flow.router.Route;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import backend.access.PatientDAO;
import backend.entities.Patient;

@Route("patient")
public class PatientPage extends HorizontalLayout implements View{
	private Button addButton = new Button("Добавить запись о пациенте");
	private Button updateButton = new Button("Редактировать запись о пациенте");
	private Button deleteButton = new Button("Удалить запись о пациенте");
	private Button backButton = new Button("Назад");
	private Patient currentPatient = null;
	private HorizontalLayout patientLayout = new HorizontalLayout();
	private VerticalLayout buttonLayout = new VerticalLayout();
	private PatientDAO patientDao = new PatientDAO();
	private Grid<Patient> grid;
	
	public PatientPage() {
		showTable();
	}

	private void showTable() {
		updatePage(patientDao.showTable());
		backButton.addClickListener(event -> {
			getUI().getNavigator().navigateTo("");
		});
		addButton.addClickListener(event -> {
			EnterPatientWindow enterWindow = new EnterPatientWindow(this);
			getUI().addWindow(enterWindow);

		});

		deleteButton.addClickListener(event -> {
			if(currentPatient != null){
				patientDao.deleteEntity(currentPatient);
				updatePage(patientDao.showTable());
			}	
		});
		updateButton.addClickListener(event -> {
			if(currentPatient != null){
				EnterPatientWindow enterWindow = new EnterPatientWindow(currentPatient, this);
				getUI().addWindow(enterWindow);
			}

		});
	}
    PatientDAO getPatientDao() {
		return patientDao;
	}

	void updatePage(ArrayList<Patient> patients){
		patientLayout.removeAllComponents();
		patientLayout.setMargin(true);
		patientLayout.setWidth("1000px");
		patientLayout.setHeight("1000px");
		patientLayout.setDefaultComponentAlignment(Alignment.TOP_RIGHT);		
		grid = new Grid<>();
		grid.setItems(patients);
		grid.addColumn(Patient::getSurname).setCaption("Фамилия");
		grid.addColumn(Patient::getName).setCaption("Имя");
		grid.addColumn(Patient::getPatronymic).setCaption("Отчество");
		grid.addColumn(Patient::getPhoneNumber).setCaption("Номер телефона");
		grid.addSelectionListener(event -> {
			try{
				currentPatient = event.getFirstSelectedItem().get();
			}catch(NoSuchElementException e){
				currentPatient = null;
			}
			
		});
		addButton.setWidth("270");
		updateButton.setWidth("270");
		deleteButton.setWidth("270");
		backButton.setWidth("270");
		buttonLayout.addComponents(addButton, updateButton, deleteButton, backButton);
		patientLayout.addComponents(grid, buttonLayout);
		addComponent(patientLayout);
    }
}
