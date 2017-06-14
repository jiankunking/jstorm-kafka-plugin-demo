package jiankunking.kafkajstorm.bolts;


import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.TupleImplExt;
import jiankunking.kafkajstorm.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;




/**
 * Created by jiankunking on 2017/4/19 16:47.
 */
public class CustomBolt extends BaseBasicBolt {

    protected final Logger logger = LoggerFactory.getLogger(CustomBolt.class);

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String ss=ByteUtil.getStringFromByteArray((byte[]) ((TupleImplExt) input).get("bytes"));
            System.out.println(ss);
            logger.info(ss);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System.out.println("declareOutputFields");
    }
}
