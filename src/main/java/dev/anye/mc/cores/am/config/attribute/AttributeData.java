package dev.anye.mc.cores.am.config.attribute;

public class AttributeData {
	private double min;
	private double def;
	private double max;
	public AttributeData(){
		setDef(1);
		setMax(2);
		setMin(0);
	}
	public AttributeData(double min,double max,double def){
		setDef(def);
		setMax(max);
		setMin(min);
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getDef() {
		return def;
	}

	public void setDef(double def) {
		this.def = def;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
}
