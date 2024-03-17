package com.tudou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tudou.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * 使用mybatis-plus写好的mapper层（包括Mapper接口内容、xxxMapper.xml文件都已经内置配置好了）
 * 需要进行如下配置
 * 1. XxxMapper接口需要添加@Mapper注解
 * 2. XxxMapper接口需要继承BaseMapper<Entity.class>类
 * 3. 如果要使用分页，需要配置对应的分页拦截器
 * 形如：@Mapper
 *      public interface BookMapper extends BaseMapper<Book>
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
