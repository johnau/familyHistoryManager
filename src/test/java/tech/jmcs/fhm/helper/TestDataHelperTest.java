package tech.jmcs.fhm.helper;

import tech.jmcs.fhm.model.FamilyMember;

import java.util.HashMap;
import java.util.Map;

class TestDataHelperTest {

    @org.junit.jupiter.api.Test
    void testParseTestXml() {

        Map<String, Map<String, Object>> data = TestDataHelper.parseFamilyMemberXMLTestData();

    }

    @org.junit.jupiter.api.Test
    void testProcessLInks() {
        String text = "John McSweeney was born in Subiaco, Western Australia on the 10th of March 1988. His father, {% \"Chris McSweeney\"|familyMember|id:2 %}, owned a house nearby. Also another link: {% \"Reg McSweeney\"|familyMember|id:4 %}";

        Map<String, FamilyMember> dummyEntityMap = new HashMap<>();
        FamilyMember dummy2 = new FamilyMember();
        dummy2.setId(255L);
        FamilyMember dummy4 = new FamilyMember();
        dummy4.setId(499L);
        dummyEntityMap.put("2", dummy2);
        dummyEntityMap.put("4", dummy4);

        String res = TestDataHelper.processLinkData(text, dummyEntityMap);
        System.out.println(res);
    }
}