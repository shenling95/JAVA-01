package work;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Caller {

    public static void main(String[] args) {
        Caller caller = new Caller();
        ExecutorService executor = Executors.newCachedThreadPool();

        Callable c = (Callable<Integer>) caller::func;
        FutureTask<Integer> f1 = new FutureTask<Integer>(c);
        Thread t1 = new Thread(f1);
        t1.start();
        try {
            System.out.println("****第1种*****使用Thread类启动，用FutureTask封装Callable");
            System.out.println(f1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future fu1 = executor.submit(c);
        try {
            System.out.println("****第2种*****使用线程池提交Callable任务");
            System.out.println(fu1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        Future<?> fu2 = executor.submit(f1);
//        try {
//            System.out.println("****第3种*****");
//            System.out.println(fu2.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


        /////////////////////////////////////

        Map<String, Integer> value = new HashMap<>();
        Runnable run = () -> value.put("result", caller.func());
//        new Thread(run).start();
//        System.out.println("****第4种*****");
//        System.out.println(value.toString());

        FutureTask f2 = new FutureTask(run, value);
        Thread t2 = new Thread(f2);
        t2.start();
        try {
            System.out.println("****第3种*****使用线程启动，FutureTask封装runnable，加上一个字典，在run方法里操作字典得到返回值");
            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            executor.submit(run,value).get();
            System.out.println("****第4种*****使用线程池启动，同上");
            System.out.println(value.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        System.out.println("****第5种*****");
//        System.out.println(value.toString());

//        Future<?> fu3 = executor.submit(f2);
//        try {
//            System.out.println("****第6种*****");
//            System.out.println(fu3.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


        /////////////////////////////////////
        CompletableFuture cf = CompletableFuture.supplyAsync(()->{
            return caller.func();
        });
        System.out.println("****第5种*****使用CompletableFuture，Callable接口");
        try {
            System.out.println(cf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////
        CompletableFuture cf2 = CompletableFuture.runAsync(()->{
            System.out.println("****第6种*****使用CompletableFuture，runnable接口，这个好像没办法返回值");
            System.out.println(caller.func());
        });

    }

    private int func(){
        return 10;
    }
}
