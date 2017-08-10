/**
 * @(#)MySampleClientTest.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package client;

import com.coder4.my.sample.client.MySampleEasyClientBuilder;
import com.coder4.my.sample.client.ThriftClient;
import com.coder4.my.sample.thrift.MySampleThrift;
import com.coder4.my.sample.thrift.MySampleThrift.Client;

import java.util.concurrent.Future;

/**
 * @author coder4
 */
public class MySampleEasyClientTest {

    public static void test1() {
        ThriftClient<Client> client = MySampleEasyClientBuilder
                .buildClient("127.0.0.1", 3000);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i<10000; i++) {
            String ret = client.call(cli -> cli.sayHi());
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        //System.out.println(ret);
    }

    public static void test2() throws Exception {
        ThriftClient<MySampleThrift.Client> client = MySampleEasyClientBuilder
                .buildClient("127.0.0.1", 3000);

        Future<String> fRet = client.asyncCall(cli -> cli.sayHi());
        System.out.println(fRet.get());
    }

    public static void main(String [] args) throws Exception {
        test1();
        //test2();
    }

}