# SDE 2017 Assigment 2 Server 


**Assigment 2 Server - Mattia Buffa mattia.buffa-1@studenti.unitn.it**  
**Assigment 2 Server heroku: http://server-as2.herokuapp.com/as2/**  
**Partner Denis Gallo**
**Assigment 2 Partner Server repository: https://github.com/DenisGallo/introsde-2017-assignment-2-server**  
**Assigment 2 Partner Client repository: https://github.com/DenisGallo/introsde-2017-assignment-2-client**  
**Assigment 2 Partner Server heroku: http://assignment2-denisgallo.herokuapp.com/sdelab**  

**Table of Contents**

- [SDE 2017 Assigment 2 Server](#sde-2017-assigment-2-Server)
	- [Project Description](#project-description)
		- [Code analysis](#code-analysis)
		- [Code tasks](#code-tasks)
		- [Execution](#execution)
		- [Additional Notes](#additional-notes)

## Project Description

### Code analysis

**Resources**

All the resources (servlet) are inside the package as2.rest.resources these classes have been used to create the response using the data retrieved by DAOs. 
PersonCollectionResource identify the path: /person 
PersonResource identify the path: /person/{id} 
ActivityTypeCollectionResource identify the path: /activity_types 
ActivityCollectionResource identify the path: /person/{id}/{activity_type} 
ActivityResource identify the path: /person/{id}/{activity_type}/{activity_id} 

**Models**

All the models are inside the package as.rest.model these classes contains the JAXB and JPA annotations to define the xml and database rappresentation. 
Person and Activity have ManyToOne and OneToMany link.
ActivityType and Activity have ManyToOne and OneToMany link.

**DAOs**

All the DAOs are inside the package as.rest.dao these classes contains methods that will connect and interact with the SQLite database.
DatabaseDao is used to initialize and populate the database.

**App.java**

This class let the server to be executed as Java Application creating a local HTTPServer.

### Code tasks

**Servlet**

Servlet will redirect the request based on HTTP Protocol methods and URI.

**Functions**

The implemented functions are managed by servlets which will retrieve data using the DAOs and will marshall or unmarshall the data using the models defined.

**Build.xml**

The file build.xml contains all the task that will:  
- install, download and resolve ivy dependencies  
- compile classes  
- create war
	
### Execution

Server is on at link: http://server-as2.herokuapp.com/as2/ 

### Additional Notes

Request 11 requires dates in "yyyy-MM-dd hh-mm-ss.s" format 
  
*Bugs don't exist there are only strange features.*