package code.shubham;

import code.shubham.commons.ConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class ClusterMonitor {

    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        new ClusterMonitor().monitor("/service1");
    }

    private void monitor(String parentZNode) throws IOException, InterruptedException, KeeperException {
        this.zooKeeper = new ZooKeeper(ConfigUtils.get("zookeeperConnectString", "localhost:2181,localhost:2182,localhost:2183"), 15000, event -> {
            log.info("----------------------------------------------");
            log.info("got the event for node = {}", event.getPath());
            log.info("the event type = {}", event.getType());
            log.info("----------------------------------------------");

            try {
                watch(this.zooKeeper, parentZNode);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            } catch (KeeperException exception) {
                throw new RuntimeException(exception);
            }
        });

        if (this.zooKeeper.exists(parentZNode, false) == null) {
            String createResponse = this.zooKeeper.create("/service1", "data".getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, null);
            log.info("ZNode-service1-createResponse={}", createResponse);
        }
        watch(zooKeeper, parentZNode);

        Thread.sleep(100_000_000);
    }

    private void watch(ZooKeeper zooKeeper, String node) throws InterruptedException, KeeperException {
        if (zooKeeper == null)
            return;

        List<String> children = zooKeeper.getChildren(node, true, null);
        children.forEach(child -> log.info("child={}", child));
    }

}
