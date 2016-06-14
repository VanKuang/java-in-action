package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

@Path(Constants.PATH_STREMING)
public class StreamingResource {

    @GET
    @Path("pdf")
    @Produces("application/pdf")
    public Response streaming() {
        final File file = new File("/Users/VanKuang/Downloads/AkkaJava.pdf");

        StreamingOutput streamingOutput = outputStream -> {
            try {
                FileChannel inputChannel = new FileInputStream(file).getChannel();
                WritableByteChannel outputChannel = Channels.newChannel(outputStream);
                inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        return Response.ok(streamingOutput)
                .status(Response.Status.OK)
                .header(HttpHeaders.CONTENT_LENGTH, file.length())
                .build();
    }
}
