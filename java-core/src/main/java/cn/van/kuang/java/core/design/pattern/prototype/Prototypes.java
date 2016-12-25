package cn.van.kuang.java.core.design.pattern.prototype;

import cn.van.kuang.java.core.design.pattern.prototype.domain.Job;
import cn.van.kuang.java.core.design.pattern.prototype.domain.Manager;
import cn.van.kuang.java.core.design.pattern.prototype.domain.Programer;

import java.util.HashMap;
import java.util.Map;

public class Prototypes {

    private static Map<Type, Job> prototypes;

    static {
        prototypes = new HashMap<>();
        prototypes.put(Type.PROGRAMER, new Programer("Van"));
        prototypes.put(Type.MANAGER, new Manager("Wilson"));
    }

    public static Job retrieve(Type type) {
        if (prototypes.containsKey(type)) {
            return prototypes.get(type).clone();
        }

        throw new IllegalArgumentException(type.name() + " not registered yet");
    }

    private Prototypes() {
    }

    public enum Type {
        PROGRAMER,
        TESTER,
        MANAGER
    }

    public static void main(String[] args) {
        Job manager = Prototypes.retrieve(Type.MANAGER);
        System.out.println(manager.title());

        Job programer = Prototypes.retrieve(Type.PROGRAMER);
        System.out.println(programer.title());

        Prototypes.retrieve(Type.TESTER);
    }
}
