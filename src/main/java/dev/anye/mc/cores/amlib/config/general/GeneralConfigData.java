package dev.anye.mc.cores.amlib.config.general;

public class GeneralConfigData {
	public static final GeneralConfigData DEFAULT = new GeneralConfigData(true,false);
    private boolean mixinAttributes;
    private boolean showTipGui;

	public GeneralConfigData(boolean mixinAttributes,boolean showTipGui){
		this.mixinAttributes = mixinAttributes;
		this.showTipGui = showTipGui;
	}

    public void setShowTipGui(boolean showTipGui) {
        this.showTipGui = showTipGui;
    }

    public boolean isShowTipGui() {
        return showTipGui;
    }

    public void setMixinAttributes(boolean mixinAttributes) {
        this.mixinAttributes = mixinAttributes;
    }

    public boolean isMixinAttributes() {
        return mixinAttributes;
    }
}
