package tech.jmcs.fhm.jsf.helper;

import tech.jmcs.fhm.model.InternalHyperLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentSectionToSectionPartsConverter {

    public static List<SectionPart> convert(String sectionContent, List<InternalHyperLink> links, Map<String, String> linkIdMap) {
        List<SectionPart> sectionParts = new ArrayList<>();

        // split section content by ". ", "/n", "{% ... %}"

        // create SectionPart objects

        return sectionParts;
    }

}
