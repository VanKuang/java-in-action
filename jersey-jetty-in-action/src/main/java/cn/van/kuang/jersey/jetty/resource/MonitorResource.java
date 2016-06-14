package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(Constants.PATH_MONITOR)
public class MonitorResource {

    @Inject
    Provider<MonitoringStatistics> provider;

    @GET
    public String getStatics() {
        final MonitoringStatistics monitoringStatistics = provider.get();
        final ExecutionStatistics requestStatistics = monitoringStatistics.getRequestStatistics();
        final TimeWindowStatistics timeWindowStatistics = requestStatistics.getTimeWindowStatistics().get(0);

        return "Request count ["
                + timeWindowStatistics.getRequestCount()
                + "], avg request processing time ["
                + timeWindowStatistics.getAverageDuration()
                + "] ms, max processing time ["
                + timeWindowStatistics.getMaximumDuration()
                + "] ms, min processing time ["
                + timeWindowStatistics.getMinimumDuration()
                + "] ms";
    }

}
