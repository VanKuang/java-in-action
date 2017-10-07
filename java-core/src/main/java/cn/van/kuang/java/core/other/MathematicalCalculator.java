package cn.van.kuang.java.core.other;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MathematicalCalculator {

    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        System.out.println(engine.eval("1+2"));
        System.out.println(engine.eval("1+2*3"));
        System.out.println(engine.eval("1+2*(3/2)"));
    }

}
