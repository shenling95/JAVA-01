package aop;


import aop.handler.AopInvocationHandler;
import aop.test.Test;
import aop.test.TestImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class AopTest {

    public static void main(String[] args) {
        TestImpl test = new TestImpl();

        InvocationHandler testHandler = new AopInvocationHandler<TestImpl>(test);

        Test testProxy = (Test) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class<?>[] {Test.class}, testHandler);

        testProxy.method1();
        testProxy.method2();
        testProxy.method3();
        testProxy.method4();
    }
}
