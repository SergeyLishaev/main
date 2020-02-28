package fronted;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import com.vaadin.flow.router.Route;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import backend.access.DoctorDAO;
import backend.entities.Doctor;


@Route("doctor")
public class DoctorPage extends HorizontalLayout implements View {
	private Button addButton = new Button("Добавить запись о враче");
	private Button updateButton = new Button("Редактировать запись о враче");
	private Button deleteButton = new Button("Удалить запись о враче");
	private Button backButton = new Button("Назад");
	private Button statisticButton = new Button("Показать статистику");
	private Doctor currentDoctor = null;
	private HorizontalLayout doctorLayout = new HorizontalLayout();
	private VerticalLayout buttonLayout = new VerticalLayout();
	private DoctorDAO doctorDao = new DoctorDAO();
	private Grid<Doctor> grid;

	public DoctorPage() {
		showTable();
	}
	private void showTable() {
		updatePage(doctorDao.showTable());
		backButton.addClickListener(event -> {
			getUI().getNavigator().navigateTo("");
		});
		addButton.addClickListener(event -> {
			EnterDoctorWindow enterWindow = new EnterDoctorWindow(this);
			getUI().addWindow(enterWindow);

		});

		deleteButton.addClickListener(event -> {
			if(currentDoctor != null){
				doctorDao.deleteEntity(currentDoctor);
				updatePage(doctorDao.showTable());
			}	
		});
		updateButton.addClickListener(event -> {
			if(currentDoctor != null){
				EnterDoctorWindow enterWindow = new EnterDoctorWindow(currentDoctor, this);
				getUI().addWindow(enterWindow);
			}

		});
		statisticButton.addClickListener(event -> {
			if(currentDoctor != null){
				EnterDoctorWindow enterWindow = null;
				try {
					enterWindow = new EnterDoctorWindow(currentDoctor);
				} catch (Exception e) {
					e.printStackTrace();
				}
				getUI().addWindow(enterWindow);
			}
		});
	}
	DoctorDAO getDoctorDao() {
		return doctorDao;
	}
	void updatePage(ArrayList<Doctor> doctors){
		doctorLayout.removeAllComponents();
		doctorLayout.setMargin(true);
		doctorLayout.setWidth("1000px");
		doctorLayout.setHeight("1000px");
		doctorLayout.setDefaultComponentAlignment(Alignment.TOP_RIGHT);		
		grid = new Grid<>();
		grid.setItems(doctors);
		grid.addColumn(Doctor::getSurname).setCaption("Фамилия");
		grid.addColumn(Doctor::getName).setCaption("Имя");
		grid.addColumn(Doctor::getPatronymic).setCaption("Отчество");
		grid.addColumn(Doctor::getSpecialization).setCaption("Специализация");
		grid.addSelectionListener(event -> {
			try{
				currentDoctor = event.getFirstSelectedItem().get();
			}catch(NoSuchElementException e){
				currentDoctor = null;
			}
			
		});
		addButton.setWidth("270");
		updateButton.setWidth("270");
		deleteButton.setWidth("270");
		backButton.setWidth("270");
		statisticButton.setWidth("270");
		buttonLayout.addComponents(addButton, updateButton, deleteButton, statisticButton, backButton);
		doctorLayout.addComponents(grid, buttonLayout);
		addComponent(doctorLayout);
	}

}
