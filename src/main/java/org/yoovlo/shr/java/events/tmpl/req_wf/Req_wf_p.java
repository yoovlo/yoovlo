package org.yoovlo.shr.java.events.tmpl.req_wf;
import org.yoovlo.shr.java.events.tmpl.Wf_pack;
//req workflow package
public class Req_wf_p extends Wf_pack<Wf,Wf_i,Actvs_i> {
	public static final String Task_list="req";
	public Req_wf_p() {
		super(
			"req",
			Req_wf_p.Task_list,
			Wf.class,
			Wf_i.class,
			Actvs_i.class,
			new Actvs()
		);
		run_to=180000;
		exec_to=180000;
		actvs_start_to_close_to=180000;
	}
}