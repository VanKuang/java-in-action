package cn.van.kuang.hello.world.java.ruleengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TemplateGenerator {

    private final static String WILDCARD = "*";
    private final static String SEPARATOR = "#";
    private final static String SUB_TEMPLATE_KEY_WORD = "PLACE_HOLDER";
    private final static String SUB_TEMPLATE = "${" + SUB_TEMPLATE_KEY_WORD + "}";

    private final Column[] columns;
    private final Column[] orderedColumns;

    private int max = 0;
    private int min = 0;

    public TemplateGenerator(Column[] columns) {
        if (columns == null) {
            throw new IllegalArgumentException("columns can't be null");
        }

        this.columns = columns;
        this.orderedColumns = sortColumns(columns);
        locateMaxAndMin();
    }

    public List<String> generateStringBaseTemplates() {
        if (max == min) {
            return Collections.emptyList();
        }

        List<String> templates = new ArrayList<String>();
        for (int number = max; number >= min; number--) {
            String binaryString = preAppendZero(Integer.toBinaryString(number));
            char[] chars = binaryString.toCharArray();

            String template = "";
            for (int i = 0, length = chars.length; i < length; i++) {
                template += (chars[i] == '0')
                        ? WILDCARD
                        : SUB_TEMPLATE.replace(SUB_TEMPLATE_KEY_WORD, orderedColumns[i].name);
                template += SEPARATOR;
            }

            templates.add(template.substring(0, template.length() - SEPARATOR.length()));
        }

        return templates;
    }

    public List<List<Column>> generateColumnBaseTemplates() {
        if (max == min) {
            return Collections.emptyList();
        }

        List<List<Column>> templates = new ArrayList<List<Column>>();

        for (int number = max; number >= min; number--) {
            String binaryString = preAppendZero(Integer.toBinaryString(number));
            char[] chars = binaryString.toCharArray();

            List<Column> template = new ArrayList<Column>();
            for (int i = 0, length = chars.length; i < length; i++) {
                Column column = new Column(orderedColumns[i].name, chars[i] == '0');
                template.add(column);
            }

            List<Column> originalSequenceColumns = restoreOriginalSequence(template);
            templates.add(originalSequenceColumns);
        }

        return templates;
    }

    private List<Column> restoreOriginalSequence(List<Column> template) {
        List<Column> sequencedColumns = new ArrayList<Column>(template.size());
        for (Column column : columns) {
            for (Column templateColumn : template) {
                if (column.name.equals(templateColumn.name)) {
                    sequencedColumns.add(templateColumn);
                }
            }
        }
        return sequencedColumns;
    }

    private String preAppendZero(String value) {
        int maxDigit = Integer.toBinaryString(max).length();
        int delta = maxDigit - value.length();
        for (int i = 0; i < delta; i++) {
            value = "0" + value;
        }
        return value;
    }

    private Column[] sortColumns(Column[] columns) {
        Column[] orderedColumns = new Column[columns.length];

        int index = 0;
        for (Column column : columns) {
            if (!column.isWildcard) {
                orderedColumns[index] = column;
                index++;
            }
        }
        for (Column column : columns) {
            if (column.isWildcard) {
                orderedColumns[index] = column;
                index++;
            }
        }

        return orderedColumns;
    }

    private void locateMaxAndMin() {
        for (int i = 0, length = orderedColumns.length; i < length; i++) {
            max += Math.pow(2, length - i - 1);

            if (!orderedColumns[i].isWildcard) {
                min += Math.pow(2, length - i - 1);
            }
        }
    }
}