package fronted;

import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import backend.access.PrescriptionDAO;
import backend.entities.Doctor;
import backend.entities.FullName;
import backend.entities.Prescription;

public class EnterPrescriptionWindow extends Window{
	private TextField description = new TextField("Описание:");
    private ComboBox<FullName> patientBox = new ComboBox<FullName>("Список пациентов");
    private ComboBox<FullName> doctorBox = new ComboBox<FullName>("Список врачей");
    private TextField date = new TextField("Дата создания:");
    private TextField validity = new TextField("Срок действия:");
    private ComboBox<String> priorityBox = new ComboBox<String>("Тип приоритета");
    private Label mainLabel = new Label("Введите данные:");
    private Button enterButton = new Button("");
    private Button cancelButton = new Button("Отмена", event -> close());
    private VerticalLayout enterLayout = new VerticalLayout();
    private HorizontalLayout comboBoxLayout = new HorizontalLayout();
    private HorizontalLayout textLayout = new HorizontalLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Binder<Prescription> binder = new Binder<Prescription>(Prescription.class);
	private TextValidator textValidator = new TextValidator();
    
    EnterPrescriptionWindow(boolean flag, PrescriptionPage page){
    	if(flag){
    		showEnterPage(page);
    	}
    	else{
    		showFilterWindow(page);
    	}
    	
    }
    EnterPrescriptionWindow(Prescription prescription, PrescriptionPage page){
    	showUpdatePage(prescription, page);
    }
    private void showEnterPage(PrescriptionPage page){
    	enterButton.setCaption("Добавить");
    	customizationPage();
    	enterButton.addClickListener(e -> {
    		String descriptionText = description.getValue();
    		String dateText = date.getValue();
    		String validityText = validity.getValue();
    		FullName patientText = patientBox.getSelectedItem().get();
    		FullName doctorText = doctorBox.getSelectedItem().get();
    		String priorityText = priorityBox.getSelectedItem().get();
    		checkTexts();
    		if(binder.isValid()){
    			Prescription prescription = new Prescription(descriptionText, patientText, doctorText, dateText, validityText, priorityText);
        		PrescriptionDAO prescriptionDao = page.getPrescriptionDao();
        		boolean flag = false;
        		try {			
    				flag = prescriptionDao.createEntity(prescription);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
        		if(flag){
        			close();
            		try {
    					page.updatePage(prescriptionDao.showTable());
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}
        		else Notification.show("Ошибка!",
		                  "Запись уже существует",
		                  Notification.Type.HUMANIZED_MESSAGE);
        		
    		}
    		
    	});
    }
    private void showUpdatePage(Prescription prescription, PrescriptionPage page){
    	enterButton.setCaption("Изменить");
    	customizationPage();
    	description.setValue(prescription.getDescription());
    	date.setValue(prescription.getDate());
    	validity.setValue(prescription.getValidity());
    	enterButton.addClickListener(e -> {
    		prescription.setDescription(description.getValue());
    		prescription.setPatient(patientBox.getSelectedItem().get());
    		prescription.setDoctor(doctorBox.getSelectedItem().get());
    		prescription.setDate(date.getValue());
    		prescription.setValidity(validity.getValue());
    		prescription.setPriority(priorityBox.getSelectedItem().get());
    		checkTexts();
    		if(binder.isValid()){
    			PrescriptionDAO prescriptionDao = page.getPrescriptionDao();
    			boolean flag = false;
        		try {
        			flag = prescriptionDao.updateEntity(prescription);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
        		if(flag){
        			close();
            		try {
    					page.updatePage(prescriptionDao.showTable());
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}
        		else Notification.show("Ошибка!",
		                  "Запись уже существует",
		                  Notification.Type.HUMANIZED_MESSAGE);
    		} 		
    	});

    }
    private void fillComboBox(){
    	ArrayList<String> priority = new ArrayList<String>();
    	priority.add("Cito");
    	priority.add("Normal");
    	priority.add("Statim");
    	PrescriptionDAO prescriptionDao = new PrescriptionDAO();
    	ArrayList<FullName> doctorsNames = prescriptionDao.getDoctorFullName();
    	ArrayList<FullName> patientsNames = prescriptionDao.getPatientFullName();
    	patientBox.setWidth("300");
    	doctorBox.setWidth("300");
    	priorityBox.setItems(priority);
    	patientBox.setItems(patientsNames);
    	doctorBox.setItems(doctorsNames);
    	patientBox.setItemCaptionGenerator(FullName::getFullName);
    	doctorBox.setItemCaptionGenerator(FullName::getFullName);
    }
    private void fillComboBoxForFilter(){
    	ArrayList<String> priority = new ArrayList<String>();
    	priority.add("Cito");
    	priority.add("Normal");
    	priority.add("Statim");
    	PrescriptionDAO prescriptionDao = new PrescriptionDAO();
    	ArrayList<FullName> patientsNames = prescriptionDao.getPatientFullName();
    	patientBox.setWidth("300");
    	priorityBox.setItems(priority);
    	patientBox.setItems(patientsNames);
    	patientBox.setItemCaptionGenerator(FullName::getFullName);
    }
    private void showFilterWindow(PrescriptionPage page){
    	enterButton.setCaption("Отфильтровать");
    	enterLayout.setHeight("400");
    	enterLayout.setWidth("800");
    	fillComboBoxForFilter();
    	enterButton.addClickListener(e -> {
    		String descriptionText = description.getValue();
    		Long idPatient = patientBox.getSelectedItem().get().getId();
    		String priorityText = priorityBox.getSelectedItem().get();
    		if(binder.isValid()){
    			PrescriptionDAO prescriptionDao = new PrescriptionDAO();
        		close();
        		try {
					page.updatePage(prescriptionDao.showFilterTable(idPatient, descriptionText, priorityText));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}	
    	});  	
     	textLayout.addComponents(description, patientBox, priorityBox);
    	buttonLayout.addComponents(enterButton, cancelButton);
    	enterLayout.addComponents(mainLabel, textLayout, buttonLayout);
    	setContent(enterLayout);
    	center();
    }
    private void customizationPage(){
    	enterLayout.setHeight("400");
    	enterLayout.setWidth("900");  
    	description.setWidth("415");
    	fillComboBox();
    	comboBoxLayout.addComponents(patientBox, doctorBox, priorityBox);
    	textLayout.addComponents(description, date, validity);
    	buttonLayout.addComponents(enterButton, cancelButton);
    	enterLayout.addComponents(mainLabel, comboBoxLayout, textLayout, buttonLayout);
    	setContent(enterLayout);
    	center();
    }
	private void checkTexts() {
		binder.forField(description).asRequired("Поле не должно быть пустым").withValidator(description -> textValidator.checkText(description, 255), "").bind(Prescription::getDescription, Prescription::setDescription);
		binder.forField(date).asRequired("Поле не должно быть пустым").withValidator(date -> textValidator.checkDate(date), "").bind(Prescription::getDate, Prescription::setDate);
		binder.forField(validity).asRequired("Поле не должно быть пустым").withValidator(validity -> textValidator.checkValidity(validity, 13), "").bind(Prescription::getValidity,
				Prescription::setValidity);
	}
}
