package mock.mapper;

import system.Session;
import mock.MockDomainModel;
import mock.MockKey;
import data.keys.Key;
import domain.mappers.DataMapper;

public class MockDataMapper extends DataMapper<MockDomainModel> {

    @Override
    public MockDomainModel read(Key key) {
        MockKey link = (MockKey)key;
        MockDomainModel result = new MockDomainModel(link.name);
        Session.getIdentityMap(MockDomainModel.class).put(key, result);
        return result;
    }

    @Override
    public void update(MockDomainModel[] obj) {
        //do nothing
        for (MockDomainModel data : obj) {
            data.saveValues();
        }
    }

    @Override
    public void insert(MockDomainModel[] obj) {
        //build keys
        for (MockDomainModel data : obj) {
            MockKey key = new MockKey(data.getDisplayName());
            Session.getIdentityMap(MockDomainModel.class).put(key, data);
        }
    }

    @Override
    public void delete(MockDomainModel[] obj) {
        for (MockDomainModel data : obj) {
            MockKey key = new MockKey(data.getDisplayName());
            Session.getIdentityMap(MockDomainModel.class).remove(key);
        }
    }

    @Override
    public Class<MockDomainModel> getType() {
        return MockDomainModel.class;
    }


}
