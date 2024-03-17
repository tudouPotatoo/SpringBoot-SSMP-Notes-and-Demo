# 基于SpringBoot的SSMP整合案例

需求：使用SpringBoot整合Spring、SpringMVC、MyBatis-Plus，实现一个包含增、删、改、查、分页功能的模块。



## 项目结构

SSM框架开发目录结构

```
├──main
|  ├──java
|  |  └──com
|  |     └──tudou
|  |        ├── pojo                         //POJO包
|  |        ├── controller                   //控制器包
|  |        ├── service                      //Service接口包
|  |        |   └── impl                  	 //Service实现类包
|  |        |       └── BookMapper.java      //Service实现类
|  |        ├── mapper                       //Mapper接口包
|  |        |   └── BookMapper.java          //Mapper接口
|  |        ├── util                         //工具包
|  |        ├── exception                    //统一异常处理
|  |        ├── config                       //配置类包（可以配置拦截器等）
|  |        └── Application.class            //引导类
|  ├── resources                             //资源文件夹（配置文件）
|  |  └──application.yml                     //Spring配置文件
└──test
| ├── java                                   //测试Java代码，遵循和main目录包名相同的原则
|  |  └──com
|  |     └──tudou
|  |        ├── service                      
|  |        |       └── BookServiceTest.java //Service层测试类
|  |        ├── mapper                       
|  |        |   └── BookMapperTest.java      //Mapper层测试类
└──pom.xml                                   
```



## SSM项目开发流程

## 步骤

1. 创建一个SpringBoot项目
2. 创建数据库表
3. 引入需要的依赖
4. 进行相关配置
5. 创建pojo实体类
6. 写数据层逻辑
7. 测试数据层代码
8. 写业务层逻辑
9. 测试业务层代码
10. 写控制层代码
11. 测试控制层代码



## 实战

### 01 创建一个SpringBoot项目

新建一个module

![图1](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE1.png)

勾选需要的依赖

![图2](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE2.png)

如果这个地方没有变蓝，手动将项目设为maven项目

![图3](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE3.png)

![图4](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE4.png)

可以修改引导类的名字

![图5](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE5.png)

application.properties文件名修改为application.yml。【因为现在yaml格式使用频率更高】

![图6](C:/Users/tudou/Documents/Java%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99/SpringBoot%E5%AD%A6%E4%B9%A0/%E6%80%BB%E7%BB%93%E7%AC%94%E8%AE%B0/%E5%9F%BA%E4%BA%8ESpringBoot%E7%9A%84SSMP%E6%95%B4%E5%90%88%E6%A1%88%E4%BE%8B.assets/%E5%9B%BE6.png)



### 02 创建数据库表

