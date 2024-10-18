package nws.mc.cores.amlib.config.general;

public class GeneralConfigData {
    private boolean mixinAttributes;
    private boolean showTipGui;

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
