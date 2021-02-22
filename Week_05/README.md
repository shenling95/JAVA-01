学习笔记

# 1、使用动态代理实现一个简单的aop

转载自：https://blog.csdn.net/hq86937375/article/details/98481629

**静态代理和动态代理**

根据加载被代理类的时机不同，将代理分为静态代理和动态代理。如果我们在代码编译时就确定了被代理的类是哪一个，那么就可以直接使用静态代理；如果不能确定，那么可以使用类的动态加载机制，在代码运行期间加载被代理的类这就是动态代理，比如RPC框架和Spring AOP机制。

------

**静态代理**

由程序员创建或特定工具自动生成源代码，也就是在编译时就已经将接口，被代理类，代理类等确定下来。在程序运行之前，代理类的.class文件就已经生成。

代理模式最主要的就是有一个公共接口（UserManager），一个具体的类（UserManagerImp），一个代理类（UserManagerImpProxy）,代理类持有具体类的实例，代为执行具体类实例方法。上面说到，代理模式就是在访问实际对象时引入一定程度的间接性，因为这种间接性，可以附加多种用途。这里的间接性就是指不直接调用实际对象的方法，那么我们在代理过程中就可以加上一些其他用途。

![在这里插入图片描述](E:\java01\Week_05\watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hxODY5MzczNzU=,size_16,color_FFFFFF,t_70)

***当在代码阶段规定这种代理关系，Proxy类通过编译器编译成class文件，当系统运行时，此class已经存在了。这种静态的代理模式固然在访问无法访问的资源，增强现有的接口业务功能方面有很大的优点，但是大量使用这种静态代理，会使我们系统内的类的规模增大，并且不易维护；并且由于Proxy和RealSubject的功能 本质上是相同的，Proxy只是起到了中介的作用，这种代理在系统中的存在，导致系统结构比较臃肿和松散。***

**动态代理**

动态代理：代理类并不是在Java代码中定义的，而是在运行时根据我们在Java代码中的“指示”动态生成的。相比于静态代理， 动态代理的优势在于可以很方便的对代理类的函数进行统一的处理。

动态代理模式的结构跟上面的静态代理模式稍微有所不同，**多引入了一个InvocationHandler角色**。

![在这里插入图片描述](E:\java01\Week_05\watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly19ibG9nLmNzZG4ubmV0L2hxODY5MzczNzU=,size_16,color_FFFFFF,t_70)

动态代理工作的基本模式就是将自己的方法功能的实现交给 InvocationHandler角色，外界对Proxy角色中的每一个方法的调用，Proxy角色都会交给InvocationHandler来处理，而InvocationHandler则调用具体对象角色的方法。如下图所示：

![在这里插入图片描述](E:\java01\Week_05\watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L22hxODY5MzczNzU=,size_16,color_FFFFFF,t_70)

**JDK的动态代理创建机制----通过接口**

1. 获取 RealSubject上的所有接口列表；
2. 确定要生成的代理类的类名，默认为：com.sum.$ProxyXXXX ；
3. 根据需要实现的接口信息，在代码中动态创建该Proxy类的字节码；
4. 将对应的字节码转换为对应的class 对象；
5. 创建InvocationHandler 实例handler，用来处理Proxy所有方法调用；
6. Proxy 的class对象 以创建的handler对象为参数，实例化一个proxy对象

在java的java.lang.reflect包下提供了一个Proxy类和一个InvocationHandler接口，通过这个类和这个接口可以生成JDK动态代理类和动态代理对象。

> newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
> 返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序

在调用代理对象中的每一个方法时，在代码内部，都是直接调用了InvocationHandler 的invoke方法，而invoke方法根据代理类传递给自己的method参数来区分是什么方法。

> invoke(Object proxy,Method method,Object[] args)
> 在代理实例上处理方法调用并返回结果

![image-20210221200332746](E:\java01\Week_05\image-20210221200332746.png)

# 2、spring bean的装配

转载自：https://www.cnblogs.com/wmyskxz/p/8830632.html

在 Spring 中提供了 3 种方法进行配置：

