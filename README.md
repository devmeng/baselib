[![](https://jitpack.io/v/devmeng/baselib.svg)](https://jitpack.io/#devmeng/baselib)
项目使用 ViewBinding 获取页面控件
buildFeatures {
    viewBinding = true
}
##开发前的必需配置：
* [1.将 baselib 中的 config.gradle 文件导入到工程目录下] 
* [2.在工程目录下的 build.gradle 中应用 config.gradle]
* [配置文件，如下 apply from: 'config.gradle'] 
=====================================================

登录注册功能实现
根据需求修改内容

=====================================================
动态换肤功能
1.SkinManager.init(application) 初始化该功能
默认在 com.devmeng.baselib.base.BaseApplication 中初始化
2.根据需求，在 SkinAttribute.attributeList 中增加换肤属性
3.需要创建制作皮肤包的项目，用于制作皮肤包 apk
建议将皮肤包上传至后台服务器
4.在换肤业务逻辑中使用
切换皮肤 SkinManager.instance.loadSkin(skinPath)
还原皮肤 SkinManager.instance.loadSkin()

===================================================== 

内置全量更新/热更新功能 需要将 tinker-support.gradle 配置在
application module 即主应用目录下 
并在 build.gradle 中配置 apply from: 'tinker-support.gradle' 
其中 UpgradeActivity 为更新弹窗，可进行自定义 
注:需要将公司注册的 tinkerId 配置在 company_application_id.xml 中的 tinker_app_id 下

===================================================== 
组件化开发（提高开发效率）根据测试需要开启
1.gradle.properties
配置对应于每个模块在模块与 library 之间转换的判断语句或 Boolean 类型的值 
例:xxxRunAlone = true (true 为独立运行即模块形式， false 为合并模块即 library 形式)
2.在对应模块的 build.gradle 中配置转换所需的判断语句 例:
if(xxxRunAlone.toBoolean())
{ apply plugin: 'com.android.application' } 
else { apply plugin: 'com.android.library' } 
3.在对应模块的 build.gradle 中配置独立运行所需的清单文件及内部的个性化配置 
android { sourceSets{ main{ if(
xxxRunAlone.toBoolean()){ manifest.srcFile 'src/main/manifest/AndroidManifest.xml' } else {
manifest.srcFile 'src/main/AndroidManifest.xml' } } } } 
4.在 App 主程序的 build.gradle 中应用对应模块 例:
if(!xxxRunAlone.toBoolean()){ 
api project(path:"xxxModule")
} 
===================================================== 
注意：《暂缓嵌入 AspectJx "RuntimeException: Zip file is empty" 问题尚未解决》 
内部可嵌入 AOP 编程逻辑(AspectJ插件)所需的插件，嵌入方法如下 
1.在工程 build.gradle 中导入如下代码(插入 AspectJ 插件)
//AspectJ 面向切面编程插件 
classpath 'org.aspectj:aspectjtools:1.9.1' 
classpath 'org.aspectj:aspectjweaver:1.9.1' 
2.在模组的 build.gradle 中导入依赖 
api rootProject.ext.aspectJ["aspectjrt"]
注意：需要在 config.gradle 配置完成情况下导入
3.AspectJ 控制器，例如：

===================================================== 
GreenDao数据库的配置 
1.在项目目录下的 build.gradle 中配置插件
classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' 
2.在 module 或 library 中应用 greenDao 插件，并添加依赖
apply plugin: 'org.greenrobot.greendao' 
dependencies 
{ api rootProject.ext.greenDaoLibs } 
3.在 module 或 library 中做出相关配置 
greendao { schemaVersion 1 //数据库版本号 daoPackage 'com.xxx.xxx.db' //
设置DaoMaster、DaoSession、Dao 一般包名+文件夹名 
targetGenDir 'src/main/java' //设置DaoMaster、DaoSession、Dao目录
文件生成的目录，相当于父级目录 }