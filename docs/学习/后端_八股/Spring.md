# Spring
> Spring 是个java企业级应用的开源开发框架。Spring主要用来开发Java应用，但是有些扩展是针对构建J2EE平台的web应用。Spring 框架目标是简化Java企业级应用开发，并通过POJO为基础的编程模型促进良好的编程习惯。 
>
> POJO编程模型指的就是普通的JavaBean，通常它有一些参数作为对象的属性，然后对每个属性定义了get和set方法作为访问接口，在实际开发中这种方式非常常见。

## SpringBoot、SpringMVC、Spring有什么区别 
1. Spring 是⼀个全⾯的Java企业级应⽤程序开发框架，提供了⼴泛的功能，包括依赖注⼊、AOP（⾯向切⾯编程）、 事务管理等。 
2. Spring MVC 是 Spring 中的⼀个很重要的模块，主要赋予 Spring 快速构建 MVC 架构的 Web 程序的能⼒。MVC 是 模型(Model)、视图(View)、控制器(Controller)的简写，其核⼼思想是通过将业务逻辑、数据、显示分离来组织代码。 主要关注于处理Web请求、管理⽤户会话、控制应⽤程序流程等。 
3. Springboot是⼀个微服务框架，延续了spring框架的核⼼思想IOC和AOP，简化了应⽤的开发和部署。Spring Boot 是为了简化Spring应⽤的创建、运⾏、调试、部署等⽽出现的，提供约定⼤于配置的⽅式, 使⽤它可以做到专注于 Spring应⽤的开发，⽽⽆需过多关注XML的配置。可以更快速地构建独⽴的、⾃包含的Spring应⽤程序。

简⽽⾔之，Spring是⼀个全⾯的框架，Spring MVC是Spring框架的⼀部分，专注于Web应⽤程序开发，⽽Spring  Boot是基于Spring的微服务框架，旨在简化和加速Spring应⽤程序的开发。 

## 你们项目中为什么使用Spring框架？ 
这么问的话，就直接说Spring框架的好处就可以了。比如说Spring有以下特点： 
- 轻量：Spring 是轻量的，基本的版本大约2MB。 
- 控制反转(Inversion of Control)：Spring通过控制反转实现了松散耦合，对象们给出它们的依赖，而不是创建或查找依赖的对象们。 
- 面向切面的编程(AOP)：Spring支持面向切面的编程，并且把应用业务逻辑和系统服务分开。 
- 容器：Spring 包含并管理应用中对象的生命周期和配置。 
- MVC框架：Spring的WEB框架是个精心设计的框架，是Web框架的一个很好的替代品。 
- 事务管理：Spring 提供一个持续的事务管理接口，可以扩展到上至本地事务下至全局事务（JTA）。 
- 异常处理：Spring 提供方便的API把具体技术相关的异常（比如由JDBC，Hibernate or JDO抛 出的）转化为一致的unchecked 异常。 

## Autowired和Resource关键字的区别？ 
@Resource和@Autowired都是做bean的注入时使用，其实@Resource并不是Spring的注解，它的包是javax.annotation.Resource，需要导入，但是Spring支持该注解的注入。 

1. 共同点:两者都可以写在字段和setter方法上。两者如果都写在字段上，那么就不需要再写setter方法。 
2. 不同点:
   - @Autowired为Spring提供的注解，需要导入包 org.springframework.beans.factory.annotation.Autowired;只按照byType注入。 @Autowired注解是按照类型（byType）装配依赖对象，默认情况下它要求依赖对象必须存在，如果允许null值，可以设置它的required属性为false。如果我们想使用按照名称（byName）来装配，可以结合@Qualifier注解一起使用。
    ```java
    public class TestServiceImpl {     // 下面两种@Autowired只要使用一种即可     
            @Autowired     
            private UserDao userDao; // 用于字段上          
            
            @Autowired    
            public void setUserDao(UserDao userDao) { // 用于属性的方法上        
                this.userDao = userDao;    
            } 
    } 
    ```

    ```java
    public class TestServiceImpl {     
        @Autowired     
        @Qualifier("userDao")    
        private UserDao userDao;  
    } 
    ```
   - @Resource默认按照ByName自动注入，由J2EE提供，需要导入包javax.annotation.Resource。 @Resource有两个重要的属性：name和type，而Spring将@Resource注解的name属性解析为 bean的名字，而type属性则解析为bean的类型。所以，如果使用name属性，则使用byName的自 动注入策略，而使用type属性时则使用byType自动注入策略。如果既不制定name也不制定type属 性，这时将通过反射机制使用byName自动注入策略。 
    ```java
    public class TestServiceImpl {     // 下面两种@Resource只要使用一种即可     
        @Resource(name="userDao")     
        private UserDao userDao; // 用于字段上          
        
        @Resource(name="userDao")    
        public void setUserDao(UserDao userDao) { // 用于属性的setter方法上        
            this.userDao = userDao;    
        } 
    } 
    ```
    注：最好是将@Resource放在setter方法上，因为这样更符合面向对象的思想，通过set、get去操作属性，而不是直接去操作属性。 @Resource装配顺序： 1、如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛 出异常。 2、如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常。 3、如果指定了type，则从上下文中找到类似匹配的唯一bean进行装配，找不到或是找到多个，都会 抛出异常。 4、如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配， 则回退为一个原始类型进行匹配，如果匹配则自动装配。 @Resource的作用相当于@Autowired，只不过@Autowired按照byType自动注入。 

## 依赖注入的方式有几种，各是什么? 
- 构造器注入:将被依赖对象通过构造函数的参数注入给依赖对象，并且在初始化对象的时候注入。 优点：对象初始化完成后便可获得可使用的对象。 缺点：当需要注入的对象很多时，构造器参数列表将会很长；不够灵活。若有多种注入方式，每种方式只需注入指定几个依赖，那么就需要提供多个重载的构造函数，麻烦。
- setter方法注入:IoC Service Provider通过调用成员变量提供的setter函数将被依赖对象注入给依赖类。 优点：灵活。可以选择性地注入需要的对象。 缺点：由于尚未注入被依赖对象,依赖对象初始化完成后，因此还不能使用。 
