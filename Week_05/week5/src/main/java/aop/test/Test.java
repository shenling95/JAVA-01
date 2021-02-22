package aop.test;

import aop.annotation.Log;
import aop.annotation.Transaction;

public interface Test {

    @Transaction
    void method1();

    @Log
    void method2();

    @Transaction
    @Log
    void method3();

    void method4();
}
