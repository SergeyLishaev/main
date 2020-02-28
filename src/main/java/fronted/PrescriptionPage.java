package fronted;

import java.sql.SQLException;
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
import backend.access.PrescriptionDAO;
import backend.entities.FullName;
import backend.entities.Prescription;

@Route("prescription")
public class PrescriptionPage extends VerticalLayout implements View {
	
	private Button addButton = new Button("Добавить запись о рецепте");
	private Button updateButton = new Button("Редактировать запись о рецепте");
	private Button deleteButton = new Button("Удалить запись о рецепте");
	private Button backButton = new Button("Назад");
	private Button filterButton = new Button("Панель фильра");
	private Button cancelFilterButton = new Button("Отменить фильтр");
	private Prescription currentPrescription = null;
	private PrescriptionDAO prescriptionDao = new PrescriptionDAO();
	private Grid<Prescription> grid;
	private HorizontalLayout buttonLayout = new HorizontalLayout();
	private VerticalLayout prescriptionLayout = new VerticalLayout();
	private Label countLabel = new Label("");
	
	public PrescriptionPage() throws SQLException {
		showTable();
	}

	private void showTable() throws SQLException {
		updatePage(prescriptionDao.showTable());
		backButton.addClickListener(event -> {
			getUI().getNavigator().navigateTo("");
		});
		addButton.addClickListener(event -> {
			EnterPrescriptionWindow enterWindow = new EnterPrescriptionWindow(true, this);
			getUI().addWindow(enterWindow);

		});

		deleteButton.addClickListener(event -> {
			if(currentPrescription != null){
				prescriptionDao.deleteEntity(currentPrescription);
				try {
					updatePage(prescriptionDao.showTable());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
		updateButton.addClickListener(event -> {
			if(currentPrescription != null){
				EnterPrescriptionWindow enterWindow = new EnterPrescriptionWindow(currentPrescription, this);
				getUI().addWindow(enterWindow);
			}

		});
		filterButton.addClickListener(event -> {
			EnterPrescriptionWindow enterWindow = new EnterPrescriptionWindow(false, this);
			getUI().addWindow(enterWindow);

		});		
		cancelFilterButton.addClickListener(event -> {
			try {
				updatePage(prescriptionDao.showTable());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});	
	}
	public PrescriptionDAO getPrescriptionDao() {
		return prescriptionDao;
	}
	void updatePage(ArrayList<Prescription> prescriptions) throws SQLException{
		prescriptionLayout.removeAllComponents();
		prescriptionLayout.setWidth("1300px");
		prescriptionLayout.setHeight("900px");
		grid = new Grid<>();
		grid.setItems(prescriptions);
		grid.addColumn(Prescription::getDescription).setCaption("Описание");
		grid.addColumn(prescription -> prescription.getPatient().getFullName()).setCaption("Имя пациента");
		grid.addColumn(prescription -> prescription.getDoctor().getFullName()).setCaption("Имя врача");
		grid.addColumn(Prescription::getDate).setCaption("Дата создания");
		grid.addColumn(Prescription::getValidity).setCaption("Срок действия");
		grid.addColumn(Prescription::getPriority).setCaption("Приоритет");
		grid.addSelectionListener(event -> {
			try{
				currentPrescription = event.getFirstSelectedItem().get();
			}catch(NoSuchElementException e){
				currentPrescription = null;
			}
			
		});
		countLabel.setCaption("Общее количество рецептов: " + prescriptionDao.getCountPrescriptions());
		buttonLayout.addComponents(cancelFilterButton, filterButton, addButton, updateButton, deleteButton, backButton);
		grid.setWidth("1200");
		prescriptionLayout.addComponents(grid, buttonLayout);
		addComponents(countLabel, prescriptionLayout);
	}
}
