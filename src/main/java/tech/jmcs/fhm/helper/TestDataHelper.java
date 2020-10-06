package tech.jmcs.fhm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import tech.jmcs.fhm.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestDataHelper {
    private static final Logger LOG = LoggerFactory.getLogger(TestDataHelper.class);

    public static final String FAMILY_MEMBER_FIRSTNAME_ELEMENT = "firstname";
    public static final String FAMILY_MEMBER_OTHERNAMES_ELEMENT = "othernames";
    public static final String FAMILY_MEMBER_LASTNAME_ELEMENT = "lastname";
    public static final String FAMILY_MEMBER_DOB_ELEMENT = "dob";
    public static final String FAMILY_MEMBER_DOD_ELEMENT = "dod";

    public static final String FAMILY_RELATIONSHIPS_ELEMENT = "relationships";
    public static final String RELATIONSHIP_ELEMENT = "relationship";
    public static final String BIO_SECTIONS_ELEMENT = "bio-sections";
    public static final String SECTION_ELEMENT = "section";

    private static final Pattern LINK_PATTERN = Pattern.compile("[{][%][ ]?(.+)[|](.+)[|](.+)[ ]?[%][}]"); // 3 data groups
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Get Family Member Data from Text-Data XML
     * @return
     */
    public static Map<String, Map<String, Object>> parseFamilyMemberXMLTestData() {
        Document doc = openTestDataXMLDocument();
        if (doc == null) return null;

        Map<String, Map<String, Object>> dataMap = new HashMap<>();

        doc.getDocumentElement().normalize();
        LOG.debug("Root element: {}", doc.getDocumentElement().getNodeName());

        Node familyNode = doc.getElementsByTagName("family").item(0);
        Element familyElement = processElement(familyNode);
        if (familyElement == null) {
            LOG.warn("Cannot process the family node");
        } else {
            processFamilyData(familyElement, dataMap);
        }

        return dataMap;
    }

    /**
     * Get Articles Data from Test-Data XML
     * @return
     */
    public static Map<String, Map<String, Object>> parseArticlesXMLTestData() {
        Document doc = openTestDataXMLDocument();
        if (doc == null) return null;

        Map<String, Map<String, Object>> dataMap = new HashMap<>();

        doc.getDocumentElement().normalize();
        LOG.debug("Root element: {}", doc.getDocumentElement().getNodeName());

        Node articlesNode = doc.getElementsByTagName("articles").item(0);
        Element articlesElement = processElement(articlesNode);
        if (articlesElement == null) {
            LOG.warn("Cannot process articles node");
        } else {
            processArticlesData(articlesElement, dataMap);
        }

        return dataMap;
    }

    /**
     * Process XML data for family members (with no db relationships)
     * @param familyDataMap
     * @return
     */
    public static Map<String, FamilyMember> filterFamilyMemberOnly(Map<String, Map<String, Object>> familyDataMap) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, FamilyMember> familyMap = new HashMap<>();
        familyDataMap.forEach( (id, data) -> {
            FamilyMember fm = new FamilyMember();
            fm.setFirstname((String) data.get(FAMILY_MEMBER_FIRSTNAME_ELEMENT));
            fm.setOtherNames((String) data.get(FAMILY_MEMBER_OTHERNAMES_ELEMENT));
            fm.setLastname((String) data.get(FAMILY_MEMBER_LASTNAME_ELEMENT));
            try {
                fm.setDob(df.parse((String) data.get(FAMILY_MEMBER_DOB_ELEMENT)));
            } catch (ParseException e) {
            }
            try {
                fm.setDod(df.parse((String) data.get(FAMILY_MEMBER_DOD_ELEMENT)));
            } catch (ParseException e) {
            }

            familyMap.put(id, fm);
        });

        return familyMap;
    }

    /**
     * Process XML data for family relationships
     * @param familyDataMap
     * @param entityMap
     * @return
     */
    public static Map<String, List<FamilyRelationship>> filterFamilyRelationshipsOnly(Map<String, Map<String, Object>> familyDataMap, Map<String, FamilyMember> entityMap) {
        Map<String, List<FamilyRelationship>> relationshipMap = new HashMap<>();

        familyDataMap.forEach( (id, data) -> {
            FamilyMember fm = entityMap.get(id);

            Object r = data.get(FAMILY_RELATIONSHIPS_ELEMENT);
            Map<String, String> relationships = (Map<String, String>) r;

            if (relationships != null && !relationships.isEmpty()) {
                relationshipMap.put(id, new ArrayList<>());

                relationships.forEach((relId, type) -> {
                    RelationshipType rType = RelationshipType.valueOf(type);
                    FamilyMember rel = entityMap.get(relId);

                    FamilyRelationship famRel = new FamilyRelationship();
                    famRel.setA(fm);
                    famRel.setRelType(rType);
                    famRel.setB(rel);

                    relationshipMap.get(id).add(famRel);
                });
            }

        });

        return relationshipMap;
    }

    /**
     * Update family member entities with relationships
     * @param relationshipMap
     * @param entityMap
     * @return
     */
    public static List<FamilyMember> updateFamilyMembersWithRelationships(Map<String, List<FamilyRelationship>> relationshipMap, Map<String, FamilyMember> entityMap) {
        List<FamilyMember> updatedList = new ArrayList<>();

        relationshipMap.forEach( (id, relsList) -> {
            FamilyMember fm = entityMap.get(id);

            relsList.forEach( rel -> {

                List<FamilyRelationship> existingRelationships = fm.getRelationships();
                if (existingRelationships == null) {
                    existingRelationships = new ArrayList<>();
                }
                existingRelationships.add(rel);
                fm.setRelationships(existingRelationships);

            });

            updatedList.add(fm);
        });

        return updatedList;
    }

    /**
     * Process XML data for family member bio sections
     * @param familyDataMap
     * @param entityMap
     * @return
     */
    public static Map<String, List<FamilyMemberBioSection>> filterFamilyMemberBioSections(Map<String, Map<String, Object>> familyDataMap, Map<String, FamilyMember> entityMap) {
        Map<String, List<FamilyMemberBioSection>> sections = new HashMap<>();

        familyDataMap.forEach( (id, data) -> {
            FamilyMember fm = entityMap.get(id);

            Object bs = data.get(BIO_SECTIONS_ELEMENT);
            Map<String, String> bioSections = (Map<String, String>) bs;

            if (bioSections != null && !bioSections.isEmpty()) {
                sections.put(id, new ArrayList<>());

                bioSections.forEach( (title, content) -> {
                    FamilyMemberBioSection fmbs = new FamilyMemberBioSection();
                    fmbs.setTitle(title);
                    content = processLinkData(content, entityMap);
                    fmbs.setContent(content);
                    fmbs.setFamilyMember(fm);
                    sections.get(id).add(fmbs);
                });
            }

        });

        return sections;
    }

    /**
     * Update family member entities with bio sections
     * @param bioSectionsMap
     * @param entityMap
     * @return
     */
    public static List<FamilyMember> updateFamilyMembersWithBioSections(Map<String, List<FamilyMemberBioSection>> bioSectionsMap, Map<String, FamilyMember> entityMap) {
        List<FamilyMember> updatedList = new ArrayList<>();

        bioSectionsMap.forEach( (id, sectionsList) -> {
            FamilyMember fm = entityMap.get(id);
            List<FamilyMemberBioSection> existingBioSections = fm.getBioSections();
            if (existingBioSections == null) {
                existingBioSections = new ArrayList<>();
                fm.setBioSections(existingBioSections);
            }

            sectionsList.forEach(sect -> {
                List<FamilyMemberBioSection> ebs = fm.getBioSections();
                ebs.add(sect);

            });

            updatedList.add(fm);
        });

        return updatedList;
    }

    public static String processLinkData(String text, Map<String, FamilyMember> fmEntityMap) {
        List<LinkData> links = new ArrayList<>();

        String originalText = text;

        Matcher matcher = LINK_PATTERN.matcher(text);
        while (matcher.find()) {
            int st = matcher.start();
            int en = matcher.end();

            String linkText = matcher.group(1);
            if (linkText.startsWith("\"") && linkText.endsWith("\"")) {
                linkText = linkText.substring(1, linkText.length()-1);
            }

            String linkOutcome = matcher.group(2);
            String linkParamString = matcher.group(3);
            String[] paramArr = linkParamString.split(","); // Note: This may need to be replaced by a regex match...
            Map<String, String> params = new HashMap<>();
            for (String p : paramArr) {
                p = p.trim();
                String[] pp = p.split(":");
                params.put(pp[0], pp[1]);
            }

            LinkData linkData = null;
            if (linkOutcome.equals("familyMember")) {
                String link = processFamilyMemberLink(linkText, params, fmEntityMap);
                linkData = new LinkData(link, st, en);
            } else if (linkOutcome.equals("article")) {

            }

            LOG.debug("Created link data with link: {}", linkData.getLink());
            links.add(linkData);
        }

        for (LinkData l : links) {
            String updatedText = text.substring(1, l.getStartPosition()) + l.getLink() + text.substring(l.getEndPosition(), text.length());
            LOG.debug("Updated text: {}", updatedText);
            text = updatedText;
        }

        return text;
    }

    /**
     * Returns a valid link String {% "Link Text"|outcome|param1:value, param2:value %}
     * Returns null if string cannot be built
     * @param parameters
     * @return
     */
    private static String processFamilyMemberLink(String linkText, Map<String, String> parameters, Map<String, FamilyMember> fmEntityMap) {
        String id = parameters.get("id");
        if (id == null || id.isEmpty()) {
            return null;
        }

        FamilyMember entity = fmEntityMap.get(id);
        String realId = entity.getId().toString();

        String link = String.format("{%% \"%s\"|familyMember|%s %%}", linkText, realId);
        LOG.debug("Generated link: {}", link);
        return link;
    }

    /**
     * Incomplete, abandoning this part of test data for now
     */
    private static class LinksProcessor {

        private Map<String, InternalHyperLink> links;
        private String contentText;

        private final DocumentSection originalContent;
        private final Map<String, FamilyMember> familyMemberEntityMap;
        private final Map<String, Article> articleEntityMap;

        protected LinksProcessor(DocumentSection originalContent, Map<String, FamilyMember> familyMemberEntityMap, Map<String, Article> articleEntityMap) {
            this.originalContent = originalContent;
            this.familyMemberEntityMap = familyMemberEntityMap;
            this.articleEntityMap = articleEntityMap;
        }

        void process(DocumentSection section) {
            Matcher matcher = LINK_PATTERN.matcher(section.getContent());
            List<LinkData> updatedLinks = new ArrayList<>();

            // loop each link match found
            while (matcher.find()) {
                int st = matcher.start();
                int en = matcher.end();
                String linkText = matcher.group(1);
                String linkOutcome = matcher.group(2);
                Map<String, String> linkParams = splitParams(matcher.group(3));
                fixFamilyMemberIdParameter(linkParams);

                // look up existing link ?? ... difficult with params, just create new links each time

                // create if doesnt exist

                InternalHyperLink link = new InternalHyperLink();
                link.setOutcome(linkOutcome);
                link.setParameters(linkParams);
                link.setDocumentSection(section);

                // map link to section
                List<InternalHyperLink> existingLinks = section.getLinks();
                if (existingLinks == null) existingLinks = new ArrayList();
                existingLinks.add(link);
                section.setLinks(existingLinks);

                // store update for modification to text content later
                LinkData ld = new LinkData("link", st, en);
                updatedLinks.add(ld);
            }

            String text = section.getContent();
            for (LinkData l : updatedLinks) {
                String updatedText = text.substring(1, l.getStartPosition()) + l.getLink() + text.substring(l.getEndPosition(), text.length());
                LOG.debug("Updated text: {}", updatedText);
                text = updatedText;
            }

            LOG.debug("Method incomplete....");
        }

        private Map<String, String> splitParams(String linkParamString) {
            String[] paramArr = linkParamString.split(","); // Note: This may need to be replaced by a regex match...
            Map<String, String> params = new HashMap<>();
            for (String p : paramArr) {
                p = p.trim();
                String[] pp = p.split(":");
                params.put(pp[0], pp[1]);
            }
            return params;
        }

        private void fixFamilyMemberIdParameter(Map<String, String> linkParams) {
            String id = linkParams.get("id");
            if (id == null || id.isEmpty()) {
                return;
            }

            FamilyMember entity = familyMemberEntityMap.get(id);
            String realId = entity.getId().toString();

            linkParams.put("id", realId);
        }

        @Deprecated
        private String processLinkData(String text, Map<String, FamilyMember> fmEntityMap) {
            List<LinkData> links = new ArrayList<>();

            String originalText = text;

            Matcher matcher = LINK_PATTERN.matcher(text);
            while (matcher.find()) {
                int st = matcher.start();
                int en = matcher.end();

                String linkText = matcher.group(1);
                if (linkText.startsWith("\"") && linkText.endsWith("\"")) {
                    linkText = linkText.substring(1, linkText.length()-1);
                }

                String linkOutcome = matcher.group(2);
                String linkParamString = matcher.group(3);
                String[] paramArr = linkParamString.split(","); // Note: This may need to be replaced by a regex match...
                Map<String, String> params = new HashMap<>();
                for (String p : paramArr) {
                    p = p.trim();
                    String[] pp = p.split(":");
                    params.put(pp[0], pp[1]);
                }

                LinkData linkData = null;
                if (linkOutcome.equals("familyMember")) {
                    String link = processFamilyMemberLink(linkText, params, fmEntityMap);
                    linkData = new LinkData(link, st, en);
                } else if (linkOutcome.equals("article")) {

                }

                LOG.debug("Created link data with link: {}", linkData.getLink());
                links.add(linkData);
            }

            for (LinkData l : links) {
                String updatedText = text.substring(1, l.getStartPosition()) + l.getLink() + text.substring(l.getEndPosition(), text.length());
                LOG.debug("Updated text: {}", updatedText);
                text = updatedText;
            }

            return text;
        }

        /**
         * Returns a valid link String {% "Link Text"|outcome|param1:value, param2:value %}
         * Returns null if string cannot be built
         * @param parameters
         * @return
         */
        @Deprecated
        private String processFamilyMemberLink(String linkText, Map<String, String> parameters, Map<String, FamilyMember> fmEntityMap) {
            String id = parameters.get("id");
            if (id == null || id.isEmpty()) {
                return null;
            }

            FamilyMember entity = fmEntityMap.get(id);
            String realId = entity.getId().toString();

            String link = String.format("{%% \"%s\"|familyMember|%s %%}", linkText, realId);
            LOG.debug("Generated link: {}", link);
            return link;
        }

        Map<String, InternalHyperLink> getLinks() { return links; }

        String getContentText() { return contentText; }
    }

    private static String processArticleLink(String linkText, Map<String, String> parameters, Map<String, Article> arEntityMap) {

        return "";
    }

    public static class LinkData {
        private String link;
        private int startPosition;
        private int endPosition;

        public LinkData() {}

        public LinkData(String link, int startPosition, int endPosition) {
            this.link = link;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(int startPosition) {
            this.startPosition = startPosition;
        }

        public int getEndPosition() {
            return endPosition;
        }

        public void setEndPosition(int endPosition) {
            this.endPosition = endPosition;
        }
    }

    private static void processArticlesData(Element articlesElement, Map<String, Map<String, Object>> dataMap) {
        // Get only the article elements
        NodeList aList = articlesElement.getElementsByTagName("article");
        // Iterate the article elements
        for (int a = 0; a < aList.getLength(); a++) {

        }
    }

    private static void processFamilyData(Element familyElement, Map<String, Map<String, Object>> dataMap) {
        // Get only the familyMember elements
        NodeList fmList = familyElement.getElementsByTagName("familyMember");
        // Iterate the family member elements
        for (int i = 0; i < fmList.getLength(); i++) {
            Element familyMemberElement = processElement(fmList.item(i));
            if (familyMemberElement == null) {
                LOG.warn("A test familyMember could not be processed");
                continue; // move to the next family member
            }

            String id = familyMemberElement.getAttribute("id");

            dataMap.put(id, new HashMap<String, Object>());

            // Get data elements
            String firstname = familyMemberElement.getElementsByTagName("firstname").item(0).getTextContent();
            String othernames = familyMemberElement.getElementsByTagName("othernames").item(0).getTextContent();
            String lastname = familyMemberElement.getElementsByTagName("lastname").item(0).getTextContent();
            String dob = familyMemberElement.getElementsByTagName("dob").item(0).getTextContent();
            String dod = familyMemberElement.getElementsByTagName("dod").item(0).getTextContent();

            dataMap.get(id).put("firstname", firstname);
            dataMap.get(id).put("othernames", othernames);
            dataMap.get(id).put("lastname", lastname);
            dataMap.get(id).put("dob", dob);
            dataMap.get(id).put("dod", dod);

            // Get the relationships node
            Node relationshipsNode = familyMemberElement.getElementsByTagName(FAMILY_RELATIONSHIPS_ELEMENT).item(0);
            Element relationshipsElement = processElement(relationshipsNode);
            if (relationshipsElement == null) {
                LOG.debug("Test familyMember: #{} : {} {} will not be completed because relationships element was malformed or missing", id, firstname, lastname);
            } else {
                NodeList relationshipList = relationshipsElement.getElementsByTagName(RELATIONSHIP_ELEMENT);

                Map<String, String> relMap = new HashMap();

                // Iterate the relationship nodes
                for (int j = 0; j < relationshipList.getLength(); j++) {
                    Element rel = processElement(relationshipList.item(j));
                    if (rel == null) {
                        LOG.debug("Test familyMember: #{} : {} {} will not be completed because relationship element was malformed or missing", id, firstname, lastname);
                        continue; // move to the next section
                    }

                    String type = rel.getElementsByTagName("type").item(0).getTextContent();
                    String relId = rel.getElementsByTagName("rel-id").item(0).getTextContent();

                    relMap.put(relId, type);
                }

                dataMap.get(id).put(FAMILY_RELATIONSHIPS_ELEMENT, relMap);

                LOG.trace("This person: {} {} {} | {}", firstname, othernames, lastname, dob);
                LOG.trace("And has relationships:");
                String rString = relMap.entrySet().stream()
                        .map(e -> e.getValue() + " " + e.getKey())
                        .collect(Collectors.joining(", "));
                LOG.trace("{}", rString);
            }

            // Get the bio sections node - if node doesnt exist do not process
            Node bioSectionNode = familyMemberElement.getElementsByTagName(BIO_SECTIONS_ELEMENT).item(0);
            if (bioSectionNode == null) {
                LOG.debug("Test familyMember #{} : {} {} does not have any bio sections", id, firstname, lastname);
            } else {

                Element bioSectionElement = processElement(bioSectionNode);
                if (bioSectionElement == null) {
                    LOG.debug("Test familyMember #{} : {} {} does not have any bio sections", id, firstname, lastname);
                } else {
                    NodeList bioSectionList = bioSectionElement.getElementsByTagName(SECTION_ELEMENT);

                    Map<String, String> bioSectionMap = new HashMap<>();

                    for (int j = 0; j < bioSectionList.getLength(); j++) {
                        Element sect = processElement(bioSectionList.item(j));
                        if (sect == null) {
                            LOG.debug("Test familyMember #{} : {} {} will not be completed because section element was malformed or missing", "id", firstname, lastname);
                            continue; // move to the next section
                        }

                        String sectionTitle = sect.getAttribute("title");
                        String sectionContent = sect.getTextContent();

                        LOG.trace("Bio Section '{}' : '{}'", sectionTitle, sectionContent);

                        bioSectionMap.put(sectionTitle, sectionContent);
                    }

                    dataMap.get(id).put(BIO_SECTIONS_ELEMENT, bioSectionMap);
                }
            }

        }
    }

    private static Document openTestDataXMLDocument() {
        Document doc;
        try {
            File fXmlFile = new File(TestDataHelper.class.getResource("/test-data.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        } catch (Exception e) {
            LOG.warn("Could not parse the Test Data XML file");
            return null;
        }

        return doc;
    }

    private static Element processElement(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return (Element) node;
        } else {
            return null;
        }

    }

}
