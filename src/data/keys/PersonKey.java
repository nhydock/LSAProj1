package data.keys;

/**
 * Primary key identified with people
 * @author nhydock
 *
 */
public class PersonKey implements Key
{
	public final int id;
	
	public PersonKey(int id)
	{
		this.id = id;
	}
	
	public boolean equals(Object obj)
	{
		//equals identity
		if (obj == this)
			return true;
		//not null
		if (obj == null)
			return false;
		//is a person key
		if (!(obj instanceof PersonKey))
			return false;
		PersonKey key = (PersonKey)obj;
		
		//value matching
		boolean eq = true;
		eq = eq && (key.id == this.id);
		return eq;
	}
}