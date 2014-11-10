package domain.model;

public class NullUser extends User {

	@Override
	public void rollbackValues() { }

	@Override
	public void saveValues() { }

	@Override
	public String toString(){
		return "null";
	}
}
