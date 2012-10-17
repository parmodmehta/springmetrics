package com.springmetrics.annotation.resolver;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import com.springmetrics.annotation.Timed;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;

/**
 * @author pmehta
 *
 */
public class TimerMetricsResolver extends BaseMetricsResolver {

	private Timer timer = null;

	/**
	 * 
	 * @param timed
	 * @param m
	 */
	public TimerMetricsResolver(final Timed timed, final Method m) {
        Class<?> klass = m.getDeclaringClass();
        MetricName metricName = new MetricName(getGroup(timed.group(), klass),
        										getType(timed.type(), klass), 
        										getName(timed.name(), m));
        
		this.timer = Metrics.newTimer(metricName, 
										timed.durationUnit()== null ? TimeUnit.MILLISECONDS : timed.durationUnit(),
										timed.durationUnit() == null ? TimeUnit.SECONDS : timed.rateUnit());
	}
	
	public TimerContext getTimerContext() {
		return this.timer.time();
	}
	
	public void stop(final TimerContext timerContext) {
		timerContext.stop();
	}
}
