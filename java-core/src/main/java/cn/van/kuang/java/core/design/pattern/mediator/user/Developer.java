package cn.van.kuang.java.core.design.pattern.mediator.user;

public class Developer implements Role {

    private final String name;
    private final String skillLanaguage;

    public Developer(String name, String skillLanaguage) {
        this.name = name;
        this.skillLanaguage = skillLanaguage;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Type type() {
        return Type.DEV;
    }

    @Override
    public void onMessage(String msg) {
        System.out.println(this.name + " received [" + msg + "]");
    }

    public String getSkillLanaguage() {
        return skillLanaguage;
    }
}
