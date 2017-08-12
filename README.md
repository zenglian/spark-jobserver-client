## spark-jobserver-client
### Backgroud
People always use curl or HUE to upload jar and run spark job in Spark Job Server.
But the Spark Job Server official only presents the rest apis to upload job jar and 
run a job, doesn't give client lib with any language implementation.

Now there is another option to communicate with spark job server in Java, that is spark-jobserver-client, the Java Client of the Spark Job Server implementing the arranged Rest APIs.

spark-jobserver-client is a open-source program under Apache License v2. It aims to make the java applications use the spark more easily.

### How to build

**Note: you need install lombok before compiling it in eclipse or other IDEs**

You can execute the following commands to compile this client:
```shell
git clone git@github.com:leonsoft/spark-jobserver-client.git
cd spark-jobserver-client
mvn clean package
```
Then you can find `spark-jobserver-client-1.0.jar`in spark-jobserver-client/target, it is the main jar of spark-jobserver-client. 

You can download the jar file release 1.0 too.

### How to use
See examples in JobServerClientTest.java

## changes
This repo is forked from bluebreezecf/SparkJobServerClient.  

**Main changes:**  

Simplify package, class, method names.  
Simplify serialization/deserialization with gson.  
Remove unncessary commetns as the code is self-explanatory.  
Remove author info from javadoc etc.  
Use lombok.  
Use java 8.  
Added some tests.     


