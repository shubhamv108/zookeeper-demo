package code.shubham;

import code.shubham.commons.ConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(ConfigUtils.get("zookeeperConnectString", "localhost:2181,localhost:2182,localhost:2183"), 15000, event -> {
            log.info("----------------------------------------------");
            log.info("got the event for node = {}", event.getPath());
            log.info("the event type = {}", event.getType());
            log.info("----------------------------------------------");
        });

        zooKeeper.create("/node", "data".getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, null);
        zooKeeper.create("/node/child", "child".getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, null);

        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/node", true, stat);
        log.info(new String(data));
        log.info("version={}", stat.getVersion());

        List<String> children = zooKeeper.getChildren("/node", true);
        children.forEach(child -> log.info("child={}", child));

        zooKeeper.setData("/node", "updatedData".getBytes(), -1); // -1 means that it will udate the latest version
        data = zooKeeper.getData("/node", true, stat);

        zooKeeper.delete("/node/child", -1);
        zooKeeper.delete("/node", -1);

        Thread.sleep(100_000);
    }
}