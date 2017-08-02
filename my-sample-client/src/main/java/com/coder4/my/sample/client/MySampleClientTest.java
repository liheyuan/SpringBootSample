/**
 * @(#)MySampleClientTest.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import com.coder4.my.sample.thrift.MySampleThrift;

import java.util.concurrent.Future;

/**
 * @author coder4
 */
public class MySampleClientTest {

    public static void test1() {
        ThriftClient<MySampleThrift.Client> client = MySampleClientBuilder
                .buildClient("127.0.0.1", 3000);

        String ret = client.call(cli -> cli.sayHi());
        System.out.println(ret);
    }

    public static void test2() throws Exception {
        ThriftClient<MySampleThrift.Client> client = MySampleClientBuilder
                .buildClient("127.0.0.1", 3000);

        Future<String> fRet = client.asyncCall(cli -> cli.sayHi());
        System.out.println(fRet.get());
    }

    public static void main(String [] args) throws Exception {
        test1();
        test2();
    }

}