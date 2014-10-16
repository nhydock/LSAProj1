package mock;

import data.keys.Key;
import domain.mappers.DataMapper;

public class MockMapper implements DataMapper<MockDomainModel> {

    @Override
    public MockDomainModel find(Key key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(MockDomainModel[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void insert(MockDomainModel[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(MockDomainModel[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Class<MockDomainModel> getType() {
        // TODO Auto-generated method stub
        return MockDomainModel.class;
    }


}
