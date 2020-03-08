package cn.van.kuang.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TestEtcd {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestEtcd.class);

    public static void main(String[] args) throws Exception {
        final Client client = Client.builder().endpoints("http://localhost:2379", "http://localhost:2380").build();
        final KV kvClient = client.getKVClient();

        doGet(kvClient, "foo");

        putString(kvClient, "key", "value");
        putString(kvClient, "key", "value");

        doGet(kvClient, "key");

        kvClient.close();
        client.close();
    }

    private static void putString(final KV kvClient, final String key, final String value) throws Exception {
        Objects.requireNonNull(kvClient);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        kvClient.put(ByteSequence.from(key.getBytes()), ByteSequence.from(value.getBytes())).get();
    }

    private static void doGet(final KV kvClient, final String key) throws Exception {
        Objects.requireNonNull(kvClient);
        Objects.requireNonNull(key);

        final CompletableFuture<GetResponse> response = kvClient.get(ByteSequence.from(key.getBytes()));
        final GetResponse getResponse = response.get();
        for (final KeyValue keyValue : getResponse.getKvs()) {
            LOGGER.info("{} --> {}",
                    new String(keyValue.getKey().getBytes()),
                    new String(keyValue.getValue().getBytes()));
        }
    }

}
