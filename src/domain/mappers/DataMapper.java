package domain.mappers;

import data.keys.Key;
import data.keys.PersonKey;
import domain.model.DomainModelObject;
import domain.model.Person;

public interface DataMapper<T extends DomainModelObject> {

    public T find(Key key);

    public void update(T[] obj);

    public void insert(T[] obj);

    public void delete(T[] obj);

    public Class<T> getType();
}
