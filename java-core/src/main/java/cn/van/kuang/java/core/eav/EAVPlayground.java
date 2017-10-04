package cn.van.kuang.java.core.eav;

import java.util.HashMap;
import java.util.Map;

public class EAVPlayground {

    public static void main(String[] args) throws Exception {
        EntityDAO dao = new EntityDAO();

        dao.initTables();

        Map<String, String> kobeAttrs = new HashMap<>();
        kobeAttrs.put("name", "Kobe");
        kobeAttrs.put("height", "6.6");
        kobeAttrs.put("position", "PG");
        kobeAttrs.put("club", "LAKERS");
        kobeAttrs.put("all_star", "17");

        int kobeID = createPlayer(kobeAttrs);

        Entity kobe = dao.getEntity(kobeID);
        System.out.println(kobe);

        Map<String, String> jamesAttrs = new HashMap<>();
        jamesAttrs.put("name", "James");
        jamesAttrs.put("height", "6.8");
        jamesAttrs.put("position", "FG");
        jamesAttrs.put("club", "CAVALIERS");

        int jamesID = createPlayer(jamesAttrs);
        Entity james = dao.getEntity(jamesID);
        System.out.println(james);
    }

    private static int createPlayer(Map<String, String> attrs) throws Exception {
        EntityDAO dao = new EntityDAO();

        int entityID = dao.createEntity();

        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            int attributeID = dao.createAttribute(entry.getKey());
            dao.addAttributeValue(entityID, attributeID, entry.getValue());
        }

        return entityID;
    }

}
