/**
 * @(#)MySampleClientBuilder.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import com.coder4.my.sample.thrift.MySampleThrift;

/**
 * @author coder4
 */
public class MySampleClientBuilder extends EasyThriftClientBuilder<MySampleThrift.Client> {

    public MySampleClientBuilder(String host, int port) {
        setThriftClass(MySampleThrift.class);

        setHost(host);
        setPort(port);
    }

    public static ThriftClient<MySampleThrift.Client> buildClient(String host, int port) {
        return new MySampleClientBuilder(host, port).build();
    }

}