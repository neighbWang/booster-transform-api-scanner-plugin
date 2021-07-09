# booster-transform-retrace-plugin

This project is used for retrace method instructions invocation, such as  `androidx/appcompat/app/AppCompatActivity.<init>()V`, `androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V`, etc.

## Properties

The following table shows the properties that transformer supports:

| Property                         | Description                                                  | 
| -------------------------------- | ------------------------------------------------------------ | 
| `booster.transform.retraces` |  method instructions to retrace                  | 

## Usages

The properties can be passthrough the command line as following:

```bash
./gradlew transformClassesWithBoosterForDebug -Pbooster.transform.retraces=androidx/appcompat/app/AppCompatActivity.<init>()V , androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V
```

or configured in the `gradle.properties`:

```properties
booster.transform.retraces=androidx/appcompat/app/AppCompatActivity.<init>()V,androidx/appcompat/app/AppCompatActivity.onCreate(Landroid/os/Bundle;)V
```

Add run relative-task(such as `transformClassesWithBoosterForDebug` or `assembleDebug`, etc), then report will be generated as following: `${app-build}/reports/RetraceTransformer/${variant}/report.txt`

