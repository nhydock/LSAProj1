package mock;

import domain.model.DomainModelObject;
import system.Session;

public class MockDomainModel extends DomainModelObject implements MockObj {
    String displayName;
    
    public MockDomainModel()
    {
        saveValues();
        Session.getUnitOfWork().markNew(this);
    }
    
    public MockDomainModel(String name)
    {
        this.displayName = name;
        
        saveValues();
    }
    
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
        displayName = (String)values.get("name");
    }

    @Override
    public void saveValues() {
        values.put("name", getDisplayName());
    }
}
