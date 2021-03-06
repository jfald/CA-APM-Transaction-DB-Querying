# CA-APM-Transaction-DB-Querying (1.2)
Query the CA APM SmartStor or Event Database using an Event query, and kickout the information into a CSV file.
This was initially developed a few years ago and is just now being shared. A zip file with the jar and example script has been released.

# Description
This program is like the "Query History Event" window in Workstation. It was designed to get around the limit of returning 500 results per query. 500 results is very limited when looking over large time frames. The number of results will depend on what you set in the configuration file. The default is set to a 10 minute period.

This tool will output a text file. Each line represents an event in the following format:
Server,Process,Time,URL or Method,Metric Path,Error1,Error2

If the last two errors are equal then "Error2" is empty.


## Releases
The initial release is version 1.2. Using the information in in the Description above should get you started.

## APM version
This is known to work with CA APM 9.7 and 10.x
For some reason the order of elements in XML changed in CA APM 9.7. I updated the code to take the new output so this will not work with versions previous to 9.7. This shouldn't be a problem as version prior to 9.7 are not supported by CA anymore.

## Limitations
There is more information than just the errors in the transaction DB. This just extracts error information.

## License
MIT license. See license included in this Repo

# Installation Instructions

## Dependencies
This tool is dependent on the IntroscopeJDBC.jar file. That file is not included here since it is proprietary to CA.

## Installation
Download and extract the zip file into your working directory. Copy the IntroscopeJDBC.jar file that is included with your EM to the tdbc-1.2_lib directory so that it is in the classpath. There is a META-INF/MANIFEST.MF file in the jar file that will indicate the class-path. Alternatively you can update that file to indicate where to find the IntroscopeJDBC.jar file.

## Configuration
The config.properties contains file the connection information to your MOM. You will need to extract the config.properties file and update the values based on your environment and needs. You can also update default settings if you won't want to include them in the command line as java arguments. Here is an example on how to update the file.

Extract the file with the following command-- jar xf tdbq.jar config.properties
Make updates to the extracted file and make sure you save it. 
Save the changes back into the jar file with-- jar uf tdbq.jar config.properties

# Usage Instructions
Test your query in the \"Query History Event\" window before using it with this tool to make sure the query contains the data you want.

You can run this by using command line arguments---
java -jar tdbq-1.2.jar [start date] [start hour] [end date] [end hour] [\"query\"]
Example usage: java -jar tdbq-1.2.jar 12/05/13 08:00 12/05/13 10:00 \"type:errorsnapshot AND host:myserver\"

If you don't use arguments then the "previousHours" from the config.properties file will be used.
If you just use one argument (no matter what you put in) then a "usage" message will be displayed.

An example script on how to run this from a cronjob and have the results mailed out has been included.

## Debugging and Troubleshooting
You won't have to debug or troubleshoot anything because this tool always works. Just kidding. Finding the IntroscopeJDBC.jar file, using a proper query are common problems. I'm not sure what else to add that wasn't included above.

# Change log
Changes for each version of the field pack.

Version | Author | Comment
--------|--------|--------
1.2 | Jonathan Faldmo | First version of the field pack.
