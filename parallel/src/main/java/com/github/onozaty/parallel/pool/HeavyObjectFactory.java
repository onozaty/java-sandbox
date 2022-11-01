package com.github.onozaty.parallel.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.github.onozaty.parallel.HeavyObject;

public class HeavyObjectFactory extends BasePooledObjectFactory<HeavyObject> {

    @Override
    public HeavyObject create() throws Exception {
        return new HeavyObject();
    }

    @Override
    public PooledObject<HeavyObject> wrap(HeavyObject obj) {
        return new DefaultPooledObject<HeavyObject>(obj);
    }
}
