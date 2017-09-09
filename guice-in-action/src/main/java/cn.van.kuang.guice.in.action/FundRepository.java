package cn.van.kuang.guice.in.action;

import java.util.Arrays;
import java.util.List;

public class FundRepository implements Repository<Fund> {

    @Override
    public List<Fund> list() {
        Fund aaa = new Fund(1, "AAA");
        Fund bbb = new Fund(1, "AAA");

        return Arrays.asList(aaa, bbb);
    }

}