~~~~mysql
-- ----------------------------
-- Table structure for tbl_book
-- ----------------------------
DROP TABLE IF EXISTS `tbl_book`;
CREATE TABLE `tbl_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_book
-- ----------------------------
INSERT INTO `tbl_book` VALUES (1, '三体', '科幻', '大刘的巅峰之作，将中国科幻推向世界舞台。总共分为三部曲：《地球往事》、《黑暗森林》、《死神永生》。');
INSERT INTO `tbl_book` VALUES (2, '格林童话', '童话', '睡前故事。');
INSERT INTO `tbl_book` VALUES (3, 'Spring 5设计模式', '计算机理论', '深入Spring源码剖析Spring源码中蕴含的10大设计模式');
INSERT INTO `tbl_book` VALUES (4, 'Spring MVC+ MyBatis开发从入门到项目实战', '计算机理论', '全方位解析面向Web应用的轻量级框架,带你成为Spring MVC开发高手');
INSERT INTO `tbl_book` VALUES (5, '轻量级Java Web企业应用实战', '计算机理论', '源码级剖析Spring框架,适合已掌握Java基础的读者');
INSERT INTO `tbl_book` VALUES (6, 'Java核心技术卷|基础知识(原书第11版)', '计算机理论', 'Core Java第11版，Jolt大奖获奖作品，针对Java SE9、10、 11全面更新');
INSERT INTO `tbl_book` VALUES (7, '深入理解Java虚拟机', '计算机理论', '5个维度全面剖析JVM,面试知识点全覆盖');
INSERT INTO `tbl_book` VALUES (8, 'Java编程思想(第4版)', '计算机理论', 'Java学习必读经典殿堂级著作!赢得了全球程序员的广泛赞誉');
INSERT INTO `tbl_book` VALUES (9, '零基础学Java (全彩版)', '计算机理论', '零基础自学编程的入门]图书，由浅入深，详解Java语言的编程思想和核心技术');
INSERT INTO `tbl_book` VALUES (10, '直播就该这么做:主播高效沟通实战指南', '市场营销', '李子柒、李佳琦、薇娅成长为网红的秘密都在书中');
INSERT INTO `tbl_book` VALUES (11, '直播销讲实战一本通', '市场营销', '和秋叶一起学系列网络营销书籍');
INSERT INTO `tbl_book` VALUES (12, '直播带货:淘宝、天猫直播从新手到高手', '市场营销', '一本教你如何玩转直播的书， 10堂课轻松实现带货月入3W+');
INSERT INTO `tbl_book` VALUES (13, 'Spring实战第5版', '计算机理论', 'Spring入门经典教程,深入理解Spring原理技术内幕');
INSERT INTO `tbl_book` VALUES (14, 'Spring 5核心原理与30个类手写实战', '计算机理论', '十年沉淀之作，写Spring精华思想');
~~~~



### 03 引入需要的依赖

除了创建项目时已经选中导入的依赖外，还需要的依赖有：

* MyBatis-plus
* Druid
* lombok

最终的pom.xml文件为：

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.tudou</groupId>
    <artifactId>springboot_08_SSMP_study</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.6.13</spring-boot.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--mysql驱动依赖-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!--mybatis-plus依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>
        <!--Druid依赖-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <mainClass>com.tudou.Application</mainClass>
                    <skip>true</skip>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
~~~~



### 04 进行相关配置

~~~~yaml
# 配置tomcat服务器启动端口
server:
  port: 80
# 配置Druid数据库信息
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/springboot_db?serverTimezone=UTC
      username: root
      password: password
# 配置mybatis-plus
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_  # 数据库表前缀
      id-type: auto   # 配置id自增
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 配置日志输出格式
~~~~



### 05 创建pojo实体类

~~~~java
package com.tudou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private String type;
    private String description;
}
~~~~



### 06 写数据层逻辑

数据层逻辑需要完成：

* BookMapper.java接口

* 配置分页拦截器



>**使用mybatis-plus写好的mapper层（包括Mapper接口内容、xxxMapper.xml文件都已经内置配置好了）**
>
>**需要进行如下配置：**
>
>1. XxxMapper接口需要添加@Mapper注解
>
>2. XxxMapper接口需要继承BaseMapper<Entity.class>类
>
>   形如：
>
>   ~~~~java
>   @Mapper
>   public interface BookMapper extends BaseMapper<Book>
>   ~~~~
>
>3. 如果要使用分页，需要配置对应的分页拦截器



* **BookMapper.java接口**

  ~~~~java
  package com.tudou.mapper;
  
  import com.baomidou.mybatisplus.core.mapper.BaseMapper;
  import com.tudou.pojo.Book;
  import org.apache.ibatis.annotations.Mapper;
  
  @Mapper
  public interface BookMapper extends BaseMapper<Book> {
  }
  ~~~~
  
  
  
* **配置分页拦截器**

  ~~~~java
  package com.tudou.config;
  
  import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
  import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class MyBatisPlusConfig {
      @Bean
      public MybatisPlusInterceptor mybatisPlusInterceptor() {
          // 定义Mybatis Plus的拦截器
          MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
          // 添加具体的拦截器
          mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
          return mpInterceptor;
      }
  }
  ~~~~



### 07 测试数据层代码

~~~~java
package com.tudou.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tudou.pojo.Book;
import com.tudou.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 增加一条数据
     */
    @Test
    public void testInsert() {
        Book book = new Book();
        book.setName("马斯克传");
        book.setType("人物传记");
        book.setDescription("《馬斯克》是美國商業巨頭、SpaceX/特斯拉執行長馬斯克的授權傳記。");
        int count = bookMapper.insert(book);
        System.out.println(count > 0);
    }

    /**
     * 删除一条数据
     */
    @Test
    public void testDelete() {
        int count = bookMapper.deleteById(14);
        System.out.print(count > 0);
    }

    /**
     * 根据id更新数据
     */
    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setId(2);
        book.setName("乔布斯传");
        book.setType("人物传记");
        book.setDescription("《賈伯斯傳》是史蒂夫·賈伯斯授權CNN和《時代雜誌》前高管沃爾特·艾薩克森撰寫的傳記。");
        int count = bookMapper.updateById(book);
        System.out.println(count);
    }

    /**
     * 根据id查找数据
     */
    @Test
    public void testSelectOne() {
        Book book = bookMapper.selectById(2);
        System.out.println(book);
    }

    /**
     * 查找所有数据
     */
    @Test
    public void testSelectAll() {
        List<Book> books = bookMapper.selectList(null);
        System.out.println(books);
    }

    /**
     * 分页查找数据
     * mybatis-plus使用分页要配置拦截器
     */
    @Test
    public void testSelectByPage() {
        int currPage = 1;
        int pageSize = 5;
        IPage<Book> page = new Page<>(currPage, pageSize);
        page = bookMapper.selectPage(page, null);
        System.out.println("current =====>" + page.getCurrent());
        System.out.println("size =====>" + page.getSize());
        System.out.println("total =====>" + page.getTotal());
        System.out.println("pages =====>" + page.getPages());
        System.out.println("records =====>" + page.getRecords());
    }

    /**
     * 根据条件查询数据
     */
    @Test
    public void testSelectByCondition() {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        lqw.like(Book::getType, "计算机");
        lqw.like(Book::getName, "Spring");
        List<Book> books = bookMapper.selectList(lqw);
        System.out.println(books);
    }
}
~~~~



### 08 写业务层逻辑

业务层逻辑需要完成：

* BookService.java接口

* BookServiceImpl.java实现类

  

>**使用Mybatis-plus写好的Service层代码需要配置：**
>
>1. Service接口继承IService<Entity.Class>类
>
>   形如：
>
>   ~~~~java
>   public interface BookService extends IService<Book>
>   ~~~~
>
>2. ServiceImpl类添加@Service注解
>
>3. ServiceImpl实现类实现Service接口
>
>4. ServiceImpl实现类继承ServiceImpl<XxxMapper.class, Entity.Class>类
>
>   这里的XxxMapper.class代表这个接口对应的mapper层的mapper接口
>
>   形如：
>
>   ~~~~java
>   @Service
>   public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService
>   ~~~~



* BookService.java接口

  ~~~~java
  package com.tudou.service;
  
  import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
  import com.baomidou.mybatisplus.extension.service.IService;
  import com.tudou.pojo.Book;
  import java.util.List;
  
  /**
   * 使用Mybatis-plus写好的Service层代码需要配置
   * 1. Service接口继承IService<Entity.Class>类
   * 形如：public interface BookService extends IService<Book>
   * 2. ServiceImpl类添加@Service注解
   * 3. ServiceImpl实现类实现Service接口
   * 4. ServiceImpl实现类继承ServiceImpl<XxxMapper.class, Entity.Class>类
   *    这里的XxxMapper.class代表这个接口对应的mapper层的mapper接口
   * 形如：@Service
   *      public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService
   */
  public interface BookService extends IService<Book> {
      /**
       * 分页查询
       * @param currPage 当前的页码
       * @param pageSize 每页的大小
       * @return 分页信息 包含总数据条数、查询到的分页数据、总页数等信息
       */
      Page<Book> getByPage(int currPage, int pageSize);
  
      /**
       *
       * @param type 图书类型
       * @param name 图书名字
       * @param description 图书描述
       * @return 查询到的书籍
       */
      List<Book> getByCondition(String type, String name, String description);
  }
  
  ~~~~

  

* BookServiceImpl.java实现类

  ~~~~java
  package com.tudou.service.impl;
  
  import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
  import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
  import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
  import com.tudou.mapper.BookMapper;
  import com.tudou.pojo.Book;
  import com.tudou.service.BookService;
  import org.apache.logging.log4j.util.Strings;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  
  import java.util.List;
  
  /**
   * 直接使用Mybatis-plus写好的service层方法
   * 如果内置的方法还不足够，可以在接口定义新方法，在实现类进行实现
   */
  @Service
  public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
  
      @Autowired
      private BookMapper bookMapper;
  
      /**
       * 分页查询
       * @param currPage 当前的页码
       * @param pageSize 每页的大小
       * @return 分页信息 包含总数据条数、查询到的分页数据、总页数等信息
       */
      @Override
      public Page<Book> getByPage(int currPage, int pageSize) {
          Page<Book> page = new Page<>(currPage, pageSize);
           page = bookMapper.selectPage(page, null);
          return page;
      }
  
      /**
       *
       * @param type 图书类型
       * @param name 图书名字
       * @param description 图书描述
       * @return 查询到的书籍
       */
      @Override
      public List<Book> getByCondition(String type, String name, String description) {
          LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
          if (Strings.isNotBlank(type)) {
              lqw.like(Book::getType, type);
          }
          if (Strings.isNotBlank(name)) {
              lqw.like(Book::getName, name);
          }
          if (Strings.isNotBlank(description)) {
              lqw.like(Book::getDescription, description);
          }
          return bookMapper.selectList(lqw);
      }
  }
  ~~~~

  

### 09 测试业务层代码

~~~~java
package com.tudou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tudou.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    /**
     * 增加一条数据
     */
    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("《燦爛千陽》");
        book.setType("长篇小说");
        book.setDescription("《燦爛千陽》，故事背景設定在阿富汗，描寫的是兩代主角因無情戰火摧殘家園而相遇相知的故事，時空跨越整整30年，涵括了阿富汗近代動盪不安的歷史。");
        System.out.println(bookService.save(book));
    }

    /**
     * 删除一条数据
     */
    @Test
    public void testRemove() {
        bookService.removeById(23);
    }

    /**
     * 根据id更新数据
     */
    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setId(12);
        book.setName("追风筝的人");
        book.setType("长篇小说");
        book.setDescription("以第一人稱講述了來自阿富汗首都喀布爾富人區的普什圖少年阿米爾和他的童年好友，父親的哈扎拉僕人哈山之間的故事。");
        System.out.println(bookService.updateById(book));
    }

    /**
     * 根据id查找数据
     */
    @Test
    public void testGetById() {
        Book book = bookService.getById(2);
        System.out.println(book);
    }

    /**
     * 查找所有数据
     */
    @Test
    public void testList() {
        List<Book> books = bookService.list();
        System.out.println(books);
    }

    /**
     * 分页查找数据
     */
    @Test
    public void testListByPage() {
        int currPage = 3;
        int pageSize = 5;
        IPage<Book> page = new Page<>(currPage, pageSize);
        page = bookService.page(page, null);
        System.out.println("current =====>" + page.getCurrent());
        System.out.println("size =====>" + page.getSize());
        System.out.println("total =====>" + page.getTotal());
        System.out.println("pages =====>" + page.getPages());
        System.out.println("records =====>" + page.getRecords());
    }

    /**
     * 根据条件查询数据
     */
    @Test
    public void testListByCondition() {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        lqw.like(Book::getType, "计算机");
        lqw.like(Book::getName, "Spring");
        List<Book> books = bookService.list(lqw);
        System.out.println(books);
    }
}
~~~~



### 10 写控制层代码

控制层逻辑需要完成：

* 统一响应格式
* 编写枚举类枚举服务器状态
* xxxController.java实现
* 统一异常处理



* 统一响应格式

  ~~~~java
  package com.tudou.controller.model;
  
  import com.tudou.utils.enumeration.StatusMsg;
  import lombok.Data;
  
  /**
   * 统一给前端返回的数据格式
   */
  @Data
  public class Result {
      /**
       * 状态码
       */
      private Integer code;
      /**
       * 业务处理后的数据
       */
      private Object data;
      /**
       * 执行情况信息
       * 如果服务器出故障 可以通过msg返回服务器的情况
       */
      private String msg;
  
      public Result() {
      }
  
      public Result(Integer code, Object data) {
          this.code = code;
          this.data = data;
      }
  
      public Result(Integer code, StatusMsg msg) {
          this.code = code;
          this.msg = msg.toString();
      }
  
      public Result(Integer code, Object data, StatusMsg msg) {
          this.code = code;
          this.data = data;
          this.msg = msg.toString();
      }
  }
  ~~~~

  

* 枚举类枚举服务器状态

  ~~~~java
  package com.tudou.utils.enumeration;
  
  public enum StatusMsg {
      SUCCESS("操作成功"),
      ERROR("服务器出错，请稍候");
  
      private final String message;
  
      StatusMsg(String message) {
          this.message = message;
      }
  
      @Override
      public String toString() {
          return this.message;
      }
  }
  ~~~~

  

* BookController.java实现

  ~~~~java
  package com.tudou.controller;
  
  import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
  import com.tudou.controller.model.Result;
  import com.tudou.pojo.Book;
  import com.tudou.service.BookService;
  import com.tudou.utils.enumeration.StatusMsg;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.HttpStatus;
  import org.springframework.web.bind.annotation.*;
  
  import java.util.List;
  
  //@Controller
  //@ResponseBody
  @RestController
  @RequestMapping("/books")
  public class BookController {
  
      @Autowired
      private BookService bookService;
  
      /**
       * 增加书籍
       */
      @PostMapping
      public Result addBook(@RequestBody Book book) {
          bookService.save(book);
          return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
      }
  
      /**
       * 删除书籍
       */
      @DeleteMapping("/{id}")
      public Result deleteBookById(@PathVariable Integer id) {
          bookService.removeById(id);
          return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
      }
  
      /**
       * 修改书籍信息
       */
      @PutMapping
      public Result updateBook(@RequestBody Book book) {
          bookService.updateById(book);
          return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
      }
  
      /**
       * 查询书籍信息
       */
      @GetMapping("/{id}")
      public Result getById(@PathVariable int id) {
          Book book = bookService.getById(id);
          return new Result(HttpStatus.OK.value(), book);
      }
  
      /**
       * 查询所有书籍信息
       */
      @GetMapping
      public Result getAll() {
          List<Book> books = bookService.list();
          return new Result(HttpStatus.OK.value(), books);
      }
  
      /**
       * 分页查询书籍信息
       */
      @GetMapping("/{currPage}/{pageSize}")
      public Result getByPage(@PathVariable int currPage, @PathVariable int pageSize) {
          Page<Book> page = bookService.getByPage(currPage, pageSize);
          return new Result(HttpStatus.OK.value(), page);
      }
  
      /**
       * 根据条件查询书籍信息
       * 传入形式?type=xx&name=xxx&description=xx
       */
      @GetMapping("/query")
      public Result getByCondition(@RequestParam(required = false) String type, @RequestParam(required = false) String name, @RequestParam(required = false) String description) {
          List<Book> books = bookService.getByCondition(type, name, description);
          return new Result(HttpStatus.OK.value(), books);
      }
  }
  ~~~~

  

* 统一异常处理

  ~~~~java
  package com.tudou.exception;
  
  import com.tudou.controller.model.Result;
  import com.tudou.utils.enumeration.StatusMsg;
  import org.springframework.http.HttpStatus;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  import org.springframework.web.bind.annotation.RestControllerAdvice;
  
  // 相当于 @ResponseBody + @ControllerAdvice
  @RestControllerAdvice
  public class MyExceptionAdvice {
      @ExceptionHandler({Exception.class})
      public Result handlerException(Exception ex) {
          // 输出错误信息
          ex.printStackTrace();
          // 向前端返回数据
          return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), StatusMsg.ERROR.toString());
      }
  }
  ~~~~

  

### 11 测试控制层

在postman进行测试，观察数据库表情况即可。

