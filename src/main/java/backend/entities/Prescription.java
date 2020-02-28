package backend.entities;

public class Prescription extends AbstractEntity{
	private Long id;
	private String description;
	private FullName patient;
	private FullName doctor;
	private String date;
	private String validity;
	private String priority;
	
	public Prescription(Long id, String description, FullName patient, FullName doctor,
    		String date, String volidity, String priority){
		this.id = id;
		this.description = description;
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.validity = volidity;
		this.priority = priority;
	}
	public Prescription(String description, FullName patient, FullName doctor,
    		String date, String volidity, String priority){
		this.id = null;
		this.description = description;
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.validity = volidity;
		this.priority = priority;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public FullName getPatient() {
		return patient;
	}

	public void setPatient(FullName patient) {
		this.patient = patient;
	}

	public FullName getDoctor() {
		return doctor;
	}

	public void setDoctor(FullName doctor) {
		this.doctor = doctor;
	}
	
}

