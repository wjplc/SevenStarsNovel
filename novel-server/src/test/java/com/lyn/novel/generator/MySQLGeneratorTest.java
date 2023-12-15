package com.lyn.novel.generator;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * MySQL 代码生成
 *
 * @author lanjerry
 * @since 3.5.3
 */
public class MySQLGeneratorTest{

    private static final String USERNAME = "wjp";
    /**
     * 项目信息
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String JAVA_PATH = "/src/main/java";
    private static final String RESOURCE_PATH = "/src/main/resources";
    private static final String BASE_PACKAGE = "com.wjp.novel";

    /**
     * 数据库信息
     */
    private static final String DATABASE_IP = "192.168.146.128";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_NAME = "novel";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "123456";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:mysql://127.0.0.1:3306/novel-lh?serverTimezone=Asia/Shanghai", "root", "123456")
        .build();

    /**
     * 策略配置
     */
    protected static StrategyConfig.Builder strategyConfig() {
        StrategyConfig.Builder strategy = new StrategyConfig.Builder();

        strategy
                .addInclude("book");

        strategy.entityBuilder()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation();

        return strategy;

    }
//"book","book_category","book_channel","book_chapter","book_content","book_score","comment","user"
  //  ,"book_category","book_channel","book_chapter","book_content","book_score","comment","user","user_auth","permission"
    /**
     * 全局配置
     */
    protected static GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder()
                .author("wjp")
                .fileOverride()
                .commentDate("yyyy/MM/dd")
                .outputDir(PROJECT_PATH + JAVA_PATH);
    }

    /**
     * 包配置
     */
    protected static PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder()
                .parent(BASE_PACKAGE)
                .entity("dao.entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("dao.mapper")
                .controller("controller.front")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_PATH + RESOURCE_PATH + "/src/main/resources/mapper"));
    }

    /**
     * 模板配置
     */
    protected static TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder()
                .disable(TemplateType.SERVICE)
                .disable(TemplateType.SERVICEIMPL)
                .disable(TemplateType.CONTROLLER);
//                .disable(TemplateType.XML)
//                .disable(TemplateType.MAPPER);

    }

    /**
     * 注入配置
     */
    protected static InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.global(globalConfig().build());
        generator.packageInfo(packageConfig().build());
        generator.execute();
    }
}