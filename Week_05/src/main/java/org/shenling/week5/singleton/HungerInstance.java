package org.shenling.week5.singleton;

/*这种方式和上面的方式其实类似，只不过将类实例化的过程放在了静态代码块中，也是在类装载的时候，就执行静态代码块中的代码，初始化类的实例。

优缺点和上面是一样的。*/
public class HungerInstance {


    private static HungerInstance instance;

    static {
        instance = new HungerInstance();
    }

    private HungerInstance() {
    }

    public static HungerInstance getInstance() {
        return instance;

    }
}
