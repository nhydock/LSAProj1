package domain.model;

public abstract class DomainModelObject {

	protected final long sessionID;
	
	private Uow work;
	
	public DomainModelObject(long sessionID)
	{
		this.sessionID = sessionID;
		this.work = new Uow();
	}
	
	public Uow getUnitOfWork()
	{
		return work;
	}
	
	/**
	 * Flags the domain object to be deleted from the system
	 */
	public void delete()
	{
		getUnitOfWork().markDeleted();
	}
}
