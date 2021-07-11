# booster-transform-retrace-plugin

This project is used for retrace method instructions invocation, such as  `androidx/appcompat/app/AppCompatActivity.<init>()V`, `androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V`, etc.

## Properties

The following table shows the properties that transformer supports:

| Property                         | Description                                                  | 
| -------------------------------- | ------------------------------------------------------------ | 
| `booster.transform.retraces` |  method instructions to retrace                  | 

## Usages

This plugin is base on [Booster](https://github.com/didi/booster). So you must import booster plugin, and add this plugin classpath in root build script as following:
```groovy
buildscript {
    ext.booster_version = '3.3.1'
    repositories {
        google()
        mavenCentral()

        // OPTIONAL If you want to use SNAPSHOT version, sonatype repository is required.
        maven { url 'https://oss.sonatype.org/content/repositories/public' }
    }
    dependencies {
        classpath "com.didiglobal.booster:booster-gradle-plugin:$booster_version" 
        classpath "com.neighbwang.gradle:booster-transform-api-scanner-plugin:1.1.0"
    }
}

```


The properties can be passthrough the command line as following:

```bash
./gradlew transformClassesWithBoosterForDebug -Pbooster.transform.retraces=androidx/appcompat/app/AppCompatActivity.<init>()V , androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V
```

or configured in the `gradle.properties`:

```properties
booster.transform.retraces=androidx/appcompat/app/AppCompatActivity.<init>()V,androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V
```

Add run relative-task(such as `transformClassesWithBoosterForDebug` or `assembleDebug`, etc), then report will be generated as following: `${app-build}/reports/RetraceTransformer/${variant}/report.txt`.

