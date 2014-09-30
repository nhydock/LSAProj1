package domain.model;

/**
 * Unit of work handler
 * @author nhydock
 *
 */
public class Uow {

	/**
	 * Possible states of the unit of work
	 */
	public static enum State
	{
		Loaded,
		Deleted,
		Changed;
	}
	
	private State state = State.Loaded;
	
	/**
	 * Marks a unit of work as changed.
	 * If the state is marked as deleted, changes will not matter.
	 */
	protected void markChanged()
	{
		if (state == State.Loaded)
		{
			state = State.Changed;
		}
	}
	
	/**
	 * Marks a unit of work as to be deleted.
	 * Deletion overrides changes in priority
	 */
	protected void markDeleted()
	{
		state = State.Deleted;
	}
	
	/**
	 * Marks a unit of work back to default loaded.
	 * Use if wanting to rollback deletion before committing
	 */
	protected void reset()
	{
		state = State.Loaded;
	}
	
	/**
	 * @return the current state of the Unit of Work
	 */
	public State getState()
	{
		return state;
	}
}
