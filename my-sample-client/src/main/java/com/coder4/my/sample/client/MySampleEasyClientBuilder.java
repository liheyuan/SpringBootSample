/**
 * @(#)MySampleClientBuilder.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import com.coder4.my.sample.thrift.MySampleThrift;
import com.coder4.my.sample.thrift.MySampleThrift.Client;
import com.coder4.sbmvt.thrift.client.ThriftClient;
import com.coder4.sbmvt.thrift.client.builder.EasyThriftClientBuilder;

/**
 * @author coder4
 */
public class MySampleEasyClientBuilder extends EasyThriftClientBuilder<Client> {

    public MySampleEasyClientBuilder(String host, int port) {
        setThriftClass(MySampleThrift.class);

        setHost(host);
        setPort(port);
    }

    public static ThriftClient<Client> buildClient(String host, int port) {
        return new MySampleEasyClientBuilder(host, port).build();
    }

}