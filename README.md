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
For some reason the order of elements in XML changed in CA APM 9.7. I updated the code to take the new output so this will not work with versions previous to 9.7. This shouldn't be a problem as 9.7 and previous releases are not supported anymore.

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

## Support
This document and associated tools are made available from CA Technologies as examples and provided at no charge as a courtesy to the CA APM Community at large. This resource may require modification for use in your environment. However, please note that this resource is not supported by CA Technologies, and inclusion in this site should not be construed to be an endorsement or recommendation by CA Technologies. These utilities are not covered by the CA Technologies software license agreement and there is no explicit or implied warranty from CA Technologies. They can be used and distributed freely amongst the CA APM Community, but not sold. As such, they are unsupported software, provided as is without warranty of any kind, express or implied, including but not limited to warranties of merchantability and fitness for a particular purpose. CA Technologies does not warrant that this resource will meet your requirements or that the operation of the resource will be uninterrupted or error free or that any defects will be corrected. The use of this resource implies that you understand and agree to the terms listed herein.

Although these utilities are unsupported, please let us know if you have any problems or questions by adding a comment to the CA APM Community Site area where the resource is located, so that the Author(s) may attempt to address the issue or question.

Unless explicitly stated otherwise this field pack is only supported on the same platforms as the APM core agent. See [APM Compatibility Guide](http://www.ca.com/us/support/ca-support-online/product-content/status/compatibility-matrix/application-performance-management-compatibility-guide.aspx).


# Contributing
The [CA APM Community](https://communities.ca.com/community/ca-apm) is the primary means of interfacing with other users and with the CA APM product team.  The [developer subcommunity](https://communities.ca.com/community/ca-apm/ca-developer-apm) is where you can learn more about building APM-based assets, find code examples, and ask questions of other developers and the CA APM product team.

If you wish to contribute to this or any other project, please refer to [easy instructions](https://communities.ca.com/docs/DOC-231150910) available on the CA APM Developer Community.


# Change log
Changes for each version of the field pack.

Version | Author | Comment
--------|--------|--------
1.2 | Jonathan Faldmo | First version of the field pack.
