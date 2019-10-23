package com.vs.kafka;

import com.vs.kafka.source.MySourceConnectorConfig;
import org.junit.Test;

public class MySourceConnectorConfigTest {
  @Test
  public void doc() {
    System.out.println(MySourceConnectorConfig.conf().toRst());
  }
}