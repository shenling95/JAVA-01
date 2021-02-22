package aop.aspect;

public class LogAspect implements Aspect {
    @Override
    public void before() {
        System.out.println("调用前");
    }

    @Override
    public void after() {
        System.out.println("调用后");
    }

    @Override
    public void around() {

    }
}
