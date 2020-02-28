package backend.entities;



public class Patient extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String surname;
	private String name;
	private String patronymic;
	private String phoneNumber;
	
	public Patient(Long id, String surname, String name, String patronymic, String phoneNumber){
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.phoneNumber = phoneNumber;
		
	}
	public Patient(String surname, String name, String patronymic, String phoneNumber){
		this.id = null;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.phoneNumber = phoneNumber;
		
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
