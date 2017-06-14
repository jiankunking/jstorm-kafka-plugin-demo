package jiankunking.kafkajstorm.topologies;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import com.alibaba.jstorm.client.ConfigExtension;
import jiankunking.kafkajstorm.bolts.CustomBolt;
import jiankunking.kafkajstorm.kafka.KafkaSpout;
import jiankunking.kafkajstorm.kafka.KafkaSpoutConfig;
import jiankunking.kafkajstorm.util.PropertiesUtil;

import java.util.Map;

/**
 * Created by jiankunking on 2017/4/19 16:27.
 * 拓扑图 入口类
 */
public class CustomCounterTopology {

    /**
     * 入口类，即提交任务的类
     *
     * @param args 0：配置文件位jar包中的custom.counter.properties文件
     *             会按照绝对路径去加载
     * @throws InterruptedException
     * @throws AlreadyAliveException
     * @throws InvalidTopologyException
     */
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        System.out.println("11111");
        PropertiesUtil propertiesUtil = new PropertiesUtil("/application.properties", false);
        Map propsMap = propertiesUtil.getAllProperty();
        KafkaSpoutConfig spoutConfig = new KafkaSpoutConfig(propertiesUtil.getProps());
        spoutConfig.configure(propsMap);
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout", new KafkaSpout(spoutConfig));
        builder.setBolt("customBolt", new CustomBolt(), 1).shuffleGrouping("kafkaSpout");
        //Configuration
        Config conf = new Config();
        conf.setDebug(false);
        //指定使用logback.xml
        ConfigExtension.setUserDefinedLogbackConf(conf, "%JSTORM_HOME%/conf/logback.xml");
        if (args != null && args.length > 0) {
            //提交到集群运行
            StormSubmitter.submitTopologyWithProgressBar("customCounterTopology", conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            //本地模式运行
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("CustomCounterTopology", conf, builder.createTopology());
        }
    }
}
