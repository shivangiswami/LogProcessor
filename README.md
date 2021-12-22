# LogProcessor

About the project :
This custom-build server logs different events to a file named logfile.txt. Every event has 2 entries in the file 
- one entry when the event was started and another when the event was finished. The entries in the file have 
no specific order (a finish event could occur before a start event for a given id) Every line in the file is a 
JSON object containing the data. 
This processor take the logfile as input, calculate the execution time of events and trigger alert if the execution time is greater than 4 seconds

Steps to execute :

1. Checkout the project to your local repository
2. Run mvn clean install to download all the dependencies mentioned in pom.xml file
3. Run testng.xml file given inside /suite folder
4. Output are printed over console logs as well as written in output file created inside resources folder

Input :
LogFile "test" kept in resources folder



