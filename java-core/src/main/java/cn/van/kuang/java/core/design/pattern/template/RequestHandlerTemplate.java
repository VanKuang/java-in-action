package cn.van.kuang.java.core.design.pattern.template;

public abstract class RequestHandlerTemplate implements RequestHandler {

    @Override
    public void onRequest(Request request) {
        log(request);
        validate(request);
        doProcess(request);
    }

    protected abstract void log(Request request);

    protected abstract void validate(Request request);

    protected abstract void doProcess(Request request);

}
