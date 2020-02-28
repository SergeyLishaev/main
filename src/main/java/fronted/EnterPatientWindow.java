package fronted;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import backend.entities.Doctor;
import backend.entities.Patient;
import backend.access.PatientDAO;

public class EnterPatientWindow extends Window{
	private TextField surname = new TextField("Фамилия:");
    private TextField name = new TextField("Имя:");
    private TextField patronymic = new TextField("Отчество:");
    private TextField phoneNumber = new TextField("Номер телефона (без кода страны):");
    private Label mainLabel = new Label("Введите данные:");
    private Button enterButton = new Button("");
    private Button cancelButton = new Button("Отмена", event -> close());
    private HorizontalLayout textLayout = new HorizontalLayout();
	private HorizontalLayout buttonLayout = new HorizontalLayout();
	private VerticalLayout enterLayout = new VerticalLayout();
	private Binder<Patient> binder = new Binder<Patient>(Patient.class);
	private TextValidator textValidator = new TextValidator();
    
    EnterPatientWindow(PatientPage page){
    	showEnterPage(page);
    }
    EnterPatientWindow(Patient patient, PatientPage page){
    	showUpdatePage(patient, page);
    }
    private void showEnterPage(PatientPage page){ 	
    	enterButton.setCaption("Добавить");
    	customizationPage();
    	enterButton.addClickListener(e -> {
    		String surnameText = surname.getValue();
    		String nameText = name.getValue();
    		String patronymicText = patronymic.getValue();
    		String phoneNumberText = phoneNumber.getValue();
    		checkTexts();
    		if(binder.isValid()){
    			Patient patient = new Patient(surnameText, nameText, patronymicText, "+7" + phoneNumberText);
        		PatientDAO patientDao = page.getPatientDao();
        		boolean flag = false;
        		try {
        			flag = patientDao.createEntity(patient);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
        		if(flag){
        			close();
            		page.updatePage(patientDao.showTable());
        		}
        		else Notification.show("Ошибка!",
		                  "Запись уже существует",
		                  Notification.Type.HUMANIZED_MESSAGE);
        		
    		}
    		
    	});
    }
    private void showUpdatePage(Patient patient, PatientPage page){
    	enterButton.setCaption("Изменить");
    	customizationPage();
    	surname.setValue(patient.getSurname());
    	name.setValue(patient.getName());
    	patronymic.setValue(patient.getPatronymic());
    	phoneNumber.setValue(patient.getPhoneNumber());
    	enterButton.addClickListener(e -> {
    		patient.setSurname(surname.getValue());
    		patient.setName(name.getValue());
    		patient.setPatronymic(patronymic.getValue());
    		patient.setPhoneNumber("+7" + phoneNumber.getValue());
    		checkTexts();
    		if(binder.isValid()){
    			PatientDAO patientDao = page.getPatientDao();
    			boolean flag = false;
        		try {
        			flag = patientDao.updateEntity(patient);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
        		if(flag){
        			close();
            		page.updatePage(patientDao.showTable());
        		}
        		else Notification.show("Ошибка!",
		                  "Запись уже существует",
		                  Notification.Type.HUMANIZED_MESSAGE);
    		}
    		
    	});
    }
    private void customizationPage(){
    	enterLayout.setHeight("400");
    	enterLayout.setWidth("900");
    	textLayout.addComponents(surname, name, patronymic, phoneNumber);
    	buttonLayout.addComponents(enterButton, cancelButton);
    	enterLayout.addComponents(mainLabel, textLayout, buttonLayout);
    	setContent(enterLayout);
    	center();
    }
	private void checkTexts() {
		binder.forField(surname).asRequired("Поле не должно быть пустым").withValidator(surname -> textValidator.checkText(surname, 25), "").bind(Patient::getSurname, Patient::setSurname);
		binder.forField(name).asRequired("Поле не должно быть пустым").withValidator(name -> textValidator.checkText(name, 25), "").bind(Patient::getName, Patient::setName);
		binder.forField(patronymic).asRequired("Поле не должно быть пустым").withValidator(patronymic -> textValidator.checkText(patronymic, 25), "").bind(Patient::getPatronymic,
				Patient::setPatronymic);
		binder.forField(phoneNumber).asRequired("Поле не должно быть пустым").withValidator(phoneNumber -> textValidator.checkPhoneNumber(phoneNumber), "").bind(Patient::getPhoneNumber,
				Patient::setPhoneNumber);
	}
}
