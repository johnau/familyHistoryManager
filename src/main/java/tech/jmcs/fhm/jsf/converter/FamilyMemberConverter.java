package tech.jmcs.fhm.jsf.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.model.FamilyMember;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="jsf.familyMemberConverter", managed=true)
public class FamilyMemberConverter implements Converter {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyMemberConverter.class);

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException nfex) {
            LOG.error("Did not receive a valid ID for FamilyMember");
            return null;
        }

        return familyMemberFacade.find(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        FamilyMember fm;
        try {
            fm = (FamilyMember) o;
        } catch (ClassCastException ccex) {
            LOG.error("The object was not of the expected type: FamilyMember");
            return "";
        }

        return fm.getId().toString();
    }
}
