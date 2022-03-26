package org.yoovlo.shr.java.req;
import org.yoovlo.shr.java.Ser;
public class Res {
	public Object b;
	@Override 
	public String toString() {
		return Ser.sSer(this);
	}
}
