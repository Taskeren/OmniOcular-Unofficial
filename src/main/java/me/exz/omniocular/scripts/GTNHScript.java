package me.exz.omniocular.scripts;

import static me.exz.omniocular.scripts.Utility.ALIGNRIGHT;
import static me.exz.omniocular.scripts.Utility.AQUA;
import static me.exz.omniocular.scripts.Utility.BLUE;
import static me.exz.omniocular.scripts.Utility.BOLD;
import static me.exz.omniocular.scripts.Utility.DAQUA;
import static me.exz.omniocular.scripts.Utility.DBLUE;
import static me.exz.omniocular.scripts.Utility.DPURPLE;
import static me.exz.omniocular.scripts.Utility.DRED;
import static me.exz.omniocular.scripts.Utility.GOLD;
import static me.exz.omniocular.scripts.Utility.GREEN;
import static me.exz.omniocular.scripts.Utility.RED;
import static me.exz.omniocular.scripts.Utility.TAB;
import static me.exz.omniocular.scripts.Utility.WHITE;
import static me.exz.omniocular.scripts.Utility.YELLOW;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import me.exz.omniocular.IScript;

public class GTNHScript implements IScript {

    @Override
    public Type registerType() {
        return Type.TileEntity;
    }

    @Override
    public Pattern registerIdPattern() {
        return Pattern.compile("BaseMetaTileEntity");
    }

    private static void patternLine(List<String> lines, String displayName, String line) {
        lines.add(displayName + TAB + ALIGNRIGHT + line);
    }

    private static String works_0 = DRED + "倪"
        + RED
        + "哥"
        + YELLOW
        + "正"
        + GREEN
        + "在"
        + AQUA
        + "超"
        + DAQUA
        + "辛"
        + BLUE
        + "勤"
        + DBLUE
        + "工"
        + DPURPLE
        + '作';
    private static String works_1 = DRED + "农"
        + RED
        + "具"
        + YELLOW
        + "已"
        + GREEN
        + "经"
        + AQUA
        + "被"
        + DAQUA
        + "正"
        + BLUE
        + "确"
        + DBLUE
        + "安"
        + DPURPLE
        + '放';

