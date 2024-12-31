package cn.springhub.spring.util;

import cn.springhub.base.exception.SystemException;
import cn.springhub.base.util.DateUtils;
import cn.springhub.base.util.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *  表达式占位符解析
 * @author 艾虎
 * @date 2020/10/16 11:11
 */
public class ExpressionUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final Map<String, Expression> EXPRESSION_MAP = new HashMap<>();

    /**
     *  解析文本表达式，将占位符替换成对应的数据
     *  表达式为： #{#user}
     * @param exp           待解析的字符串
     * @param listener      上下文构建条件参数
     * @return  解析后字符串
     */
    public static String parse(String exp, Consumer<EvaluationContext> listener) {
        return parse(exp, String.class, true, listener);
    }

    /**
     *  计算表达式结果
     * @param exp       待解析的表达式
     * @param clazz     解析成功后的对象
     * @param listener  上下文构建条件参数
     * @return 解析后字符串
     */
    public static <T> T calculate(String exp, Class<T> clazz, Consumer<EvaluationContext> listener) {
        return parse(exp, clazz, false, listener);
    }

    /**
     *  解析表达式，生成结果
     * @param exp 待解析的表达式
     * @param clazz 解析成功后的对象
     * @param isTemplate  是否是模板解析
     * @param listener 构建上下文对象
     */
    private static <T> T parse(String exp, Class<T> clazz, boolean isTemplate, Consumer<EvaluationContext> listener) {
        if(StringUtils.isEmpty(exp)) {
            return null;
        }

        // 生成解析对象
        Expression expression = getExpression(exp, isTemplate);

        // 构建上下文环境
        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.registerFunction("format", DateUtils.class.getDeclaredMethod("covert", new Class[]{Date.class, String.class}));
            listener.accept(context);

            return expression.getValue(context, clazz);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 获取表达式对象
     * @param exp   待解析的表达式
     * @param isTemplate    是否是文本模板
     * @return  表达式对象
     */
    private static Expression getExpression(String exp, boolean isTemplate) {
        if (EXPRESSION_MAP.containsKey(exp)) {
            return EXPRESSION_MAP.get(exp);
        }

        Expression expression = isTemplate ? parser.parseExpression(exp, new TemplateParserContext()) : parser.parseExpression(exp);
        EXPRESSION_MAP.put(exp, expression);

        return expression;
    }

    public static void main(String[] args) {
        String key = "key";
        String key1 = "#number + 10 + '--' + #test";

//        System.out.println(ExpressionUtils.calculate(key1, String.class, (context) -> {
//            context.setVariable("age", "年龄");
//            context.setVariable("number", 20);
//            context.setVariable("args", new Integer[]{1, null});
//        }));
//
//        System.out.println(ExpressionUtils.calculate(key, String.class, (context) -> {
//            context.setVariable("age", "年龄");
//            context.setVariable("number", 20);
//            context.setVariable("args", new Integer[]{1, null});
//        }));
ExpressionUtils expressionUtils = new ExpressionUtils();
        System.out.println(ExpressionUtils.calculate("#root1.name", String.class, context -> {
            context.setVariable("root1", expressionUtils.getClass());
            context.setVariable("params", args);
        }));
    }
}
