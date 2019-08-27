# Selenium Test Framework  
This project provide Selenium test framework to implement and run functional tests.  
  
Framework is fully configurable by configuration.properties file (placed in resources directory).  
  

## **Configuration description**

 

***applicationAddress*** - base address of our application under tests, by default points to sample shop application hosted on SII servers .
```  
applicationAddress=http://5.196.7.235  
```  
  
***browserName*** - name of browser on which we are going to run tests, for now possible values are: CHROME, FIREFOX  
```  
browserName=CHROME  
```  
  
***driverType*** - type of driver on which we are going to run tests, possible values: LOCAL, REMOTE
```  
driverType=LOCAL  
```  
  
***gridHubUrl*** - url of grid hub, useful only when driverType set to REMOTE.  
For REMOTE driver type we need to run selenium grid hub first - more info at https://www.seleniumhq.org/docs/07_selenium_grid.jsp  
```  
gridHubUrl=http://localhost:5566/wd/hub  
```  
  

## Run automated tests

  
To run the tests you can use simple maven command:  
```  
mvn clean test  
```  
  

## Generate test report

  
Allure framework is used to generate report from test results - https://github.com/allure-framework/allure-maven  

To serve test report on local machine make sure you have run tests using `mvn clean test` command before, then use below command:  
```  
mvn allure:serve  
```  

If uou need to generate static html report from your test result just use following command.  
Report will be generated tо directory: target/site/allure-maven/index.html  
```  
mvn allure:report  
```