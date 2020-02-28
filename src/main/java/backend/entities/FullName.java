package backend.entities;

public class FullName extends AbstractEntity{
	
	private Long id;
	private String fullName;
	
	public FullName(Long id, String surname, String name, String patronymic){
		this.id = id;
		this.fullName = surname + " " + name + " " + patronymic;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	public String getFullName(){
		return fullName;
	}
}
