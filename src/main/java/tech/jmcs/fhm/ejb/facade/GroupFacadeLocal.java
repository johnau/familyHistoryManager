package tech.jmcs.fhm.ejb.facade;

import tech.jmcs.fhm.model.Group;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GroupFacadeLocal {

    void create(Group group);

    void edit(Group group);

    void remove(Group group);

    Group find(Object id);

    List<Group> findAll();

    List<Group> findRange(int[] range);

    int count();

    Group findByName(String groupName);

}
