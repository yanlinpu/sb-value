package net.yanlp.test;

import lombok.extern.slf4j.Slf4j;
import net.yanlp.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@Slf4j
@SpringBootTest
@ContextConfiguration(classes = Application.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tmp")
public class ValueTest {
    @Value("${conf.stringNull:#{null}}")
    private String stringNull;

    @Value("${conf.stringTest:#{null}}")
    private String stringTest;

    @Value("${conf.string-test2:#{null}}")
    private String stringTest2;

    @Value("${conf.stringTest2:#{null}}")
    private String stringTest22;

    @Value("${conf.booleanTest:false}")
    private boolean booleanTest;

    @Value("${conf.listTest:#{null}}")
    private List<String> listTest;

    @Value("${conf.listTest2:#{null}}")
    private List<String> listTest2;

    @Value("${conf.user-yanlp-oneline-map:#{null}}")
    private Map<String, String> yanlpMap;

    @Value("#{${conf.user-yanlp-oneline}}")
    private Map<String, String> yanlpMap2;

    @Value("#{${conf.list-user-map}}")
    private Map<String, List<String>> userList;

    @Value("#{new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(\"${conf.dateTest:{2020-12-12}}\")}")
    private Date dateTest;

    /**
     * https://zhuanlan.zhihu.com/p/174786047
     * SpEL (Spring Expression Language)
     */
    @Test
    public void SPELTest() {
        // 创建解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析表达式对象
        Expression expression = parser.parseExpression("('hello' + ' world').concat(#end)");
        // 构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("map", map);

        // getValue 取值
        log.info("\ngetValue: {}" +
                        "\ngetValue of String: {}" +
                        "\ngetValue 1+1 of Integer: {}" +
                        "\ngetValue 1 between {1,2} of boolean: {}" +
                        "\ngetValue T(String) of class: {}" +
                        "\ngetValue 实例化 of Date: {}" +
                        "\ngetValue 1,2,3 of List: {}" +
                        "\ngetValue 1,2,3,1 of Set: {}" +
                        "\ngetValue map['a'] of Integer: {}"
                ,
                expression.getValue(context),
                parser.parseExpression("'Hello World!'").getValue(String.class),
                parser.parseExpression("1+1").getValue(Integer.class),
                parser.parseExpression("1 between {1,2}").getValue(boolean.class),
                parser.parseExpression("T(String)").getValue(Class.class),
                parser.parseExpression("new java.util.Date()").getValue(Date.class),
                parser.parseExpression("{1,2,3}").getValue(List.class),
                parser.parseExpression("{1,2,3,1}").getValue(Set.class),
                parser.parseExpression("#map['a']").getValue(context1, Integer.class)
        );
    }

    @Test
    public void valueTest() {
        log.info("\n@Value ${ } 使用占位符" +
                        "\n@Value #{ } SpEL 表达式取值" +
                        "\nstringNull: {}" +
                        "\nstringTest: {}" +
                        "\nstring-test2: {}" +
                        "\nbooleanTest: {}" +
                        "\nlistTest: {}" +
                        "\n@Value 不支持 yml 换行 中划线 listTest2: {}" +
                        "\n@Value dateTest: {}" +
                        "\n@Value 不支持松散语法 @ConfigurationProperties支持 使用默认值null stringTest2: {}" +
                        "\n@Value 不支持复杂类型 @ConfigurationProperties支持 使用默认值null yanlpMap: {}" +
                        "\n@Value 通过 SpEL 支持map map格式必须在一行 并且用\"\"把map所对应的value包起来 eg. a: \"{a: \"one\", b: \"two\"}\" yanlpMap: {}, name: {}" +
                        "\n@Value 通过 SpEL 支持map list user: {}",
                stringNull, stringTest, stringTest2, booleanTest, listTest, listTest2, dateTest, stringTest22, yanlpMap,
                yanlpMap2, yanlpMap2.getOrDefault("name", ""),
                userList
        );
    }
}
