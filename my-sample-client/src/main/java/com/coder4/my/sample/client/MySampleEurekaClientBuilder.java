/**
 * @(#)MySampleClientBuilder.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import com.coder4.my.sample.thrift.MySampleThrift;
import com.coder4.my.sample.thrift.MySampleThrift.Client;
import com.coder4.sbmvt.thrift.client.ThriftClient;
import com.coder4.sbmvt.thrift.client.builder.EurekaThriftClientBuilder;

/**
 * @author coder4
 */
public class MySampleEurekaClientBuilder extends EurekaThriftClientBuilder<Client> {

    public MySampleEurekaClientBuilder(String serviceName) {
        setThriftClass(MySampleThrift.class);

        setThriftServiceName(serviceName);
    }

    public static ThriftClient<Client> buildClient(String serviceName) {
        return new MySampleEurekaClientBuilder(serviceName).build();
    }

}