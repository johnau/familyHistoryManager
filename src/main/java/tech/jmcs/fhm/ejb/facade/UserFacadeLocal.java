package tech.jmcs.fhm.ejb.facade;

import tech.jmcs.fhm.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

    User findByUsername(String username);

}
