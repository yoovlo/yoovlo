package org.yoovlo.shr.java.events;
import org.yoovlo.shr.java.events.Con_h;
import org.apache.kafka.common.errors.WakeupException;
import org.yoovlo.shr.java.T;

import java.util.concurrent.atomic.AtomicBoolean;

public class Con_runner implements Runnable {
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final Con_h con_h;

	public Con_runner(Con_h lcon_h) {
		con_h=lcon_h;
		T.t("Con_runner","events_con");
	}

	public void run() {
		T.t("Con_runner.run");
		try {
			con_h.run();
		} catch (WakeupException e) {
			if(!closed.get()) throw e;
		} finally {
			con_h.close();
		}
	}
	public void shutdown() {
		T.t("shutdown","events_con");
		closed.set(true);
		con_h.wakeup();
	}
 }