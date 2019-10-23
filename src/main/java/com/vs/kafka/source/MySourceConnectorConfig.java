package com.vs.kafka.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

import java.util.HashMap;
import java.util.Map;


public class MySourceConnectorConfig extends AbstractConfig {

    public static final String USERNAME_CONFIG = "opensky.username";
    private static final String USERNAME_DOC = "The username to use with the OpenSKY API.";

    public static final String PASSWORD_CONFIG = "opensky.password";
    private static final String PASSWORD_DOC = "The password to use with the OpenSKY API.";

    public static final String ICAO24_CONFIG = "opensky.icao24";
    private static final String ICAO24_DOC = "Transponder to trace";

    public static final String TOPIC_CONFIG = "opensky.topic";
    private static final String TOPIC_DEFAULT = "opensky";
    private static final String TOPIC_DOC = "Topic to publish data to.";

    public MySourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public MySourceConnectorConfig(Map<String, String> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        ConfigDef configDef = new ConfigDef()
                .define(USERNAME_CONFIG, Type.STRING, Importance.HIGH, USERNAME_DOC)
                .define(PASSWORD_CONFIG, Type.STRING, Importance.HIGH, PASSWORD_DOC)
                .define(ICAO24_CONFIG, Type.STRING, Importance.LOW, ICAO24_DOC)
                .define(TOPIC_CONFIG, ConfigDef.Type.STRING, TOPIC_DEFAULT, new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, TOPIC_DOC);
        return configDef;
    }

    public Map<String, String> toMap() {
        Map<String, ?> uncastProperties = this.values();
        Map<String, String> config = new HashMap<>(uncastProperties.size());
        uncastProperties.forEach((key, valueToBeCast) -> config.put(key, valueToBeCast.toString()));
        return config;
    }

}
