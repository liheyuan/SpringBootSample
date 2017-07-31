/**
 * @(#)RpcHandler.java, Jul 31, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.server.rpc;

import com.coder4.my.sample.thrift.SampleThrift;
import org.apache.thrift.TException;

/**
 * @author coder4
 */
public class RpcHandler implements SampleThrift.Iface {

    @Override
    public String sayHi() throws TException {
        return "Hi, Spring Boot.";
    }

}