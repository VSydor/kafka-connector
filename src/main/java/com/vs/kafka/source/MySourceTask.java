package com.vs.kafka.source;

import com.google.common.collect.Lists;
import com.vs.kafka.VersionUtil;
import com.vs.kafka.model.StateVectorSchema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.vs.kafka.source.MySourceConnectorConfig.*;

public class MySourceTask extends SourceTask {

    static final Logger LOGGER = LoggerFactory.getLogger(MySourceTask.class);
    private OpenSkyApi openSkyApi;
    private String icao24;
    private String topic;

    private Long interval = 10000L;
    private Long last_execution = 0L;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        //TODO: Do things here that are required to start your task. This could be open a connection to a database, etc.
        setupTaskConfig(map);
    }

    @Override
    public List<SourceRecord> poll() {
        if (System.currentTimeMillis() > (last_execution + interval)) {
            last_execution = System.currentTimeMillis();
            OpenSkyStates states = getStates(icao24);
            if (Objects.nonNull(states)) {
                return toSourceRecords(states);
            }
        }
        return Collections.emptyList();
    }

    private List<SourceRecord> toSourceRecords(OpenSkyStates states) {

        List<StateVector> stateVectors = Lists.newArrayList(states.getStates());
        ArrayList<SourceRecord> records = new ArrayList<>();
        for (StateVector stateVector : stateVectors) {
            LOGGER.info("State vector: {}", stateVector);
            records.add(
                    new SourceRecord(
                            // TODO: populate these!
                            new HashMap<>(),
                            new HashMap<>(),
                            topic,
                            StateVectorSchema.stateVectorSchema(),
                            StateVectorSchema.toStruct(stateVector)));
        }

        return records;
    }

    @Override
    public void stop() {
        //TODO: Do whatever is required to stop your task.
        LOGGER.info("Stopping!");
    }

    private OpenSkyStates getStates(String icao24) {
        OpenSkyStates states = new OpenSkyStates();
        try {
            // Calling OpenSky API
            states = openSkyApi.getStates(0, new String[]{icao24});
            LOGGER.info("Got {} states.", states.getStates().size());

        } catch (Exception e) {
            LOGGER.error("Oops! Something went wrong!", e);
        }
        return states;
    }

    private void setupTaskConfig(Map<String, String> props) {
        String username = props.get(USERNAME_CONFIG);
        String password = props.get(PASSWORD_CONFIG);
        this.openSkyApi = new OpenSkyApi(username, password);
        this.topic = props.get(TOPIC_CONFIG);
        this.icao24 = props.get(ICAO24_CONFIG);
    }
}