@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  router startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and ROUTER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\router.jar;%APP_HOME%\lib\common.jar;%APP_HOME%\lib\camel-core-3.9.0.jar;%APP_HOME%\lib\camel-activemq-3.9.0.jar;%APP_HOME%\lib\camel-jms-3.9.0.jar;%APP_HOME%\lib\camel-gson-3.9.0.jar;%APP_HOME%\lib\camel-jetty-3.9.0.jar;%APP_HOME%\lib\camel-http-3.9.0.jar;%APP_HOME%\lib\camel-mail-3.9.0.jar;%APP_HOME%\lib\camel-jsonpath-3.9.0.jar;%APP_HOME%\lib\logback-classic-1.2.3.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.30.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.30.jar;%APP_HOME%\lib\activation-1.1.1.jar;%APP_HOME%\lib\gson-2.8.6.jar;%APP_HOME%\lib\camel-health-3.9.0.jar;%APP_HOME%\lib\camel-xml-jaxb-3.9.0.jar;%APP_HOME%\lib\camel-spring-3.9.0.jar;%APP_HOME%\lib\camel-core-engine-3.9.0.jar;%APP_HOME%\lib\camel-core-languages-3.9.0.jar;%APP_HOME%\lib\camel-bean-3.9.0.jar;%APP_HOME%\lib\camel-browse-3.9.0.jar;%APP_HOME%\lib\camel-file-3.9.0.jar;%APP_HOME%\lib\camel-cluster-3.9.0.jar;%APP_HOME%\lib\camel-controlbus-3.9.0.jar;%APP_HOME%\lib\camel-dataformat-3.9.0.jar;%APP_HOME%\lib\camel-dataset-3.9.0.jar;%APP_HOME%\lib\camel-direct-3.9.0.jar;%APP_HOME%\lib\camel-directvm-3.9.0.jar;%APP_HOME%\lib\camel-language-3.9.0.jar;%APP_HOME%\lib\camel-log-3.9.0.jar;%APP_HOME%\lib\camel-mock-3.9.0.jar;%APP_HOME%\lib\camel-ref-3.9.0.jar;%APP_HOME%\lib\camel-rest-3.9.0.jar;%APP_HOME%\lib\camel-saga-3.9.0.jar;%APP_HOME%\lib\camel-scheduler-3.9.0.jar;%APP_HOME%\lib\camel-stub-3.9.0.jar;%APP_HOME%\lib\camel-vm-3.9.0.jar;%APP_HOME%\lib\camel-seda-3.9.0.jar;%APP_HOME%\lib\camel-timer-3.9.0.jar;%APP_HOME%\lib\camel-validator-3.9.0.jar;%APP_HOME%\lib\camel-xpath-3.9.0.jar;%APP_HOME%\lib\camel-xslt-3.9.0.jar;%APP_HOME%\lib\camel-xml-jaxp-3.9.0.jar;%APP_HOME%\lib\camel-jetty-common-3.9.0.jar;%APP_HOME%\lib\camel-http-common-3.9.0.jar;%APP_HOME%\lib\camel-attachments-3.9.0.jar;%APP_HOME%\lib\camel-core-reifier-3.9.0.jar;%APP_HOME%\lib\camel-cloud-3.9.0.jar;%APP_HOME%\lib\camel-core-model-3.9.0.jar;%APP_HOME%\lib\camel-http-base-3.9.0.jar;%APP_HOME%\lib\camel-base-engine-3.9.0.jar;%APP_HOME%\lib\camel-base-3.9.0.jar;%APP_HOME%\lib\camel-core-processor-3.9.0.jar;%APP_HOME%\lib\camel-support-3.9.0.jar;%APP_HOME%\lib\activemq-spring-5.16.1.jar;%APP_HOME%\lib\activemq-pool-5.16.1.jar;%APP_HOME%\lib\json-path-2.4.0.jar;%APP_HOME%\lib\camel-api-3.9.0.jar;%APP_HOME%\lib\camel-management-api-3.9.0.jar;%APP_HOME%\lib\camel-util-3.9.0.jar;%APP_HOME%\lib\activemq-jms-pool-5.16.1.jar;%APP_HOME%\lib\activemq-broker-5.16.1.jar;%APP_HOME%\lib\activemq-openwire-legacy-5.16.1.jar;%APP_HOME%\lib\activemq-client-5.16.1.jar;%APP_HOME%\lib\slf4j-api-1.7.30.jar;%APP_HOME%\lib\spring-jms-5.3.5.jar;%APP_HOME%\lib\spring-context-5.3.5.jar;%APP_HOME%\lib\spring-tx-5.3.5.jar;%APP_HOME%\lib\spring-aop-5.3.5.jar;%APP_HOME%\lib\spring-messaging-5.3.5.jar;%APP_HOME%\lib\spring-beans-5.3.5.jar;%APP_HOME%\lib\geronimo-jms_2.0_spec-1.0-alpha-2.jar;%APP_HOME%\lib\jetty-servlet-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-security-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-server-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-servlets-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-client-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-jmx-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-http-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-io-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-util-ajax-9.4.36.v20210114.jar;%APP_HOME%\lib\jetty-util-9.4.36.v20210114.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\httpclient-4.5.13.jar;%APP_HOME%\lib\jakarta.mail-1.6.6.jar;%APP_HOME%\lib\logback-core-1.2.3.jar;%APP_HOME%\lib\camel-tooling-model-3.9.0.jar;%APP_HOME%\lib\jaxb-impl-2.3.3.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\jaxb-core-2.3.0.jar;%APP_HOME%\lib\spring-expression-5.3.5.jar;%APP_HOME%\lib\spring-core-5.3.5.jar;%APP_HOME%\lib\xbean-spring-4.18.jar;%APP_HOME%\lib\geronimo-jta_1.1_spec-1.1.1.jar;%APP_HOME%\lib\commons-pool2-2.9.0.jar;%APP_HOME%\lib\jetty-continuation-9.4.36.v20210114.jar;%APP_HOME%\lib\httpcore-4.4.13.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\javax.activation-1.2.0.jar;%APP_HOME%\lib\json-smart-2.3.jar;%APP_HOME%\lib\camel-util-json-3.9.0.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.2.jar;%APP_HOME%\lib\jakarta.activation-1.2.2.jar;%APP_HOME%\lib\spring-jcl-5.3.5.jar;%APP_HOME%\lib\jackson-databind-2.9.10.8.jar;%APP_HOME%\lib\geronimo-jms_1.1_spec-1.1.1.jar;%APP_HOME%\lib\hawtbuf-1.11.jar;%APP_HOME%\lib\geronimo-j2ee-management_1.1_spec-1.0.1.jar;%APP_HOME%\lib\accessors-smart-1.2.jar;%APP_HOME%\lib\jackson-annotations-2.9.10.jar;%APP_HOME%\lib\jackson-core-2.9.10.jar;%APP_HOME%\lib\asm-5.0.4.jar


@rem Execute router
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ROUTER_OPTS%  -classpath "%CLASSPATH%" router.Router %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ROUTER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ROUTER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
