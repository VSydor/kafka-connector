package com.vs.kafka.model;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.opensky.model.StateVector;

public class StateVectorSchema {

    /*
    *   "geoAltitude": 8465.82,
        "longitude": -95.6834,
        "latitude": 35.5197,
        "velocity": 237.56,
        "heading": 82.66,
        "verticalRate": 9.43,
        "icao24": "a4754b",
        "callsign": "N387A   ",
        "onGround": false,
        "lastContact": 1571752709,
        "lastPositionUpdate": 1571752709,
        "originCountry": "United States",
        "squawk": "3413",
        "spi": false,
        "baroAltitude": 8366.76,
        "positionSource": "ADS_B",
        "serials": null
    * */

    public static final String GEO_ALTITUDE = "geoAltitude";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String VELOCITY = "velocity";
    public static final String HEADING = "heading";
    public static final String ICAO_24 = "icao24";
    public static final String ORIGIN_COUNTRY = "originCountry";

    public static final Schema SCHEMA = SchemaBuilder.struct()
            .name(StateVectorSchema.class.getSimpleName())
            .field(GEO_ALTITUDE, Schema.OPTIONAL_FLOAT64_SCHEMA)
            .field(LONGITUDE, Schema.OPTIONAL_FLOAT64_SCHEMA)
            .field(LATITUDE, Schema.OPTIONAL_FLOAT64_SCHEMA)
            .field(VELOCITY, Schema.OPTIONAL_FLOAT64_SCHEMA)
            .field(HEADING, Schema.OPTIONAL_FLOAT64_SCHEMA)
            .field(ICAO_24, Schema.OPTIONAL_STRING_SCHEMA)
            .field(ORIGIN_COUNTRY, Schema.OPTIONAL_STRING_SCHEMA)
            .build();


    public static Schema stateVectorSchema() {
        return SCHEMA;
    }

    public static Struct toStruct(StateVector stateVector) {
        Struct struct = new Struct(stateVectorSchema())
                .put(GEO_ALTITUDE, stateVector.getGeoAltitude())
                .put(LONGITUDE, stateVector.getLongitude())
                .put(LATITUDE, stateVector.getLatitude())
                .put(VELOCITY, stateVector.getVelocity())
                .put(HEADING, stateVector.getHeading())
                .put(ICAO_24, stateVector.getIcao24())
                .put(ORIGIN_COUNTRY, stateVector.getOriginCountry());
        return struct;
    }
}