- 在 XML 文件中显式配置
- 在 Java 的接口和类中实现配置
- 隐式 Bean 的发现机制和自动装配原则

1.**最优先：通过隐式 Bean 的发现机制和自动装配的原则。**
基于约定由于配置的原则，这种方式应该是最优先的

- **好处：**减少程序开发者的决定权，简单又不失灵活。

2.**其次：Java 接口和类中配置实现配置**
在没有办法使用自动装配原则的情况下应该优先考虑此类方法

- **好处：**避免 XML 配置的泛滥，也更为容易。
- **典型场景：**一个父类有多个子类，比如学生类有两个子类，一个男学生类和女学生类，通过 IoC 容器初始化一个学生类，容器将无法知道使用哪个子类去初始化，这个时候可以使用 Java 的注解配置去指定。

3.**最后：XML 方式配置**
在上述方法都无法使用的情况下，那么也只能选择 XML 配置的方式。

- **好处：**简单易懂（当然，特别是对于初学者）
- **典型场景：**当使用第三方类的时候，有些类并不是我们开发的，我们无法修改里面的代码，这个时候就通过 XML 的方式配置使用了。

![image-20210221200226057](E:\java01\Week_05\image-20210221200226057.png)

# 3、单例的写法

![image-20210221202223324](C:\Users\dmm\AppData\Roaming\Typora\typora-user-images\image-20210221202223324.png)

# 4、maven/spring 的 profile 机制，都有什么用法？  

抄袭：https://www.cnblogs.com/wxgblogs/p/6696229.html

抄袭：https://www.cnblogs.com/huahua-test/p/11576907.html

# 5、给前面课程提供的 Student/Klass/School 实现自动配置和 Starter  

按照网上的教学是做了，但是没测试出来，因为不知道怎么添加进自己的仓库里。。。很尴尬，以后再来看看

https://blog.csdn.net/wo541075754/article/details/102947915

https://blog.csdn.net/weixin_41947378/article/details/108719520

![image-20210221231044520](E:\java01\Week_05\image-20210221231044520.png)

# 6、总结 Hibernate 与 MyBatis 的各方面异同点  

MyBatis 能更有效地对sql语句进行调优，而且可以集中管理xml中的mysql语句

Hibernate 则使用于快速开发且对sql的性能要求并不太高的场景，用编代码的方式来构建sql而不用自己写。

# 7、学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。  

转载自：https://blog.csdn.net/soonfly/article/details/65628372

我们在写mapper映射器的配置文件时，不经意间已经用到类型转换，不过是mybatis帮我们完成的。

```
<update id="update" parameterType="twm.mybatisdemo.pojo.User">
    update user set
    username=#{username},password=#{password},address=#{address}  where id=#{id}
</update>1234
```

像上面例子，只需要向update方法传入一个user对象，mybatis利用反射拆开user对象，然后根据对象中的字段在预处理语句（PreparedStatement）中设置参数，并且根据字段的类型，使用setXXX()方式设置相应的值。XXX可以是Integer，String，Date等Java类型。
同理，在从结果集（ResultSet）中取出一个值时，将使用rs.getInt、rs.getString、rs.getTimeStamp等方法将数据转换为Java对象。

那么问题来了，javaType和jdbcType的转换关系由谁来定呢？这就是类型处理器（type handlers）的功能所在。
比如java.lang.String转成JDBC.Varchar，java.lang.Integer转成JDBC.int。MyBatis使用内建的类型处理器能转换所有的基本数据类型、基本类型的包装类型、byte[] 、java.util.Date、java.sql.Date、java,sql.Time、java.sql.Timestamp、java枚举类型等。

不过对于自定义的类型怎么办呢？
假设上面的address在数据库字段类型是varchar(50)，但是在JAVA twm.mybatisdemo.pojo.User类中的address字段并不是String类型，而是一个自定义的`Address`类型：

```java
public class Address {
    String province;
    String city;
    public Address() {}
    public Address(String province, String city) {
        this.province = province;
        this.city = city;
    }
    //getter and setter......
}
```

因此，需要创建一个自定义的类型处理器(TypeHandler)了

