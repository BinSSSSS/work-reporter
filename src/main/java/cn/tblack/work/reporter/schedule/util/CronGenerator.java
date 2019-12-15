package cn.tblack.work.reporter.schedule.util;

import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;

/**
 * @用于生成Cron表达式的生成器
 * @author TD唐登
 * @Date:2019年10月30日
 * @Version: 1.0(测试版)
 */
public class CronGenerator {

	public static final CronDefinition DEFINITION = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);

	/**
	 * @根据下一次需要执行的时间生成一个单次执行的Cron表达式
	 * @param date
	 * @return
	 */
	public static Cron singleScheduleCron(Calendar calendar) {

		Cron cron = CronBuilder.cron(DEFINITION)
				.withYear(on(calendar.get(YEAR)))
				.withDoM(on(calendar.get(DAY_OF_MONTH)))
				.withMonth(on(calendar.get(MONTH) + 1))
				.withDoW(questionMark())
				.withHour(on(calendar.get(HOUR)))
				.withMinute(on(calendar.get(MINUTE)))
				.withSecond(on(0))
				.instance();

		return cron;
	}
	
	public static void main(String[] args) {
		
		
		System.err.println(singleScheduleCron(new GregorianCalendar()).asString());
	}
}
