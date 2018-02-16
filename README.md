Software requirements to test the code
-----------------------------------------
The coding challenge is implemented using Java language.
To run the code, you need java 8 and Maven 3.5 installed on the system.

Ensure JAVA_HOME environment variable is set and points to your JDK installation
Download  maven from https://maven.apache.org/download.cgi
Extract distribution archive in any directory
Add the bin directory of the created directory apache-maven-3.5.2 to the PATH environment variable
confirm with mvn -v in a new shell. 

Once we have java and Maven, we can just run the script run.sh. 
Modified run_tests.h and added the below line in the function setup_testing_input_output

 cp -r ${PROJECT_PATH}/pom.xml ${TEST_OUTPUT_PATH}
 
Unit Tests
-----------
JUnit tests are in the test folder- TestUtilClass.java

Tests
------
2 test folders are created under insighttestsuite folder. I could not upload big test files from FEC as Github
is not allowing to upload big input files.


Coding Approach
--------------
I have created a HashMap(Map<Donor, List<DonorContribution>> donors) to store all the donors and their contributions.
There is another hashmap (Map<String, List<Donor>> recipient_repeat_donor_map) to store all the recipients and their repeat donors.

for every valid record that is being processed,  the donor and contribution info is saved in hashmap.
if the donor is a repeat donor,donor and recipient info are saved in the repeat_donor_map.
if repeat donor, the output values along with percentile value are calculated for that particular year and zipcode 
and written to the output file repeat_donors.txt



