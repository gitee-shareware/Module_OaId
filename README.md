# Module_OaId

#### 介绍
Android 平台非常好用的OAID获取仓库，由开源项目[Android_CN_OAID](https://github.com/gzu-liyujiang/Android_CN_OAID)改造而来

**注意：主要是对原项目进行精简代码，极简API**

安卓设备唯一标识解决方案，可作为移动安全联盟统一 SDK （miit_mdid_xxx.aar）的替代方案。

## 支持情况

| 厂商或品牌                       | 系统或框架                                                 |
|-----------------------------|-------------------------------------------------------|
| 华为（Huawei）                  | HMS Core 2.6.2+、Google Play Service 4.0+              |
| 荣耀（Honor）                   | Magic UI 4/5/6、MagicOS 7.0+、Google Play Service 4.0+  |
| 小米（XiaoMi、Redmi、BlackShark） | MIUI 10.2+、Google Play Service 4.0+                   |
| 维沃（VIVO、IQOO）               | Funtouch OS 9+、OriginOS 1.0+、Google Play Service 4.0+ |
| 欧珀（OPPO、Realme）             | ColorOS 7.0+、Google Play Service 4.0+                 |
| 三星（Samsung）                 | Android 10+、Google Play Service 4.0+                  |
| 联想（Lenovo）                  | ZUI 11.4+、Google Play Service 4.0+                    |
| 华硕（ASUS）                    | Android 10+、Google Play Service 4.0+                  |
| 魅族（Meizu）                   | Android 10+、Google Play Service 4.0+                  |
| 一加（OnePlus）                 | Android 10+、Google Play Service 4.0+                  |
| 努比亚（Nubia）                  | Android 10+、Google Play Service 4.0+                  |
| 酷派（Coolpad）                 | CoolOS、Google Play Service 4.0+                       |
| 酷赛（Coosea ）                 | Android 10+、Google Play Service 4.0+                  |
| 卓易（Droi ）                   | Freeme OS、Google Play Service 4.0+                    |
| 其他（ZTE、HTC、Motorola、……）     | SSUI、Google Play Service 4.0+                         |
|

## 最新版本：1.0.5

[更新日志](/CHANGELOG.md)

### 使用说明

直接使用如下依赖：
```groovy
dependencies {
    implementation 'com.gitee.shareware:oaid:1.0.5'
}
```

### 注意事项

- **1.0.5 版本直接使用了华为、荣耀的官方广告标识服务SDK，想要与移动安全联盟 SDK 共存** 可参考如下配置

```groovy
dependencies {
    implementation('com.gitee.shareware:oaid:最新版本') {
        // 如果使用了移动安全联盟SDK，需排除掉本项目依赖的华为/荣耀官方广告标识服务SDK
        //华为SDK
        exclude group: 'com.huawei.hms', module: 'ads-identifier' 
        //荣耀SDK
        exclude group: 'com.hihonor.mcs', module: 'ads-identifier'
    }
}
```
## 代码示例

```java
方式一：
//隐私协议统一之后调用
OaIdGenerator.Companion.getOaId(this, new IGetter() {
    @Override
    public void onOAIDGetError(@NonNull Exception error) {
        Log.d("TestActivity", "onOAIDGetComplete: " + error.getMessage());
    }

    @Override
    public void onOAIDGetComplete(@NonNull String result) {
        Log.d("TestActivity", "onOAIDGetComplete: " + result);
    }
});

方式二：
结合utils库使用（com.gitee.shareware:utils:1.0.4）
https://github.com/gitee-shareware/Module_Utils
AppExt.getOaId()
```

## 混淆规则
本库自带consumer-rules.pro，远程依赖，无需配置
