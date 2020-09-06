package tech.jmcs.fhm.jsf.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.model.RelationshipType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("jsf.familyRelationshipConverter")
public class FamilyRelationshipConverter implements Converter {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyRelationshipConverter.class);

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return RelationshipType.valueOf(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        RelationshipType rt;
        try {
            rt = (RelationshipType) o;
        } catch (ClassCastException ccex) {
            LOG.error("The object was not of the expected type: RelationshipType");
            return "";
        }

        return rt.toString();
    }
}
