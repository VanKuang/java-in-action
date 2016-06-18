package cn.van.kuang.java.core.ruleengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use to get value from map
 * And the key of the map is composite by several columns
 * And the column value can be wildcard
 * like: "V1#V2#V3#V4#V5"/"V1#V2#V3#V4#*"/"V1#V2#V3#*#*"
 */
public class SimpleRuleEngine {

    private final static String SEPARATOR = "#";
    private final static String WILDCARD = "*";

    private final Column[] columns;
    private final List<String> templates;
    private final List<List<Column>> columnBaseTemplates;
    private final Map<String, String> cache;

    public SimpleRuleEngine(Column[] columns) {
        this.columns = columns;
        this.templates = generateTemplates();
        this.columnBaseTemplates = generateColumnBaseTemplates();
        this.cache = initialiseCache();
    }

    /**
     * Will generate template like: ${C1}#${C2}#${C3}#${C4}#${C5}
     * So need replace the place holder with column value to generate the mapping key
     * In this way, the column must be sequenced by priority
     */
    public String getValueByTemplates(String v1, String v2, String v3, String v4, String v5) {
        for (String template : templates) {

            // string replace is using regex, so will be a bit slower
            String key = template.replace("${C1}", v1)
                    .replace("${C2}", v2)
                    .replace("${C3}", v3)
                    .replace("${C4}", v4)
                    .replace("${C5}", v5);

            String value = cache.get(key);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    /**
     * Will generate a list of Columns
     * So need to generate mapping key according to these Columns runtime
     * In this way, the column don't need be sequenced by priority
     */
    public String getValueByColumnBase(String v1, String v2, String v3, String v4, String v5) {
        FluentMap<String, String> map = FluentMap.<String, String>map(5)
                .put(columns[0].name, v1)
                .put(columns[1].name, v2)
                .put(columns[2].name, v3)
                .put(columns[3].name, v4)
                .put(columns[4].name, v5);

        for (List<Column> columns : columnBaseTemplates) {
            String key = generateKey(map, columns);

            String value = cache.get(key);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    private String generateKey(FluentMap<String, String> map, List<Column> columns) {
        String key = "";
        for (Column column : columns) {
            key += (column.isWildcard ? WILDCARD : map.get(column.name)) + SEPARATOR;
        }
        return key.substring(0, key.length() - SEPARATOR.length());
    }

    private List<String> generateTemplates() {
        TemplateGenerator templateGenerator = new TemplateGenerator(columns);
        return templateGenerator.generateStringBaseTemplates();
    }

    private List<List<Column>> generateColumnBaseTemplates() {
        TemplateGenerator templateGenerator = new TemplateGenerator(columns);
        return templateGenerator.generateColumnBaseTemplates();
    }

    private Map<String, String> initialiseCache() {
        Map<String, String> map = new HashMap<>();

        map.put("V1#V2#V3#V4#V5", "JAVA");
        map.put("V1#V2#V3#V4#*", "C++");
        map.put("V1#V2#V3#*#*", "C#");
        map.put("V11#V2#V3#*#*", "GO");

        return map;
    }

    public static void main(String[] args) {

        Column[] columns = new Column[]{
                new Column("C1", false),
                new Column("C2", false),
                new Column("C3", true),
                new Column("C4", true),
                new Column("C5", true)
        };

        SimpleRuleEngine simpleRuleEngine = new SimpleRuleEngine(columns);

        System.out.println("====================Sequence column, map by String base=============================");

        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V55"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V44", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V11", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V22", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V33", "V4", "V5"));

        System.out.println("====================Sequence column, map by Column base===============================");

        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V55"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V44", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V11", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V22", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V33", "V4", "V5"));

        columns = new Column[]{
                new Column("C1", false),
                new Column("C2", true),
                new Column("C3", false),
                new Column("C4", true),
                new Column("C5", true)
        };

        simpleRuleEngine = new SimpleRuleEngine(columns);

        System.out.println("====================Not Sequence column, map by String base==========================");

        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V55"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V44", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V11", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V22", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByTemplates("V1", "V2", "V33", "V4", "V5"));

        System.out.println("====================Not Sequence column, map by Column base============================");

        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V55"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V44", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V11", "V2", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V22", "V3", "V4", "V5"));
        System.out.println(simpleRuleEngine.getValueByColumnBase("V1", "V2", "V33", "V4", "V5"));

        System.out.println("====================Performance Test, with sequence column==============================");

        columns = new Column[]{
                new Column("C1", false),
                new Column("C2", false),
                new Column("C3", true),
                new Column("C4", true),
                new Column("C5", true)
        };

        simpleRuleEngine = new SimpleRuleEngine(columns);

        int sum = 1000 * 1000;

        long start = System.currentTimeMillis();

        for (int i = 0; i < sum; i++) {
            simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V5");
            simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V4", "V55");
            simpleRuleEngine.getValueByTemplates("V1", "V2", "V3", "V44", "V5");
            simpleRuleEngine.getValueByTemplates("V11", "V2", "V3", "V4", "V5");
            simpleRuleEngine.getValueByTemplates("V1", "V22", "V3", "V4", "V5");
            simpleRuleEngine.getValueByTemplates("V1", "V2", "V33", "V4", "V5");
        }
        System.out.println("String base usage: "
                + (System.currentTimeMillis() - start)
                + "ms, with match " + sum + " times");

        start = System.currentTimeMillis();
        for (int i = 0; i < sum; i++) {
            simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V5");
            simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V4", "V55");
            simpleRuleEngine.getValueByColumnBase("V1", "V2", "V3", "V44", "V5");
            simpleRuleEngine.getValueByColumnBase("V11", "V2", "V3", "V4", "V5");
            simpleRuleEngine.getValueByColumnBase("V1", "V22", "V3", "V4", "V5");
            simpleRuleEngine.getValueByColumnBase("V1", "V2", "V33", "V4", "V5");
        }
        System.out.println("Column base usage: "
                + (System.currentTimeMillis() - start)
                + "ms, with match " + sum + " times");

    }
}
