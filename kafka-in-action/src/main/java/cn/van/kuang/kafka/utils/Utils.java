package cn.van.kuang.kafka.utils;

import cn.van.kuang.kafka.avro.Message;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.zookeeper.common.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public final class Utils {

    public static String getResourcePath() {
        return System.getProperty("user.dir")
                + File.separator
                + "kafka-in-action"
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator;
    }

    public static byte[] toByteArray(Message message) {
        GenericDatumWriter<Message> writer = new GenericDatumWriter<>(message.getSchema());
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Encoder e = EncoderFactory.get().binaryEncoder(os, null);
            writer.write(message, e);
            e.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T byteArrayToDatum(Schema schema, byte[] byteData) {
        GenericDatumReader<T> reader = new GenericDatumReader<>(schema);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(byteData);
            Decoder decoder = DecoderFactory.get().binaryDecoder(byteArrayInputStream, null);
            return reader.read(null, decoder);
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeStream(byteArrayInputStream);
        }
    }


    public static void main(String[] args) {
        System.out.println(getResourcePath());
    }

    private Utils() {
    }

}
