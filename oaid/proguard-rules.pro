# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keeppackagenames com.shareware.oaid.**
-keep class com.shareware.oaid.OaIdGenerator{
    public <init>(android.content.Context);
    public static getOaId(android.content.Context,com.shareware.oaid.IGetter);
    public static com.shareware.oaid.OaIdGenerator$Companion Companion;
}
-keep interface com.shareware.oaid.IGetter{*;}

-keep class com.shareware.oaid.OaIdGenerator$Companion{
    public void getOaId(android.content.Context,com.shareware.oaid.IGetter);
}

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