**1、创建类型处理器：**

```java
class AddressTypeHandler extends BaseTypeHandler<Address>{
}
```

通过idea自动生成代码，可以看到父类BaseTypeHandler有四个方法需要我们实现，包括三个get方法，一个set方法。

**2、实现get方法**
三个get方法都是用于将数据库获得的记录集里的address字段转成java Address类型的对象。
所以我们首先给Address类增加一个构造方法，用途：**根据字符串生成一个实例对象。**

```java
//假设我们存储在db中的字符串是以","号分隔省市关系的
public Address(String address) {  
    if (address != null) {  
        String[] segments = address.split(",");  
        if (segments.length > 1) {  
            this.province = segments[0];
            this.city = segments[1];  
        } 
        else if (segments.length > 0) {  
            this.city = segments[0];  
        }  
    }  
}
```

然后实现AddressTypeHandler类中的三个get方法：（*有强迫症，不喜欢看arg0，arg1。所以顺便也改一下*）

```java
@Override
public Address getNullableResult(ResultSet rSet, String columnName)
        throws SQLException {
    return new Address(rSet.getString(columnName));
}

@Override
public Address getNullableResult(ResultSet rSet, int columnIndex)
        throws SQLException {
    return new Address(rSet.getString(columnIndex));
}

@Override
public Address getNullableResult(CallableStatement cStatement, int columnIndex)
        throws SQLException {
    return new Address(cStatement.getString(columnIndex));
}
```

**3、实现set方法**

set方法是用来将java类型转成数据库存储的类型。
这里我们先实现一下Address类的toString()方法（如果toString另有它用，那么就另外用一个方法名）

```java
@Override
public String toString() {
    return this.province + "," + this.city;
}
```

然后实现AddressTypeHandler类中的setNonNullParameter

```java
@Override
public void setNonNullParameter(PreparedStatement pStatement, int index,
        Address address, JdbcType jdbcType) throws SQLException {
    pStatement.setString(index, address.toString());
}
```

**4、在myBatis配置文件中注册该类**

```java
<!-- 注册自定义类型处理器 -->
<typeHandlers>
    <typeHandler handler="twm.mybatisdemo.type.AddressTypeHandler" />
</typeHandlers>
```

这里有个小插曲，我当时在配置文件最后插入了这段，结果提示错误：

> The content of element type “configuration” must match
> “(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)”.

原来在configuration里的标签，必须按照提示的这个顺序写，不然就报错。
因此typeHandlers必须放在environments前面，typeAliases后面。
回顾一下，是不是感觉跟序列化/反序列化这套路很相似？

# 8、研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
## 1）使用 JDBC 原生接口，实现数据库的增删改查操作。



## 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。



## 3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。  

前面两个实验做好了，第三个实验在配置的时候有个问题，查了两天都没搞定，求大明哥指点一下。

代码在org.shenling.week5.database.jdbc包下

```java
Exception in thread "main" java.lang.RuntimeException: java.lang.ClassCastException: Cannot cast com.mysql.cj.jdbc.Driver to javax.sql.DataSource
	at com.zaxxer.hikari.util.UtilityElf.createInstance(UtilityElf.java:92)
	at com.zaxxer.hikari.pool.PoolBase.initializeDataSource(PoolBase.java:294)
	at com.zaxxer.hikari.pool.PoolBase.<init>(PoolBase.java:91)
	at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:101)
	at com.zaxxer.hikari.HikariDataSource.<init>(HikariDataSource.java:71)
	at org.shenling.week5.database.jdbc.HikariDatasourceConfig.getConnection(HikariDatasourceConfig.java:55)
	at org.shenling.week5.database.jdbc.HikariDemoByUtils.main(HikariDemoByUtils.java:18)
Caused by: java.lang.ClassCastException: Cannot cast com.mysql.cj.jdbc.Driver to javax.sql.DataSource
	at java.lang.Class.cast(Class.java:3369)
	at com.zaxxer.hikari.util.UtilityElf.createInstance(UtilityElf.java:81)
```

错误信息如上，没搞懂。。。