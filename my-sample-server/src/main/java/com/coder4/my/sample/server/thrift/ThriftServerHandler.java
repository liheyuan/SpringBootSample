/**
 * @(#)RpcHandler.java, Jul 31, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.server.thrift;

import com.coder4.my.sample.thrift.MySampleThrift;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class ThriftServerHandler implements MySampleThrift.Iface {

    @Override
    public String sayHi() throws TException {
        return "Hi, Spring Boot.";
    }

}