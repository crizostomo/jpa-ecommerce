# JPA - Specialist

In order to generate the metaModel classes in the target.generated-sources.annotations:
 - It was necessary to add the following in the pom.xml since this project is using Spring Boot 3.0 
``
   <dependency>
   <groupId>jakarta.xml.bind</groupId>
   <artifactId>jakarta.xml.bind-api</artifactId>
   <version>4.0.0</version>
   </dependency>

   	<!-- Runtime, com.sun.xml.bind module -->
   	<dependency>
   		<groupId>org.glassfish.jaxb</groupId>
   		<artifactId>jaxb-runtime</artifactId>
   		<version>4.0.2</version>
   	</dependency>
``

