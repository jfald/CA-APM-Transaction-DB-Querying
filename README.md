# CA-APM-Transaction-DB-Querying
Query the CA APM SmartStor or Event Database using an Event query, and kickout the information into a CSV file.

This was initially developed a few years ago and is just now being shared. I will release a .jar file soon. 


## Readme.txt for tdbq.jar file
(This hasn't been released publicly yet.)

This program is like the "Query History Event" window in Workstation. It was 
designed to get around the limit of returning 500 results per query. 500 results 
is very limited when looking over large time frames. The number of results will 
depend on what you set in the configuration file. The default is set to a 10 minute period.

Test your query in the \"Query History Event\" window before using it with this tool. 

This tool will output a text file. Each line represents an event in the following format:
Server,Process,Time,URL or Method,Metric Path,Error1,Error2

If the last two errors are equal then "Error2" is empty.

The IntroscopeJDBC.jar file should be in the Classpath. There is a META-INF/MANIFEST.MF file in the jar file that will indicate the class-path.  

## Compatibility
This is known to work with CA APM 9.7 and 10
For some reason the order of elements in XML changed in CA APM 9.7. I updated the code to take the new output so this will not work with versions previous to 9.7.  

------------
Usage: You can run this by using command line arguments---
java -jar tdbq.jar [start date] [start hour] [end date] [end hour] [\"query\"]
Example usage: java -jar tdbq.jar 12/05/13 08:00 12/05/13 10:00 \"type:errorsnapshot AND host:myserver\"

If you don't use arguments then the "previousHours" from the config.properties file will be used.
If you just use one argument (no matter what you put in) then a "usage" message will be displayed.

------------
How to update the config.properties file--
You will need to extract the config.properties file and update the values based on your environment and needs.
Extract the file with the following command-- jar xf tdbq.jar config.properties
Make updates to the extracted file and make sure you save it. 
Save the changes back into the jar file with-- jar uf tdbq.jar config.properties

-------------
See the example script on how to run this from a cronjob and have the results mailed out.
