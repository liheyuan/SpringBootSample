/**
 * @(#)MySampleEurekaClientTest.java, Aug 10, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package client;

import com.coder4.my.sample.client.MySampleEurekaClientBuilder;
import com.coder4.my.sample.client.ThriftClient;
import com.coder4.my.sample.thrift.MySampleThrift;

/**
 * @author coder4
 */
public class MySampleEurekaClientTest {

    public static void main(String[] args) {
        String serviceName = "my-sample-thrift-server";
        ThriftClient<MySampleThrift.Client> client = MySampleEurekaClientBuilder.buildClient(serviceName);
        String str = client.call(cli -> cli.sayHi());
        System.out.println(str);
    }

}