package org.yoovlo.shr.java.ser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;

public class Json_ser<T> implements Serializer<T> {
    public Class<T> klass;
    public ObjectMapper mpr;
    public Json_ser(Class<T> lklass) {
        klass=lklass;
        mpr=new ObjectMapper();
        mpr.setSerializationInclusion(Include.NON_NULL);
    }
    @Override
    public void configure(Map map, boolean b) {

    }
    @Override
    public byte[] serialize(String topic,T v) {
        byte[] b=null;
        try {
            b=mpr.writeValueAsBytes(v);
        } catch(Exception e) {

        }
        return b;
    }
    @Override
    public void close() {}
}

