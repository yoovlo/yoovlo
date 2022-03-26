package org.yoovlo.shr.java.ser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class Json_deser<T> implements Deserializer {
    public Class<T> klass;
    public ObjectMapper mpr;
    public Json_deser(Class lklass) {
        klass=lklass;
        mpr=new ObjectMapper();
    }
    @Override
    public void configure(Map map, boolean b) {

    }
    @Override
    public Object deserialize(String s,byte[] bytes) {
        T obj=null;
        try {
            obj=mpr.readValue(bytes,klass);
        } catch(Exception e) {

        }
        return obj;
    }
    @Override
    public void close() {}
}

