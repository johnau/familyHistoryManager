package tech.jmcs.fhm.ejb.facade;

import tech.jmcs.fhm.model.InternalHyperLink;

import javax.ejb.Local;
import java.util.List;

@Local
public interface InternalHyperlinkFacadeLocal {
    void create(InternalHyperLink internalHyperLink);

    void edit(InternalHyperLink internalHyperLink);

    void remove(InternalHyperLink internalHyperLink);

    InternalHyperLink find(Object id);

    List<InternalHyperLink> findAll();

    List<InternalHyperLink> findRange(int[] range);

    int count();
}
