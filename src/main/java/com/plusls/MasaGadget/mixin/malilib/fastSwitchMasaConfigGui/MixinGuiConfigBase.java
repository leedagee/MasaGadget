package com.plusls.MasaGadget.mixin.malilib.fastSwitchMasaConfigGui;

import com.plusls.MasaGadget.ModInfo;
import com.plusls.MasaGadget.config.Configs;
import com.plusls.MasaGadget.gui.MyWidgetDropDownList;
import com.plusls.MasaGadget.malilib.fastSwitchMasaConfigGui.MasaGuiUtil;
import com.plusls.MasaGadget.mixin.Dependencies;
import com.plusls.MasaGadget.mixin.Dependency;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Dependencies(dependencyList = @Dependency(modId = ModInfo.MODMENU_MOD_ID, version = "*"))
@Mixin(value = GuiConfigsBase.class, remap = false)
public abstract class MixinGuiConfigBase extends GuiListBase<GuiConfigsBase.ConfigOptionWrapper, WidgetConfigOption, WidgetListConfigOptions> implements IKeybindConfigGui {

    @Shadow
    protected Screen parentScreen;
    private final WidgetDropDownList<ConfigScreenFactory<?>> masaModGuiList = new MyWidgetDropDownList<>(GuiUtils.getScaledWindowWidth() - 155, 13, 130, 18, 200, 10,
            MasaGuiUtil.getMasaGuiData().keySet().stream().toList(), MasaGuiUtil.getMasaGuiData()::get,
            configScreenFactory -> GuiBase.openGui(configScreenFactory.create(this.parentScreen)),
            configScreenFactory -> Configs.Malilib.FAST_SWITCH_MASA_CONFIG_GUI.getBooleanValue());

    protected MixinGuiConfigBase(int listX, int listY) {
        super(listX, listY);
    }

    @Inject(method = "initGui", at = @At(value = "RETURN"))
    public void postInitGui(CallbackInfo ci) {
        masaModGuiList.setSelectedEntry(MasaGuiUtil.getMasaGuiClassData().get(this.getClass()));
        this.addWidget(masaModGuiList);
    }

    @Override
    public void resize(MinecraftClient mc, int width, int height) {
        super.resize(mc, width, height);
        masaModGuiList.setX(GuiUtils.getScaledWindowWidth() - 155);
    }

}
