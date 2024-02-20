# Selenium Test Framework  
This project provides selenium test framework. It is written in Java and follows best practices as well as most recent 
and top rated tools. It is designed for web test automation, to implement and run robust functional tests.  
Since everything is set up, the tests can be added and run straight away.
 
## Framework Features:
* follows page object pattern
* parallel test execution ready
* full control by annotations
* most popular browsers preconfigured
* downloading OS specific binaries automatically
* configurability by configuration properties file
* pretty and highly readable test result report
* easy for extension and customisation
 
## The Project's Structure:
* the pl.sii.framework package placed in src\main\java contains framework specific implementation
* the pl.sii package placed in src\test\java contains automated sample test cases 
* src\main\resources contains properties files
* pom.xml: maven file defines the following:
  * artifact id as JavaPageObjectTemplate
  * all the required dependencies with versions extracted to maven properties to reduce maintenance
  * build plugins to compile java classes, run tests and generate report

## Framework Extension
* to extend framework components for extra web elements add class implementation to pl.sii.framework.base.components package
* to provide additional browsers consider the RemoteDriverFactory class placed in pl.sii.framework.base.factory package

## Configuration Description
* **applicationAddress** - base address of application under test, by default points to sample shop application hosted on SII servers.
 ```  
   applicationAddress=http://www.automationpractice.pl/index.php
```  
  
* **browserName** - name of the browser to run tests on, already preconfigure values are: CHROME, FIREFOX  
```  
  browserName=CHROME  
```  
  
* **driverType** - the driver type to run tests, supported values are: LOCAL, REMOTE
```  
  driverType=LOCAL  
```  
  
* **gridHubUrl** - url address of grid hub, useful only when driverType set to REMOTE.  
For REMOTE driver type run selenium grid hub first - more details at https://www.seleniumhq.org/docs/07_selenium_grid.jsp  
```  
  gridHubUrl=http://localhost:5566/wd/hub  
```  

## Test Implementation
* new test's implementation is to be placed in the pl.sii package placed in src\test\java
* test class implementation needs to extend BaseTest.java class

## Run Automated Tests
To run all tests use simple maven command:  
```  
mvn clean test  
```  

To run tests for particular @tag use simple maven command:  
```  
mvn clean test -Dtag=<tag_name>
``` 
  
## Generate Test Report  
Allure framework is used to generate report from test results - https://github.com/allure-framework/allure-maven  

To serve test report on local machine make sure you have run tests using `mvn clean test` command before, then use below command:  
```  
mvn allure:serve  
```  

In order to generate static html report from your test result just use following command.  
```  
mvn allure:report  
```
Report will be generated in: target/site/allure-maven/index.html