    @Override
    public List<String> getLines(NBTTagCompound nbt, String id, EntityPlayer player) {
        List<String> lines = new LinkedList<>();

        if (nbt.hasKey("eMultiplier")) { // 尽量判断键的有无，NBT的get方法会返回默认值
            // <line displayname="§b并行数">
            // if(nbt['eMultiplier']>=64){return colorcir('§'+nbt['eMultiplier']+'§',3,1)}
            // if(nbt['eMultiplier']<64 && nbt['eMultiplier']>16){return colorcir('§'+nbt['eMultiplier']+'§',3)}
            // if(nbt['eMultiplier']<=16){return colorcir(nbt['eMultiplier'],2)}
            // </line>
        }
        if (nbt.hasKey("mWorks")) {
            patternLine(lines, "§b机器加工", nbt.getBoolean("mWorks") ? works_1 : works_0);
        }
        if (nbt.hasKey("mFluidMode")) {
            patternLine(lines, "§b流体模式", nbt.getBoolean("mFluidMode") ? GOLD + "输出金液" : WHITE + "输出面粉");
        }
        if (nbt.hasKey("inputSeparation")) {
            patternLine(lines, "§b总线隔离", nbt.getBoolean("inputSeparation") ? GREEN + "别想着多仓室输入了" : RED + "大哥阵列在看着你");
        }
        if (nbt.hasKey("mSpecialTier")) {
            if (nbt.getInteger("mSpecialTier") > 0) patternLine(lines, "§b锻炉等级", GOLD + nbt.getInteger("mSpecialTier"));
        }
        if (nbt.hasKey("batchMode")) {
            patternLine(lines, "§b批量处理", nbt.getBoolean("batchMode") ? GREEN + "看看批" : RED + "不给看批");
        }
        if (nbt.hasKey("mLockedToSingleRecipe")) {
            if (nbt.getBoolean("mLockedToSingleRecipe")) patternLine(lines, "§b配方锁定", GREEN + "纯爱战士，启动！");
        }
        if (nbt.hasKey("wirelessMode")) {
            patternLine(
                lines,
                "§b无线模式",
                nbt.getBoolean("wirelessMode") ? GREEN + "蓝波顿开无线，你就等着看烟花罢" : GREEN + "杂鱼~杂鱼~无线都开不起的杂鱼~");
        }
        if (nbt.hasKey("eID")) {
            if (nbt.getInteger("eID") >= 0 && nbt.getInteger("eID") <= 15441)
                patternLine(lines, "§接口ID", BOLD + nbt.getInteger("eID"));
        }
        if (nbt.hasKey("eMultiplier")) {
            if (nbt.getBoolean("mMuffler")) patternLine(lines, "§b静音升级", GREEN + "憋叭叭了");
        }
        if (nbt.hasKey("mAnimationEnabled")) {
            patternLine(lines, "§b动画", nbt.getBoolean("mAnimationEnabled") ? GREEN + "好看的动画" : RED + "节省TPS模式");
        }
        if (nbt.hasKey("mIsAnimated")) {
            patternLine(lines, "§b动画", nbt.getBoolean("mIsAnimated") ? GREEN + "好看的动画" : RED + "节省TPS模式");
        }
        if (nbt.hasKey("isInRitualMode")) {
            patternLine(lines, "§b苦难之井", nbt.getBoolean("isInRitualMode") ? GREEN + "无穷无尽的LP" : RED + "无穷无尽的掉落物");
        }
        if (nbt.hasKey("isInRitualMode")) {
            patternLine(
                lines,
                "§b生成精英怪",
                nbt.getBoolean("isInRitualMode") ? GREEN + "勇猛的榨精者会用装备填满你的AE" : RED + "爷不想要奇怪的装备");
        }

        // <!--超净室 -->
        if (nbt.hasKey("mID")) {
            // <line displayname="§b洁净度">
            // if(nbt['mID']==1172){return YELLOW+(nbt['mEfficiency'] / 100).toFixed(0)+AQUA+" %"}
            // </line>
        }
        // <line displayname="§b当前模式 ">
        // if(nbt['doVoidExcess']==1){return YELLOW+'⚙溢出销毁⚙'}
        // if(nbt['mFormingMode']==0){return YELLOW+'⚙卷板机⚙'}
        // if(nbt['mFormingMode']==1){return YELLOW+'⚙冲压机床⚙'}
        // if(nbt['mMode']==0 && nbt['mID']==850){return YELLOW+'⚙洗矿机⚙'}
        // if(nbt['mMode']==1 && nbt['mID']==850){return YELLOW+'⚙简易洗矿池⚙'}
        // if(nbt['mMode']==2 && nbt['mID']==850){return YELLOW+'⚙化学浸洗机⚙'}
        // if(nbt['mInternalMode']==0 && nbt['mID']==860){return YELLOW+'⚙模式[A]⚙'}
        // if(nbt['mInternalMode']==1 && nbt['mID']==860){return YELLOW+'⚙模式[B]⚙'}
        // if(nbt['mInternalMode']==2 && nbt['mID']==860){return YELLOW+'⚙模式[C]⚙'}
        // if(nbt['mPlasmaMode']==0 && nbt['mID']==862){return YELLOW+'⚙电弧炉⚙'}
        // if(nbt['mPlasmaMode']==1 && nbt['mID']==862){return YELLOW+'⚙等离子电弧炉⚙'}
        // if(nbt['mCuttingMode']==1 && nbt['mID']==992){return YELLOW+'⚙切割机⚙'}
        // if(nbt['mCuttingMode']==0 && nbt['mID']==992){return YELLOW+'⚙切片机⚙'}
        // if(nbt['mDehydratorMode']==0 && nbt['mID']==995){return YELLOW+'⚙真空炉⚙'}
        // if(nbt['mDehydratorMode']==1 && nbt['mID']==995){return YELLOW+'⚙脱水机⚙'}
        // if(nbt['turbineFitting']==0 &&
        // (nbt['mID']==31074||nbt['mID']==865||nbt['mID']==866||nbt['mID']==1152)){return YELLOW+'⚙紧⚙'}
        // if(nbt['turbineFitting']==1 &&
        // (nbt['mID']==31074||nbt['mID']==865||nbt['mID']==866||nbt['mID']==1152)){return YELLOW+'⚙松⚙'}
        // </line>
        //
        // <line displayname="§b当前模式2 ">
        // if(nbt['mFastMode']==0 && (nbt['mID']==31074||nbt['mID']==865||nbt['mID']==866)){return YELLOW+'⚙低速16X⚙'}
        // if(nbt['mFastMode']==1 && (nbt['mID']==31074||nbt['mID']==865||nbt['mID']==866)){return YELLOW+'⚙高速48X⚙'}
        // </line>
        if (nbt.hasKey("chunkLoadingEnabled")) {
            patternLine(lines, "§b区块加载", nbt.getBoolean("chunkLoadingEnabled") ? GREEN + "开启" : RED + "关闭");
        }
        if (nbt.hasKey("replaceWithCobblestone")) {
            patternLine(lines, "§b替换圆石", nbt.getBoolean("replaceWithCobblestone") ? GREEN + "开启" : RED + "关闭");
        }
        // <line displayname="§b区块半径 ">
        // if(nbt['chunkRadiusConfig']>=0){return BOLD+(nbt['chunkRadiusConfig']*16)}
        // </line>
        //
        // <line displayname="§b喷涂颜色 ">
        // var hatchcolor = ['黑色','红色','绿色','棕色','蓝色','紫色','青色','淡灰','灰色','粉色','黄绿','黄色','淡蓝','品红','橙色','白色'];
        // if(nbt['mColor']>0){return YELLOW+hatchcolor[nbt['mColor']-1]}
        // </line>
        if (nbt.hasKey("autoStock")) {
            patternLine(lines, "§6ME-自动拉取", nbt.getBoolean("autoStock") ? GREEN + "获得AE的全部力量" : RED + "手动标记所需的物品");
        }
        if (nbt.hasKey("autoPull")) {
            patternLine(lines, "§6ME-自动拉取", nbt.getBoolean("autoPull") ? GREEN + "获得AE的全部力量" : RED + "手动标记所需的物品");
        }
        if (nbt.hasKey("additionalConnection")) {
            patternLine(
                lines,
                "§6ME-其余面连接",
                nbt.getBoolean("additionalConnection") ? GREEN + "可以从别的面连接" : RED + "不能从别的面连接");
        }

        // <line displayname="§6数据存储-物品">
        // if(nbt['mID']==2710&&nbt['cachedItems']!=undefined)
        // {
        // outputString = "\\n";
        // for (i=0; nbt['cachedItems'][i] != undefined; i++)
        // {
        // outputString += YELLOW
        // +standards(nbt['cachedItems'][i]['size'])+"个"+GREEN+name(nbt['cachedItems'][i]['itemStack'])+ALIGNRIGHT+"\\n"
        // ;
        // }
        // return outputString;
        // }
        // </line>
        // <line displayname="§6数据存储-流体">
        // if(nbt['mID']==2713&&nbt['cachedFluids']!=undefined)
        // { outputString = "\\n";
        // for(i=0; nbt['cachedFluids'][i]!=undefined; i++)
        // {
        // outputString += AQUA+standards(nbt['cachedFluids'][i]['size'])+YELLOW+"mB "+GREEN+
        // fluidName(nbt['cachedFluids'][i]['fluidStack']['FluidName'])+ALIGNRIGHT+ "\\n" ;
        // };
        // return outputString;
        // };
        // </line>
        if (nbt.hasKey("eyeOfHarmonyOutputanimationsEnabled")) {
            patternLine(
                lines,
                "§6动画",
                nbt.getBoolean("eyeOfHarmonyOutputanimationsEnabled") ? GREEN + "拯救TPS" : RED + "不能从别的面连接");
        }

        // <line displayname="§6概率">
        // if (nbt['eyeOfHarmonyOutputrecipeSuccessChance']!=undefined && nbt['mActive']==1){return
        // YELLOW+nbt['eyeOfHarmonyOutputrecipeSuccessChance'].toFixed(2)*100+YELLOW+"%"}
        // </line>
        //
        // <line displayname="§6超频">
        // if (nbt['eyeOfHarmonyOutputcurrentCircuitMultiplier']>=0){return LPURPLE+"超
        // "+YELLOW+nbt['eyeOfHarmonyOutputcurrentCircuitMultiplier']+LPURPLE+" 频"}
        // </line>
        //
        // <line displayname="§6氢气">
        // if (nbt['stored.fluid.hydrogen']>=0 && nbt['mActive']==0){return
        // YELLOW+standards(nbt['stored.fluid.hydrogen'])+LPURPLE+" L"}
        // </line>
        //
        // <line displayname="§6氦气">
        // if (nbt['stored.fluid.helium']>=0 && nbt['mActive']==0){return
        // YELLOW+standards(nbt['stored.fluid.helium'])+LPURPLE+" L"}
        // </line>
        // <line displayname="§6运行配方">
        // if (nbt['mWorks']>=0 && nbt['mID']==15410){return YELLOW+name(nbt['Inventory']['0'])}
        // </line>
        //
        // <line displayname="§6缓存数">
        // if (nbt['moduleCount']>=0){return YELLOW+standards(nbt['moduleCount'])+GOLD+" 个"}
        // </line>
        //
        // <line displayname="§6总输出">
        // if (nbt['euPerTick']>=0 && nbt['mID']==14001){return YELLOW+formatBytes(nbt['euPerTick'])+GOLD+" EU/t"}
        // </line>
        //
        // <line displayname="§6输出电流">
        // if (nbt['euPerTick']>=0 && nbt['mID']==14001){return
        // BLUE+standards((nbt['euPerTick']/nbt['eMaxGenEUmax']).toFixed())+GOLD+" A"}
        // </line>
        //
        // <line displayname="§6输出电压">
        // if (nbt['eMaxGenEUmax']>=0 && nbt['mID']==14001){return BLUE+standards(nbt['eMaxGenEUmax'])+GOLD+" EU"}
        // </line>
        //
        // <line displayname="§dEU网络">
        // if (nbt['wireless_mode_cooldown']>=0 && nbt['wireless_mode']!=0){return
        // YELLOW+((nbt['wireless_mode_cooldown']/6000)*100).toFixed(1)+LPURPLE+" %"}
        // </line>
        //
        // <line displayname="§d平均污染">
        // if (nbt['mAveragePollution']>=0){return YELLOW+nbt['mAveragePollution']}
        // </line>
        // <line displayname="§d当前污染">
        // if (nbt['mCurrentPollution']>=0){return YELLOW+nbt['mCurrentPollution']}
        // </line>
        // <line displayname="§d传送机">
        // if (nbt['mID']==1145){return GOLD+'红石启动'}
        // </line>
        // <line displayname="§d传送坐标">
        // if (nbt['mID']==1145){return GOLD+'X:'+nbt['mTargetX']}
        // </line>
        // <line displayname="">
        // if (nbt['mID']==1145){return GOLD+'Y:'+nbt['mTargetY']}
        // </line>
        // <line displayname="">
        // if (nbt['mID']==1145){return GOLD+'Z:'+nbt['mTargetZ']}
        // </line>
        // <line displayname="§d目标维度ID">
        // if (nbt['mID']==1145){return GOLD+nbt['mTargetD']}
        // </line>
        // <line displayname="§d频率">
        // if (nbt['mID']==834||nbt['mID']==833){return GOLD+nbt['mFrequency']}
        // </line>
        //
        // <!--大涡轮机器：功率 -->
        // <line displayname="§d功率">
        // if (nbt['mID']==31074 || nbt['mID']==865||nbt['mID']==866){return
        // GOLD+'('+GetVtier(Math.abs(nbt['mEUt'])*nbt['mEfficiency']/10000)+GOLD+')'}
        // </line>
        // <line displayname="§d涡轮功率">
        // if (nbt['mID']==31074 || nbt['mID']==865||nbt['mID']==866){return
        // YELLOW+standards((Math.abs(nbt['mEUt'])*nbt['mEfficiency']/10000).toFixed(0))+GOLD+'EU/t'}
        // </line>
        //
        // <!--锅炉：温度 -->
        // <line displayname="§e温度">
        // if([100,105,114].indexOf(nbt['mID'])>-1){return nbt['mTemperature']+" / 500"+WHITE+" C"}
        // if([101,102].indexOf(nbt['mID'])>-1){return nbt['mTemperature']+" / 1000"+WHITE+" C"}
        // if(nbt['mID']==753){return nbt['mTemperature']+" / 1250"+WHITE+" C"}
        // if(nbt['mID']==754){return nbt['mTemperature']+" / 1500"+WHITE+" C"}
        // if(nbt['mID']==755){return nbt['mTemperature']+" / 1750"+WHITE+" C"}
        // </line>
        //
        // <line displayname="§dDebug输出电流">
        // if (nbt['eAMP']>=0){return YELLOW+standards(nbt['eAMP'])+GOLD+" A"}
        // </line>
        //
        // <line displayname="§dDebug输出电压">
        // if (nbt['eEUT']>=0){return YELLOW+standards(nbt['eEUT'])+GOLD+" EU"}
        // </line>
        // <line displayname="§d加速范围">
        // if (nbt['mAccelMode']==0 && nbt['mRadius']>=0){return YELLOW+nbt['mRadius']+AQUA+'方块'}
        // </line>
        // <line displayname="§d加速等级">
        // if (nbt['mAccelMode']>=0 && nbt['mSpeed']>=0){YELLOW+Math.pow(2,nbt['mSpeed'])+AQUA+'X'}
        // </line>
        //
        //
        // <line displayname="§6炖屎详情">
        // if (nbt['mActive']==0 && (nbt['mID']==31150 || nbt['mID']==810)){return DGRAY+"ω摸鱼中ω"}
        // if (nbt['mActive']==1 && (nbt['mID']==31150 || nbt['mID']==810)){return GOLD+"ω炖屎中ω"}
        // </line>
        //
        // <line displayname="">
        // if (nbt['mActive']==0 && (nbt['mID']==31150 || nbt['mID']==810)){return
        // ALIGNCENTER+GOLD+"ψ摸鱼----§m§o倪哥快乐§6ψ"}
        // if (nbt['mActive']==1 && (nbt['mID']==31150 || nbt['mID']==810)){return ALIGNCENTER+GOLD+"炖屎----永无止境"}
        // </line>
        //
        // <!--Nxer's机器 -->
        // <line displayname="§b§k6§r§b恒星-行星系数§k6">
        // if (nbt['stellarAndPlanetCoefficient']>=0 && (nbt['mID']==19014)){return
        // GOLD+nbt['stellarAndPlanetCoefficient']}
        // </line>
        // <line displayname="§b§k6§r§b轨道等级§k6">
        // if (nbt['motorTier']>=0 && (nbt['mID']==19013)){return GOLD+nbt['motorTier']}
        // </line>
        // <line displayname="§b§k6§r§b过载时间§k6">
        // if (nbt['overloadTime']>=0 && (nbt['mID']==19013)){return GOLD+standards(nbt['overloadTime'])}
        // </line>
        // <line displayname="§b§k6§r§b引力透镜时间§k6">
        // if (nbt['gravitationalLensTime']>=0 && (nbt['mID']==19014)){return
        // GOLD+standards(nbt['gravitationalLensTime'])}
        // </line>
        // <line displayname="§b§k6§r§b顶点环数§k6">
        // if (nbt['speedTotal']>=0 && (nbt['mID']==19003)){return GOLD+nbt['speedTotal']}
        // </line>
        // <line displayname="§b§k6§r§b环数§k6">
        // if (nbt['rings']>=0 && (nbt['mID']==19007)){return GOLD+nbt['rings']}
        // </line>
        // <line displayname="§b§k6§r§b线圈数§k6">
        // if (nbt['piece']>=0 && (nbt['mID']==19008)){return GOLD+nbt['piece']}
        // </line>
        // <line displayname="§b§k6§r§b层数§k6">
        // if (nbt['piece']>=0 && (nbt['mID']==19009)){return GOLD+nbt['piece']}
        // if (nbt['piece']>=0 && (nbt['mID']==19011)){return GOLD+nbt['piece']}
        // </line>
        // <line displayname="§b§k6§r§b发生器等级§k6">
        // var Gent=['遏制','终极遏制','粗制','原始','稳定','先进','卓越','异星','完美','太初','鸿蒙'];
        // if (nbt['fieldGeneratorTier']>=0 && (nbt['mID']==19010||nbt['mID']==19012)){return
        // GOLD+Gent[nbt['fieldGeneratorTier']-1]}
        // </line>
        // <line displayname="§b§k6§r§b并行数§k6">
        // if (nbt['rings']>=0 && (nbt['mID']==19007)){return GOLD+nbt['rings']*8}
        // if (nbt['piece']>=0 && (nbt['mID']==19008 || nbt['mID']==19009)){return GOLD+nbt['piece']*8}
        // if (nbt['piece']>=0 && (nbt['mID']==19011)){return GOLD+nbt['piece']*24}
        // </line>
        // <line displayname="§b§k6§r§b无损超频§k6">
        // if (nbt['enablePerfectOverclockSignal']==0 && (nbt['mID']==19002)){return AQUA+"不那么高贵的有损"}
        // if (nbt['speedTotal']<8 && (nbt['mID']==19003)){return AQUA+"不那么高贵的有损"}
        // if (nbt['piece']<16 && (nbt['mID']==19011)){return AQUA+"不那么高贵的有损"}
        // if (nbt['enablePerfectOverclockSignal']==1 && (nbt['mID']==19002)){return GOLD+"高贵的无损"}
        // if (nbt['speedTotal']>=8 && (nbt['mID']==19003)){return GOLD+"高贵的无损"}
        // if (nbt['piece']>=16 && (nbt['mID']==19011)){return GOLD+"高贵的无损"}
        // </line>
        // <line displayname="§b§k6§r§b机器模式§k6">
        // if (nbt['mode']==0 && (nbt['mID']==19001)){return YELLOW+"⚙深度化学扭曲仪⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19001)){return YELLOW+"⚙化学反应釜⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19002)){return YELLOW+"⚙激光蚀刻模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19002)){return YELLOW+"⚙光子掌控者⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19003)){return YELLOW+"⚙电路装配模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19003)){return YELLOW+"⚙量子逆变模式⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19004)){return YELLOW+"⚙压模机⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19004)){return YELLOW+"⚙卷板机⚙"}
        // if (nbt['mode']==2 && (nbt['mID']==19004)){return YELLOW+"⚙冲压机床⚙"}
        // if (nbt['mode']==3 && (nbt['mID']==19004)){return YELLOW+"⚙锻造锤⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19005)){return YELLOW+"⚙流体固化模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19005)){return YELLOW+"⚙流体提取模式⚙"}
        // if ((nbt['mID']==19006)){return YELLOW+"⚙搅拌机⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19007)){return YELLOW+"⚙电磁离析机⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19007)){return YELLOW+"⚙磁化机⚙"}
        // if ((nbt['mID']==19008)){return YELLOW+"⚙线材扎机⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19009)){return YELLOW+"⚙切割机模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19009)){return YELLOW+"⚙切片机模式⚙"}
        // if (nbt['mode']==2 && (nbt['mID']==19009)){return YELLOW+"⚙车床模式⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19010)){return YELLOW+"⚙压缩机模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19010)){return YELLOW+"⚙提取机模式⚙"}
        // if (nbt['mode']==2 && (nbt['mID']==19010)){return YELLOW+"⚙粒子对撞机模式⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19011)){return YELLOW+"⚙电解机模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19011)){return YELLOW+"⚙离心机模式⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19012)){return YELLOW+"⚙高压釜模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19012)){return YELLOW+"⚙晶胞铸造器⚙"}
        // if (nbt['mode']==0 && (nbt['mID']==19014)){return YELLOW+"⚙发电模式⚙"}
        // if (nbt['mode']==1 && (nbt['mID']==19014)){return YELLOW+"⚙浓缩光子模式⚙"}
        // </line>
        //
        // <!--Nxer's 人造恒星测试-->
        // <line displayname="§6§k6§b稳定力场§6§k6">
        // var Gent=['粗制','原始','稳定','先进','卓越','异星','完美','太初','鸿蒙'];
        // if (nbt['tierStabilisationField']>=2 && nbt['mID']==19015){return GOLD+Gent[nbt['tierStabilisationField']-2]}
        // </line>
        // <line displayname="§6§k6§b压缩时空场§6§k6">
        // var Gent=['粗制','原始','稳定','先进','卓越','异星','完美','太初','鸿蒙'];
        // if (nbt['tierDimensionField']>=2 && nbt['mID']==19015){return GOLD+Gent[nbt['tierDimensionField']-2]}
        // </line>
        // <line displayname="§6§k6§b时间膨胀场§6§k6">
        // var Gent=['粗制','原始','稳定','先进','卓越','异星','完美','太初','鸿蒙'];
        // if (nbt['tierTimeField']>=2 && nbt['mID']==19015){return GOLD+Gent[nbt['tierTimeField']-2]}
        // </line>
        // <line displayname="§6§k6§b发电的倍率§6§k6">
        // if (nbt['outputMultiplier']>=0 && nbt['mID']==19015){return
        // GOLD+GREEN+nbt['outputMultiplier'].toFixed(0)+GOLD}
        // </line>
        // <line displayname="§6§k6§b原料回收几率§6§k6">
        // if (nbt['rewardContinuous']>=0 && nbt['mID']==19015){return
        // GOLD+YELLOW+nbt['tierStabilisationField']*nbt['tierDimensionField']*nbt['tierTimeField']/10+AQUA+' %'}
        // </line>
        // <line displayname="§6§k6§b连续运行奖励§6§k6">
        // if (nbt['rewardContinuous']>=0 && nbt['mID']==19015){return YELLOW+(nbt['rewardContinuous']+100)+AQUA+' %'}
        // </line>
        // <line displayname="§6§k6§b人造恒星发电实际总额§6§k6">
        // var total = (nbt['currentOutputEU']*nbt['outputMultiplier']*(nbt['rewardContinuous']+100)/100).toFixed(0);
        // if (nbt['mActive']==1 && nbt['mID']==19015){return
        // YELLOW+standards(total/128)+colorcir('§MAX§',3,1)+'\\n'+AQUA+'==='+YELLOW+standards(total*16777216)+AQUA+'
        // EU/T'+AQUA+'==='}
        // </line>
        // <line displayname="§6§k6§b恒星锻炉无线EU功耗§6§k6">
        // if (nbt['mID']==19016){return GOLD+standards(nbt['costingWirelessEUTemp'])+' EU'}
        // </line>
        // <!--电力机器：功率 -->
        // <line displayname="§b外壳">
        // var
        // VTier=['§7LV','§6MV','§eHV','§8EV','§aIV','§dLuv','§bZPM','§2UV','§4UHV','§5UEV','§9§n§lUIV','§c§n§lUMV','§4§n§lUXV','§f§n§lMAX'];
        // if (nbt['casingTier']>=0 && nbt['mID']==32026){
        // return GOLD+VTier[nbt['casingTier']]
        // }
        // </line>
        //
        // <line displayname="§6催化剂折扣">
        // if (nbt['mID']==1004 && nbt['eLongDiscountValue']!=1){
        // return GOLD+((1-nbt['eLongDiscountValue'])*100).toFixed(2)+'%'
        // }
        // </line>
        //
        // <line displayname="§d功率">
        // if(nbt['mID']!=1154)
        // if(nbt['mEUt']!=undefined && nbt['mEUt']!=0 && nbt['mID']!=1020 && nbt['mID']!=1021 && nbt['mID']!=1022 &&
        // nbt['mID']!=1023 && nbt['mID']!=31041 && nbt['mID']!=31074 && nbt['mID']!=865 && nbt['mID']!=866)
        // if(nbt['mStoredSteam']==0)
        // if(nbt['mSolderingTool']!=0){
        // return GOLD+GetVtier(Math.abs(nbt['mEUt']))
        // }
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['eLongEUPerTick'])<=2147483640){
        // return GOLD+GetVtier(Math.abs(nbt['eLongEUPerTick']))
        // }
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['eLongEUPerTick'])>2147483640){
        // return GOLD+colorcir('§MAX§',3,1)
        // }
        // </line>
        //
        // <line displayname="§d功耗总额">
        // if(nbt['mID']!=1154)
        // if(nbt['mEUt']!=undefined && nbt['mEUt']!=0 && nbt['mID']!=1020 && nbt['mID']!=1021 && nbt['mID']!=1022 &&
        // nbt['mID']!=1023 && nbt['mID']!=31041 && nbt['mID']!=31074 && nbt['mID']!=865 && nbt['mID']!=866)
        // if(nbt['mStoredSteam']==0)
        // if(nbt['mMaxProgresstime']!=0){
        // return GOLD+formatBytes(nbt['mMaxProgresstime']*Math.abs(nbt['mEUt']))+GOLD+' EU'
        // }
        // if(nbt['mMaxProgresstime']!=0 && nbt['eLongEUPerTick']!=0 &&nbt['mID']==1004){
        // return GOLD+formatBytes(nbt['mMaxProgresstime']*Math.abs(nbt['eLongEUPerTick']))+GOLD+' EU'
        // }
        // </line>
        //
        // <!--MAX电流 -->
        // <line displayname="§d§k6§r§dMAX-Tier§k6">
        // if (nbt['mID']==14001 && nbt['euPerTick']>=21474836 && nbt['euPerTick']<2147483640){
        // return YELLOW+(nbt['euPerTick']/2147483640).toFixed(1)+GOLD+' A '+colorcir('§MAX§',3)
        // }
        // if (nbt['mID']==14001 && nbt['euPerTick']>=2147483640){
        // return YELLOW+(nbt['euPerTick']/2147483640).toFixed(1)+GOLD+' A '+colorcir('§MAX§',3,1)
        // }
        // if (Math.abs(nbt['mEUt'])>=21474836 && Math.abs(nbt['mEUt'])<2147483640){
        // return YELLOW+(Math.abs(nbt['mEUt'])/2147483640).toFixed(1)+GOLD+' A '+colorcir('§MAX§',3)
        // }
        // if (Math.abs(nbt['mEUt'])>=2147483640){
        // return YELLOW+standards((Math.abs(nbt['mEUt'])/2147483640).toFixed(0))+GOLD+' A '+colorcir('§MAX§',3,1)
        // }
        // if (Math.abs(nbt['eLongEUPerTick'])>=21474836 && Math.abs(nbt['eLongEUPerTick'])<2147483640){
        // return YELLOW+standards((Math.abs(nbt['eLongEUPerTick'])/2147483640).toFixed(0))+GOLD+' A
        // '+colorcir('§MAX§',3)
        // }
        // if (Math.abs(nbt['eLongEUPerTick'])>=2147483640){
        // return YELLOW+standards((Math.abs(nbt['eLongEUPerTick'])/2147483640).toFixed(0))+GOLD+' A
        // '+colorcir('§MAX§',3,1)
        // }
        // </line>
        //
        // <line displayname="§6玻璃等级-tier ">
        // var Gname = ['硼玻璃','钛','钨钢','镀铑钯','铱','锇','中子','宇宙中子','无尽','超时空',];
        // var Tier = ['§eHV','§7EV','§aIV','§dLuv','§bZPM','§2UV','§4UHV','§5UEV','§9§n§lUIV','§c§n§lUMV'];
        // if(nbt['glassTier']<=12 && nbt['glassTier']!=0){return
        // YELLOW+Gname[nbt['glassTier']-3]+WHITE+'('+Tier[nbt['glassTier']-3]+WHITE+')'}
        // </line>
        //
        // <line displayname="§6玻璃等级-tier ">
        // var Gname = ['硼玻璃','钛','钨钢','镀铑钯','铱','锇','中子','宇宙中子','无尽','超时空',];
        // var Tier = ['§eHV','§8EV','§aIV','§dLuv','§bZPM','§2UV','§4UHV','§5UEV','§9§n§lUIV','§c§n§lUMV'];
        // if(nbt['mGlassTier']<=12 && nbt['mGlassTier']!=0){return
        // YELLOW+Gname[nbt['mGlassTier']-3]+WHITE+'('+Tier[nbt['mGlassTier']-3]+WHITE+')'}
        // if(nbt['glasTier']<=12 && nbt['glasTier']!=0){return
        // YELLOW+Gname[nbt['glasTier']-3]+WHITE+'('+Tier[nbt['glasTier']-3]+WHITE+')'}
        // </line>
        //
        // <line displayname="§d机器功率">
        // if(nbt['mID']!=1154)
        // if(nbt['mEUt']!=undefined && nbt['mEUt']!=0 && nbt['mID']!=1020 && nbt['mID']!=1021 && nbt['mID']!=1022 &&
        // nbt['mID']!=1023 && nbt['mID']!=31041 && nbt['mID']!=31074 && nbt['mID']!=31074 && nbt['mID']!=865 &&
        // nbt['mID']!=866)
        // if(nbt['mStoredSteam']==0)
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['mEUt'])<=500000000)
        // {
        // return YELLOW+standards(Math.abs(nbt['mEUt']))+GOLD+" EU/t"
        // }
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['eLongEUPerTick'])<=500000000)
        // {
        // return YELLOW+standards(Math.abs(nbt['eLongEUPerTick']))+GOLD+" EU/t"
        // }
        // </line>
        // <line displayname="§d§k66机器功率§k66">
        // if(nbt['mID']!=1154)
        // if(nbt['mEUt']!=undefined && nbt['mEUt']!=0 && nbt['mID']!=1020 && nbt['mID']!=1021 && nbt['mID']!=1022 &&
        // nbt['mID']!=1023 && nbt['mID']!=31041 && nbt['mID']!=31074 && nbt['mID']!=865 && nbt['mID']!=866)
        // if(nbt['mStoredSteam']==0)
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['mEUt'])>500000000)
        // {
        // return YELLOW+standards(Math.abs(nbt['mEUt']))+GOLD+" EU/t"
        // }
        // if(nbt['mSolderingTool']!=0 && Math.abs(nbt['eLongEUPerTick'])>500000000)
        // {
        // return YELLOW+standards(Math.abs(nbt['eLongEUPerTick']))+GOLD+" EU/t"
        // }
        // </line>
        //
        // <line displayname="§d§k6§r§d接收能量存储§k6">
        // if (nbt['storageEU']>=0 && (nbt['mID']==19014)){return YELLOW+standards(nbt['storageEU'])+GOLD+"EU"}
        // </line>
        // <line displayname="§d§k666§r§d接收能量§k666">
        // if (nbt['usedPowerPoint']>=0 && (nbt['mID']==19014 && nbt['gravitationalLensTime']==0)){return
        // YELLOW+standards(nbt['usedPowerPoint'])+GOLD+"EU/t"}
        // if (nbt['usedPowerPoint']>=0 && (nbt['mID']==19014 && nbt['gravitationalLensTime']!=0)){return
        // YELLOW+standards(nbt['usedPowerPoint']*2)+GOLD+"EU/t"}
        // </line>
        //
        // <!--多方块机器于未完全修复情况下的功率 -->
        // <line displayname="hud.msg.greg.power.unfix">
        // if(nbt['mEUt']!=0) if(nbt['mEUt']!=undefined)
        // if(nbt['mWrench']==0 || nbt['mScrewdriver']==0 || nbt['mSoftHammer']==0 || nbt['mHardHammer']==0 ||
        // nbt['mSolderingTool']==0 || nbt['mCrowbar']==0){return YELLOW+Math.abs(nbt['mEUt'])*1.1+GOLD+" EU/t"}
        // </line>
        //
        // <!--蒸汽机器：功率 -->
        // <line displayname="§9蒸汽需求">
        // if(nbt['mEUt']!=0 && nbt['mActive']==1)if(nbt['mEUt']!=undefined)
        // if(nbt['mStoredSteam']!=0 || nbt['mID']==31041){return YELLOW+Math.abs(nbt['mEUt']) * 2+WHITE+" mB/t"}
        // </line>
        //
        // <!--机器/电池箱缓存能量 -->
        // <line displayname="§d能量">
        // if(nbt['mStoredEnergy']!=0&&nbt['mID']!=13106){return GREEN+formatBytes(nbt['mStoredEnergy'])+GOLD+" EU"}
        // if(nbt['mID']==13106){
        // abcd=abcdefg(""+nbt['stored']);
        // efg=(abcd/abcdefg(""+nbt['capacity'])*100).toFixed(5);
        // return GREEN+formatBytes(abcd)+" "+efg+"%";
        // }
        // </line>
        //
        // <!--大硅岩 -->
        // <line displayname="§9工作进程">
        // if (nbt['mID']==12732 && nbt['mbasicOutput']>=0){
        //
        // }
        // </line>
        //
        // <!--机器：加工进度 -->
        // <line displayname="§9工作进程">
        // if(nbt['mMaxProgresstime']!=0)
        // if(nbt['mID']!=1154 && nbt['mID']!=1131 && nbt['mID']!=1020 && nbt['mID']!=1021 && nbt['mID']!=1022 &&
        // nbt['mID']!=1023 && nbt['mID']!=1171)
        // if(nbt['mMaxProgresstime']!=undefined)
        // { outpu = "";
        // tian=shi=fen=miao=kk=0;
        // ccc=ddd=nbt['mMaxProgresstime'];
        // if(ddd==1)return colorcir("§你已经",0,1)+colorcir("超",1)+colorcir("到1T啦§",0,1);
        // bbb=ddd-nbt['mProgresstime'];
        // for(i=1;ddd>i<<10;i*=10){kk++;if(kk>8)break;}
        // aaa=((ddd-bbb)*100/ddd).toFixed(kk);
        //
        // for(i=0;i<2;i++){
        // if(ccc>1728000){tian=Math.floor(ccc/1728000);ccc=ccc-tian*1728000;outpu=outpu+DRED+tian+DRED+"天";}
        // if(ccc>72000||tian>0){shi=Math.floor(ccc/72000);ccc=ccc-shi*72000;outpu=outpu+RED+shi+RED+"时";}
        // if(ccc>1200||tian>0||shi>0){fen=Math.floor(ccc/1200);ccc=ccc-fen*1200;outpu=outpu+AQUA+fen+AQUA+"分";}
        // miao=ccc/20;
        // if(i==1&&(tian+shi+fen)!=0||ccc>200)miao=Math.floor(miao);
        // outpu=outpu+GREEN+miao+GREEN+"秒";
        // if(i==0){out =outpu;outpu = "";ccc=bbb;}
        // }
        // fda=aaa+BLUE+"%"+ ALIGNRIGHT+"\\n"+GREEN+"需耗时"+WHITE+out+TAB+"§9剩余";
        // if((100/3)>aaa)outpu=RED+fda+RED+outpu;
        // else if((100/1.5)>aaa)outpu=YELLOW+fda+YELLOW+outpu;
        // else outpu=GREEN+fda+GREEN+outpu;
        // return outpu+ALIGNRIGHT;
        // }
        // </line>
        //
        // <!--蒸汽存量 -->
        // <line displayname="§9蒸汽存量">
        // if(nbt['mSteam']['Amount']!=undefined){return nbt['mSteam']['Amount']+" mB"}
        // </line>
        // <line displayname="§9蒸汽存量">
        // if(nbt['mStoredSteam']!=0){return nbt['mStoredSteam']+" mB"}
        // </line>
        //
        // <!--大型热交换器：效率 -->
        // <line displayname="§b效率">
        // if(nbt['mActive']!=0)
        // if(nbt['mID']==1154){return YELLOW+((nbt['mEfficiency']) / 100).toFixed(0)+WHITE+" %"}
        // </line>
        //
        // <!--大型热交换器：蒸汽生产速度 -->
        // <line displayname="§9正在生产">
        // if(nbt['mActive']!=0) if(nbt['mEUt']!=undefined) if(nbt['mID']==1154)
        // if(nbt['superheated']==0){return GREEN+"蒸汽: "+YELLOW+Math.abs(nbt['mEUt']) * 2+YELLOW+" mB/t"}
        // </line>
        //
        // <!--大型热交换器：过热蒸汽生产速度 -->
        // <line displayname="§9正在生产">
        // if(nbt['mActive']!=0) if(nbt['mEUt']!=undefined) if(nbt['mID']==1154)
        // if(nbt['superheated']==1){return GREEN+"过热蒸汽: "+YELLOW+Math.abs(nbt['mEUt'])+WHITE+" mB/t"}
        // </line>
        //
        // <!--大型涡轮机：转子耐久 -->
        // <line displayname="§b转子耐久">
        // a=nbt['Inventory']['0']['tag']['GT.ToolStats']['Damage'];
        // b=nbt['Inventory']['0']['tag']['GT.ToolStats']['MaxDamage'];
        // if(nbt['mEUt']!=undefined)
        // if(nbt['mID']==1131 || nbt['mID']==865 || nbt['mID']==866 || nbt['mID']==1151 || nbt['mID']==1152 ||
        // nbt['mID']==1153){return YELLOW+((1-a/b)*100).toFixed(2)+WHITE+" %"}
        // </line>
        //
        // <!--大型涡轮机：效率 -->
        // <line displayname="§b效率">
        // if(nbt['mEfficiency']!=0)if(nbt['mEfficiency']!=undefined)
        // if(nbt['mID']==1131 || nbt['mID']==865 || nbt['mID']==866 || nbt['mID']==1151 || nbt['mID']==1152 ||
        // nbt['mID']==1153){return YELLOW+((nbt['mEfficiency']) / 100).toFixed(0)+WHITE+" %"}
        // </line>
        //
        // <!--大型涡轮机：是否运行 -->
        // <line displayname="§b状态">
        // if(nbt['mEUt']!=undefined)
        // if(nbt['mID']==1131 || nbt['mID']==865 || nbt['mID']==866 || nbt['mID']==1151 || nbt['mID']==1152 ||
        // nbt['mID']==1153){
        // switch(nbt['mActive']){case 0:return RED+"停止运行";break; case 1:return GREEN+"运行正常";break;}}
        // </line>
        //
        // <!--大型锅炉：蒸汽生产速度 -->
        // <line displayname="§b蒸汽生产">
        // if(nbt['mActive']!=0) if(nbt['mEUt']!=undefined)
        // if(nbt['mID']==1020 || nbt['mID']==1021 || nbt['mID']==1022 || nbt['mID']==1023){return
        // YELLOW+Math.abs(nbt['mEUt']) * 2+WHITE+" mB/t"}
        // </line>
        //
        // <!--大型柴油引擎：效率 -->
        // <line displayname="§b效率">
        // if(nbt['mEfficiency']!=0) if(nbt['mEfficiency']!=undefined)
        // if(nbt['mID']==1171){return YELLOW+((nbt['mEfficiency']) / 100).toFixed(0)+WHITE+" %"}
        // </line>
        //
        // <!--多方块机器需要维修 -->
        // <line displayname="§c注意 !">
        // if(nbt['mWrench']==0 || nbt['mScrewdriver']==0 || nbt['mSoftHammer']==0 || nbt['mHardHammer']==0 ||
        // nbt['mSolderingTool']==0 || nbt['mCrowbar']==0 || nbt['eCertainS']>0){
        // return RED+"xx 需要维护 xx"}
        // </line>
        // <line displayname="§c注意 !">
        // if(nbt['mWrench']==0){return RED+"xx 需要扳手 xx"}
        // if(nbt['mScrewdriver']==0){return RED+"xx 需要螺丝刀 xx"}
        // if(nbt['mSoftHammer']==0){return RED+"xx 需要软锤 xx"}
        // if(nbt['mHardHammer']==0){return RED+"xx 需要锤子 xx"}
        // if(nbt['mHardHammer']==0){return RED+"xx 需要锤子 xx"}
        // if(nbt['mSolderingTool']==0){return RED+"xx 需要电烙铁 xx"}
        // if(nbt['mCrowbar']==0){return RED+"xx 需要撬棍 xx"}
        // if(nbt['eCertainS']>0){return RED+"xx 需要定元 xx"}
        // </line>
        //
        // <!--RF能量转换器 -->
        // <line displayname="§b输出电压">
        // if(nbt['mID']==31022){
        // switch(nbt['mTier']){
        // case 0:return YELLOW+"ULV"; break;
        // case 1:return YELLOW+"LV"; break;
        // case 2:return YELLOW+"MV"; break;
        // case 3:return YELLOW+"HV"; break;
        // case 4:return YELLOW+"EV"; break;
        // case 5:return YELLOW+"IV"; break;
        // case 6:return YELLOW+"LuV"; break;
        // case 7:return YELLOW+"ZPM"; break;
        // case 8:return YELLOW+"UV"; break;
        // case 9:return YELLOW+"MAX"; break;}}
        // </line>
        // <line displayname="§b输出电流">
        // if(nbt['mID']==31022){return YELLOW+nbt['aCurrentOutputAmperage']+GOLD+" A"}
        // </line>
        //
        // <!--能源仓 -->
        // <line displayname="§d电压输入">
        // for(i=0;i<=9;i++){
        // if(nbt['mID']==40+i||nbt['mID']==150+i||nbt['mID']==690+i){return GREEN+standards(8*Math.pow(4,i))+GOLD+"
        // EU/t"}
        // }
        // for(i=0;i<=3;i++){
        // if(nbt['mID']==11300+i){return GREEN+standards(8388608*Math.pow(4,i))+GOLD+" EU/t"}
        // }
        // for(i=0;i<=8;i++){
        // if(nbt['mID']==15100+i||nbt['mID']==15110+i||nbt['mID']==15120+i){return
        // GREEN+standards(8192*Math.pow(4,i))+GOLD+" EU/t"}
        // }
        // </line>
        //
        // <!--电池箱 -->
        // <line displayname="§9最大电流">
        // if(isBetween(160,199,nbt['mID'])||isBetween(690,699)||isBetween(11240,11275)){return GREEN+b+GOLD+" A"}
        // </line>
        //
        // <!--机器/电池箱内电池总能量 -->
        // <line displayname="§9电池总和">
        // a=0;
        // b=0;
        // for(i=0,len=nbt['Inventory'].length;i<len;i++){
        // b=b+1;
        // if(nbt['Inventory'][i]['tag']!=undefined){
        // if(nbt['Inventory'][i]['tag']['GT.ItemCharge']!=undefined){
        // a=a+nbt['Inventory'][i]['tag']['GT.ItemCharge'];
        // }else if(nbt['Inventory'][i]['tag']['charge']!=undefined){
        // a=a+nbt['Inventory'][i]['tag']['charge']
        // }
        // }
        // };
        // if(a!=0){
        // return GREEN+formatBytes(a)+GOLD+" EU"}
        // </line>
        //
        // <!--电池箱 -->
        // <line displayname="§9电压输出">
        // for(i=0;i<=9;i++){
        // if(nbt['mID']==160+i||nbt['mID']==170+i||nbt['mID']==180+i||nbt['mID']==190+i||nbt['mID']==30+i||nbt['mID']==690+i){return
        // GREEN+8*Math.pow(4,i)+GOLD+" EU/t"}
        // }
        // for(i=0;i<=5;i++){
        // if(nbt['mID']==11240+i||nbt['mID']==11250+i||nbt['mID']==11260+i||nbt['mID']==11270+i){
        // if(i==4){return GREEN+1073741824+GOLD+" EU/t"}
        // else if (i==5){return GREEN+2147483640+GOLD+" EU/t"}
        // else return GREEN+8388608*Math.pow(4,i)+GOLD+" EU/t"
        // }
        // }
        // </line>
        //
        // <!--机器里液体存量 -->
        // <line displayname="§9流体存量">
        // if(nbt['mFluid']['Amount']!=undefined){return GREEN+fluidName(nbt['mFluid']['FluidName'])+" :
        // "+AQUA+standards(nbt['mFluid']['Amount'])+YELLOW+" mB"}
        // </line>
        //
        // <!-- 多方块机器物品输出 -->
        // <line displayname="§6物品输出">
        // if (nbt['eFlip'] != undefined && nbt['mOutputItem0'] != undefined && nbt['mActive']==1) {
        // outputString = "";i=0;
        // var items = {};
        // for (; nbt['mOutputItem' + i] != undefined; i++)
        // {
        //
        // str = "mOutputItem" + i;
        // itemName = name(nbt[str]);
        // itemCount = nbt[str]['Count'];
        // if (itemName in items)
        // {
        // items[itemName] += itemCount;
        // }
        // else
        // {
        // items[itemName] = itemCount;
        // }
        // };outputString +=GOLD+"循环"+GREEN+i+GOLD+"次" + ALIGNRIGHT+"\\n";
        //
        // var keys = new Array();
        // for (var key in items) {
        // keys.push(key);
        // }
        // if (keys.length != 0) {
        // for (i = 0; i < keys.length; i++) {
        // outputString += YELLOW + standards(items[keys[i]]) + "个" + GREEN + keys[i] +ALIGNRIGHT;
        // if (i != keys.length - 1) {
        // outputString += "\\n" ;
        // }
        // };
        // return outputString;
        // }
        // };
        // </line>
        //
        // <!--机器：液体产物 -->
        // <line displayname="§6流体">
        // if(nbt['mOutputFluid']['Amount']!=undefined){return AQUA+standards(nbt['mOutputFluid']['Amount'])+YELLOW+"mB
        // "+GREEN+fluidName(nbt['mOutputFluid']['FluidName'])}
        // </line>
        //
        // <!-- 大型机器：液体产物 -->
        // <line displayname="§6流体输出">
        // if (nbt['eFlip'] != undefined && nbt['mOutputFluids0'] != undefined && nbt['mActive']==1) {
        // outputString = "\\n";
        // for(i=0; nbt['mOutputFluids' + i]!=undefined; i++) {
        // str = 'mOutputFluids' + i;
        //
        // outputString += AQUA+standards(nbt[str]['Amount'])+YELLOW+"mB "+GREEN+
        // fluidName(nbt[str]['FluidName'])+ALIGNRIGHT+ "\\n" ;
        //
        // };
        // return outputString;
        // };
        // </line>
        // <!-- 单方块：自动输出 -->
        if (nbt.hasKey("mOutputFluid")) {
            patternLine(lines, "§9自动输出", nbt.getBoolean("mOutputFluid") ? GREEN + "开启" : RED + "关闭");
        }
        // <!--机箱状态 -->
        // <line displayname="§a超频比">
        // if(nbt['mID']==15450 && nbt['mActive']==1) {return AQUA+nbt['eOverClock']}
        // </line>
        // <line displayname="§a过压比">
        // if(nbt['mID']==15450 && nbt['mActive']==1) {return AQUA+nbt['eOverVolt']}
        // </line>
        // <line displayname="§c机箱热量">
        // if(nbt['mID']==15450 && nbt['mActive']==1) {return RED+standards(nbt['eHeat'])}
        // </line>
        //
        //
        // <!--机器状态 -->
        // <line>
        // if(nbt['mAllowInputFromOutputSide']==1){return "输出面允许输入"}
        // </line>
        //
        // <!-- 算力 -->
        // <line displayname="§c最低算力需求">
        // if(nbt['eDataR'] != undefined && nbt['mActive']==1 && nbt['eDataR'] != 0) {return standards(nbt['eDataR'])
        // +RED+ "/s"}
        // </line>
        // <line displayname="§6当前算力">
        // if(nbt['eDataA'] != undefined && nbt['mActive']==1) {return YELLOW+standards(nbt['eDataA']) +GREEN+ "/s"}
        // </line>
        // <line displayname="§a总算力需求">
        // if(nbt['eComputationRequired'] != undefined && nbt['mActive']==1) {return
        // GREEN+standards(nbt['eComputationRequired'] / 20)}
        // </line>
        // <line displayname="§9进度">
        // if(nbt['eComputationRequired'] != undefined && nbt['mActive']==1) {return
        // YELLOW+standards(((nbt['eComputationRequired'] - nbt['eComputationRemaining']) / 20).toFixed(0)) +RED+ "/"
        // +GOLD+ standards((nbt['eComputationRequired'] / 20 ))}
        // </line>
        // </tileentity>

        return lines;
    }

}
