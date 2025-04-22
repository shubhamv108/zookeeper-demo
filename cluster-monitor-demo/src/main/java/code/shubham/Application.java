package code.shubham;

import code.shubham.commons.ConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.UUID;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class Application {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        new Application().register();
    }

    private void register() throws IOException, InterruptedException, KeeperException {
        String id = UUID.randomUUID().toString();
        ZooKeeper zooKeeper = new ZooKeeper(ConfigUtils.get("zookeeperConnectString", "localhost:2181,localhost:2182,localhost:2183"), 15000, null);
        String response = zooKeeper.create(String.format("/service1/%s", id), id.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, null);
        log.info(response);
        Thread.sleep(100_000_000);
    }

}
