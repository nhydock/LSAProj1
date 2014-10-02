package domain.model;

/**
 * Unit of work handler
 * 
 * @author nhydock
 *
 */
public class Uow {

    /**
     * Possible states of the unit of work
     */
    public static enum State {
        Created, Loaded, Deleted, Changed;
    }

    private State state = State.Created;

    /**
     * Marks a unit of work as changed. If the state is marked as deleted,
     * changes will not matter.
     */
    public void markChanged() {
        if (state == State.Loaded) {
            state = State.Changed;
        }
    }

    /**
     * Marks a unit of work as to be deleted. Deletion overrides changes in
     * priority
     */
    public void markDeleted() {
        state = State.Deleted;
    }

    /**
     * @return the current state of the Unit of Work
     */
    public State getState() {
        return state;
    }

    /**
     * Marks a unit of work back to default loaded. Use if wanting to rollback
     * deletion before committing or After updating/inserting an object
     */
    public void markLoaded() {
        state = State.Loaded;
    }
}
