package com.adc.sqlitedemo.config.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * 配置全局使用FastJson，不使用默认的jackson
 *
 * @author risfeng
 * @date 2019/11/02
 */
@Configuration
public class JsonConfig {

  /**
   * 时区
   */
  @Value("${fastjson.time-zone:GMT+8}")
  private String timeZone;

  /**
   * 时间格式
   */
  @Value("${fastjson.date-format:yyyy-MM-dd HH:mm:ss}")
  private String dateFormat;

  /**
   * 字符编码
   */
  @Value("${spring.http.encoding.charset:UTF-8}")
  private String charset;

  /**
   * 必须在pom.xml引入FastJson的jar包，并且版必须大于1.2.10
   *
   * @return HttpMessageConverters
   */
  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig jsonConfig = new FastJsonConfig();
    //设置时区
    JSON.defaultTimeZone = TimeZone.getTimeZone(timeZone);
    //日期格式
    jsonConfig.setDateFormat(dateFormat);
    //特性设置
    jsonConfig.setFeatures(
        Feature.OrderedField,
        Feature.IgnoreNotMatch
    );
    jsonConfig.setSerializerFeatures(
        SerializerFeature.PrettyFormat,
        SerializerFeature.WriteBigDecimalAsPlain,
        SerializerFeature.WriteDateUseDateFormat,
        SerializerFeature.WriteEnumUsingToString
    );
    //字符编码
    jsonConfig.setCharset(Charset.forName(charset));
    //序列化设置
    SerializeConfig serializeConfig = SerializeConfig.globalInstance;
    serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
    jsonConfig.setSerializeConfig(serializeConfig);
    //反序列化设置
    ParserConfig parserConfig = ParserConfig.getGlobalInstance();
    parserConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
    //"autoType is not support"问题,使用setAutoTypeSupport=true的全局设置
    parserConfig.setAutoTypeSupport(true);
    jsonConfig.setParserConfig(parserConfig);

    //处理序列化支持类型
    ArrayList<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON);
    fastMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
    fastMediaTypes.add(MediaType.TEXT_HTML);
    fastMediaTypes.add(MediaType.TEXT_PLAIN);
    fastConverter.setSupportedMediaTypes(fastMediaTypes);
    fastConverter.setFastJsonConfig(jsonConfig);
    return new HttpMessageConverters(fastConverter);
  }
}
