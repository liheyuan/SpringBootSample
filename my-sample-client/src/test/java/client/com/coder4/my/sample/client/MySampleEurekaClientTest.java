/**
 * @(#)MySampleEurekaClientTest.java, Aug 10, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package client.com.coder4.my.sample.client;

import com.coder4.my.sample.client.MySampleEurekaClientBuilder;
import com.coder4.my.sample.client.ThriftClient;
import com.coder4.my.sample.thrift.MySampleThrift;

/**
 * @author coder4
 */
public class MySampleEurekaClientTest {

    public static void main(String[] args) throws InterruptedException {
        String serviceName = "my-sample-thrift-server";
        ThriftClient<MySampleThrift.Client> client = MySampleEurekaClientBuilder.buildClient(serviceName);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String str = client.call(cli -> cli.sayHi());
            // System.out.println(str);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}