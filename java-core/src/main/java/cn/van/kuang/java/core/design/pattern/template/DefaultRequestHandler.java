package cn.van.kuang.java.core.design.pattern.template;

public class DefaultRequestHandler extends RequestHandlerTemplate {
    @Override
    protected void log(Request request) {
        System.out.println("Received " + request);
    }

    @Override
    protected void validate(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request can't be null");
        }

        if (request.getId() == 0) {
            throw new IllegalArgumentException("Request id is required");
        }

        if (request.getType() == null || "".equals(request.getType())) {
            throw new IllegalArgumentException("Request type is required");
        }
    }

    @Override
    protected void doProcess(Request request) {
        System.out.println("Start processing request");
        System.out.println("Finished processing request");
    }
}
