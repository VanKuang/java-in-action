package cn.van.kuang.java.core.design.pattern.facade;

import java.util.concurrent.atomic.AtomicBoolean;

public enum JVM {

    INSTANCE;

    private ClassLoader classLoader;
    private Verifier verifier;
    private Preparationer preparationer;
    private Resolutioner resolutioner;
    private Initializationer initializationer;

    private AtomicBoolean isInitialised = new AtomicBoolean(false);

    public void run() {
        if (isInitialised.compareAndSet(false, true)) {
            init();
        }

        classLoader.load();
        verifier.verify();
        preparationer.prepare();
        resolutioner.resolute();
        initializationer.init();
    }

    private void init() {
        classLoader = new ClassLoader();
        verifier = new Verifier();
        preparationer = new Preparationer();
        resolutioner = new Resolutioner();
        initializationer = new Initializationer();
    }

    public static void main(String[] args) {
        JVM.INSTANCE.run();
        JVM.INSTANCE.run();
    }

}
