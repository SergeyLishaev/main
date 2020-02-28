package fronted;

import java.sql.SQLException;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import backend.access.DoctorDAO;
import backend.entities.Doctor;

public class EnterDoctorWindow extends Window {
	private TextField surname = new TextField("Фамилия:");
	private TextField name = new TextField("Имя:");
	private TextField patronymic = new TextField("Отчество:");
	private TextField specialization = new TextField("Специализация:");
	private Label mainLabel = new Label("Введите данные:");
	private Button enterButton = new Button("");
	private Button cancelButton = new Button("Отмена", event -> close());
	private HorizontalLayout textLayout = new HorizontalLayout();
	private HorizontalLayout buttonLayout = new HorizontalLayout();
	private VerticalLayout enterLayout = new VerticalLayout();
	private Binder<Doctor> binder = new Binder<Doctor>(Doctor.class);
	private TextValidator textValidator = new TextValidator();

	EnterDoctorWindow(DoctorPage page) {
		showEnterPage(page);
	}

	EnterDoctorWindow(Doctor doctor, DoctorPage page) {
		showUpdatePage(doctor, page);
	}
	EnterDoctorWindow(Doctor doctor) throws SQLException {
		showCountPrescriptions(doctor);
	}

	private void showEnterPage(DoctorPage page) {
		enterButton.setCaption("Добавить");
		customizationPage();
		enterButton.addClickListener(e -> {
			String surnameText = surname.getValue();
			String nameText = name.getValue();
			String patronymicText = patronymic.getValue();
			String specializationText = specialization.getValue();
			checkTexts();		
			if (binder.isValid()) {
				Doctor doctor = new Doctor(surnameText, nameText, patronymicText, specializationText);
				DoctorDAO doctorDao = page.getDoctorDao();
				boolean flag = false;
				try {
					flag = doctorDao.createEntity(doctor);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(flag){
					close();
					page.updatePage(doctorDao.showTable());
				}
				else Notification.show("Ошибка!",
		                  "Запись уже существует",
		                  Notification.Type.HUMANIZED_MESSAGE);
				
			}
		});
	}

	private void showUpdatePage(Doctor doctor, DoctorPage page) {
		enterButton.setCaption("Изменить");
		customizationPage();
		surname.setValue(doctor.getSurname());
		name.setValue(doctor.getName());
		patronymic.setValue(doctor.getPatronymic());
		specialization.setValue(doctor.getSpecialization());
		enterButton.addClickListener(e -> {		
				doctor.setSurname(surname.getValue());
				doctor.setName(name.getValue());
				doctor.setPatronymic(patronymic.getValue());
				doctor.setSpecialization(specialization.getValue());
				checkTexts();
				if(binder.isValid()){
					DoctorDAO doctorDao = page.getDoctorDao();
					boolean flag = false;
					try {
						flag = doctorDao.updateEntity(doctor);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(flag){
						close();
						page.updatePage(doctorDao.showTable());
					}
					else Notification.show("Ошибка!",
			                  "Запись уже существует",
			                  Notification.Type.HUMANIZED_MESSAGE);
					
				}
			
		});
	}
	private void showCountPrescriptions(Doctor doctor) throws SQLException{
		enterLayout.setHeight("200");
		enterLayout.setWidth("400");
		DoctorDAO doctorDao = new DoctorDAO();
		Label countLabel = new Label("Количество выписанных рецептов: " + doctorDao.showCount(doctor));
		cancelButton.setWidth("350");
		enterLayout.addComponents(countLabel, cancelButton);
		setContent(enterLayout);
		center();
	}
	private void customizationPage() {
		enterLayout.setHeight("400");
		enterLayout.setWidth("800");
		textLayout.addComponents(surname, name, patronymic, specialization);
		buttonLayout.addComponents(enterButton, cancelButton);
		enterLayout.addComponents(mainLabel, textLayout, buttonLayout);
		setContent(enterLayout);
		center();
	}

	private void checkTexts() {
		binder.forField(surname).asRequired("Поле не должно быть пустым").withValidator(surname -> textValidator.checkText(surname, 25), "").bind(Doctor::getSurname, Doctor::setSurname);
		binder.forField(name).asRequired("Поле не должно быть пустым").withValidator(name -> textValidator.checkText(name, 25), "").bind(Doctor::getName, Doctor::setName);
		binder.forField(patronymic).asRequired("Поле не должно быть пустым").withValidator(patronymic -> textValidator.checkText(patronymic, 25), "").bind(Doctor::getPatronymic,
				Doctor::setPatronymic);
		binder.forField(specialization).asRequired("Поле не должно быть пустым").withValidator(specialization -> textValidator.checkText(specialization, 50), "").bind(Doctor::getSpecialization,
				Doctor::setSpecialization);
	}
}
