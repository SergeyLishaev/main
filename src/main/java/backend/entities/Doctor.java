package backend.entities;

public class Doctor extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String surname;
	private String name;
	private String patronymic;
	private String specialization;
	
	public Doctor(Long id, String surname, String name, String patronymic, String specialization){
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.specialization = specialization;
		
	}
	public Doctor(String surname, String name, String patronymic, String specialization){
		this.id = null;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.specialization = specialization;
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
}

