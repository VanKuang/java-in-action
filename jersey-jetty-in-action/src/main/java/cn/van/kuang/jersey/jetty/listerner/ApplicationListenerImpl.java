package cn.van.kuang.jersey.jetty.listerner;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationListenerImpl implements ApplicationEventListener {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationListenerImpl.class);

    public void onEvent(ApplicationEvent event) {
        logger.info("On event: [{}]", event.getType().name());
    }

    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new RequestEventListenerImpl();
    }
}
