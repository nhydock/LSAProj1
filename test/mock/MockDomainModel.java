package mock;

import domain.model.DomainModelObject;
import system.Session;

public class MockDomainModel extends DomainModelObject {
    String displayName;
    
    public void setDisplayName(String name)
    {
        this.displayName = name;
        Session.getUnitOfWork().markChanged(this);
    }
    
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public void rollbackValues() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveValues() {
        // TODO Auto-generated method stub
        
    }
}
