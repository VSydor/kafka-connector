package com.vs.kafka;

import com.vs.kafka.sink.MySinkConnectorConfig;
import org.junit.Test;

public class MySinkConnectorConfigTest {
  @Test
  public void doc() {
    System.out.println(MySinkConnectorConfig.conf().toRst());
  }
}
