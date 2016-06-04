package cn.van.kuang.jersey.jetty.listerner;

import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by VanKuang on 16/3/14.
 */
public class RequestEventListenerImpl implements RequestEventListener {

    private final static Logger logger = LoggerFactory.getLogger(RequestEventListenerImpl.class);

    private final long start;

    public RequestEventListenerImpl() {
        this.start = System.currentTimeMillis();
    }

    public void onEvent(RequestEvent event) {
        ResourceMethod matchedResourceMethod = getMatchedResourceMethod(event);
        if (matchedResourceMethod == null) {
            return;
        }

        switch (event.getType()) {
            case REQUEST_MATCHED:
                Method definitionMethod = matchedResourceMethod
                        .getInvocable()
                        .getDefinitionMethod();

                logger.info("Resource matched: [{}.{}()]",
                        definitionMethod.getDeclaringClass(),
                        definitionMethod.getName());

                break;
            case FINISHED:
                definitionMethod = matchedResourceMethod
                        .getInvocable()
                        .getDefinitionMethod();

                logger.info("[{}.{}()] use {} ms",
                        new Object[]{
                                definitionMethod.getDeclaringClass(),
                                definitionMethod.getName(),
                                System.currentTimeMillis() - start
                        });

                break;
            default:
                break;
        }
    }

    private ResourceMethod getMatchedResourceMethod(RequestEvent event) {
        return event.getUriInfo().getMatchedResourceMethod();
    }
}
