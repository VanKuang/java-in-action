package cn.van.kuang.java.core.design.pattern.facade;

public class JVM {

    private ClassLoader classLoader;
    private Verifier verifier;
    private Preparationer preparationer;
    private Resolutioner resolutioner;
    private Initializationer initializationer;

    public JVM() {
        classLoader = new ClassLoader();
        verifier = new Verifier();
        preparationer = new Preparationer();
        resolutioner = new Resolutioner();
        initializationer = new Initializationer();
    }

    public void run() {
        classLoader.load();
        verifier.verify();
        preparationer.prepare();
        resolutioner.resolute();
        initializationer.init();
    }

    public static void main(String[] args) {
        JVM jvm = new JVM();
        jvm.run();
    }

}
