-keep class com.shareware.oaid.OaIdGenerator{
    public <init>(android.content.Context);
}

# 本库模块专用的混淆规则
-keep class aidl.android.creator.** { *; }
-keep interface aidl.android.creator.** { *; }
-keep class aidl.asus.msa.SupplementaryDID.** { *; }
-keep interface aidl.asus.msa.SupplementaryDID.** { *; }
-keep class aidl.bun.lib.** { *; }
-keep interface aidl.bun.lib.** { *; }
-keep class aidl.heytap.openid.** { *; }
-keep interface aidl.heytap.openid.** { *; }
-keep class aidl.samsung.android.deviceidservice.** { *; }
-keep interface aidl.samsung.android.deviceidservice.** { *; }
-keep class aidl.zui.deviceidservice.** { *; }
-keep interface aidl.zui.deviceidservice.** { *; }
-keep class aidl.coolpad.deviceidsupport.** { *; }
-keep interface aidl.coolpad.deviceidsupport.** { *; }
-keep class aidl.google.android.gms.ads.identifier.internal.** { *; }
-keep interface aidl.google.android.gms.ads.identifier.internal.* { *; }
-keep class aidl.oplus.stdid.** {*; }
-keep interface aidl.oplus.stdid.** {*; }

-keep class com.huawei.hms.ads.** {*; }
-keep interface com.huawei.hms.ads.** {*; }
-keep class com.hihonor.ads.** {*; }
-keep interface com.hihonor.ads.** {*; }
