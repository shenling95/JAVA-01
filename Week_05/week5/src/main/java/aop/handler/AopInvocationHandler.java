package aop.handler;

import aop.annotation.Log;
import aop.annotation.Transaction;
import aop.aspect.Aspect;
import aop.aspect.LogAspect;
import aop.aspect.TransactionAspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AopInvocationHandler<T> implements InvocationHandler {

    private T t;
    private Aspect aspect;

    public AopInvocationHandler(T t) {
        this.t = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        Annotation[] annotations = method.getDeclaredAnnotations();
        if (annotations.length == 0) {
            System.out.println("没有注解");
            result = method.invoke(this.t, args);
        }
        for (Annotation annotation : annotations) {
            System.out.println("注解的类型为：" + annotation.annotationType());
            if (annotation.annotationType() == Transaction.class) {
                Transaction t = method.getAnnotation(Transaction.class);
                System.out.println("注解的内容为：" + t.value());
                aspect = new TransactionAspect();
                aspect.before();
                result = method.invoke(this.t, args);
                aspect.after();
            }
            if (annotation.annotationType() == Log.class) {
                Log l = method.getAnnotation(Log.class);
                System.out.println("注解的内容为：" + l.value());
                aspect = new LogAspect();
                aspect.before();
                result = method.invoke(this.t, args);
                aspect.after();
            }

        }
        return null;
    }
}
