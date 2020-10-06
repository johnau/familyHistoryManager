package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.model.InternalHyperLink;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class InternalHyperlinkFacade extends AbstractFacade<InternalHyperLink> implements InternalHyperlinkFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(InternalHyperlinkFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public InternalHyperlinkFacade() {
        super(InternalHyperLink.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(InternalHyperLink newEntity) {
        return true;
    }


}
