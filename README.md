# adapt-slf4j-to-servlet

Copyright © 2021–2022, Christopher Alan Mosher, Shelton, Connecticut, USA, <cmosher01@gmail.com>.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=CVSSQ2BWDCKQ2)
[![License](https://img.shields.io/github/license/cmosher01/adapt-slf4j-to-servlet.svg)](https://www.gnu.org/licenses/gpl.html)

Slf4j adaptation layer, that sends log messages to the servlet context.

```groovy
repositories {
    mavenCentral()
    maven {
        url = uri('https://public:\u0067hp_fya6Kseu3XOBMg2icbg7f1LP6ZFYjj35v4Zj@maven.pkg.github.com/cmosher01/*')
    }
}

dependencies {
    implementation group: 'org.slf4j', name: 'slf4j-api', version: 'latest.integration'
    runtimeOnly group: 'nu.mine.mosher.io', name: 'adapt-slf4j-to-servlet', version: 'latest.release'
}
```
