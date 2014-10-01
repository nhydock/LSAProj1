package domain.model;

public abstract class DomainModelObject {

    private Uow work = new Uow();

    public Uow getUnitOfWork() {
        return work;
    }

    /**
     * Flags the domain object to be deleted from the system
     */
    public void delete() {
        getUnitOfWork().markDeleted();
    }

    public void loaded() {
        getUnitOfWork().markLoaded();
    }
}
