# kotlin-spring-data-demo
Sample application using Kotlin, Spring Boot and Spring Data JPA
 
# What is Kotlin?
Kotlin is a statically typed JVM language created by JetBrains, the company behind IntelliJ. In May 17, 2017 Google announced first-class support for Kotlin and the new Spring Framework 5.0 also introduced support for Kotlin.
 
# Building the web application
## Configuring pom.xml
An easy way to configure the pom.xml for Kotlin is to use https://start.spring.io/ to generate the skeleton of the application. On the Spring Starter webpage, select Kotlin as language, add Web and JPA as a dependency and click the Generate Project button. It will start downloading a zip file with the source code inside.
 
Extract the zip file to a new folder and let's open the project in IntelliJ (to reward JetBrains for coming up with Kotlin).
 
In the pom.xml file you'll find the usual Spring Boot dependencies and also a couple of other ones needed for Kotlin:

```
<dependency>  
   <groupId>org.jetbrains.kotlin</groupId>  
   <artifactId>kotlin-stdlib-jre8</artifactId>  
   <version>${kotlin.version}</version>  
</dependency>  
<dependency>  
   <groupId>org.jetbrains.kotlin</groupId>  
   <artifactId>kotlin-reflect</artifactId>  
   <version>${kotlin.version}</version>  
</dependency>  
```

## Source folder
If you used Spring's code generator tool, you'll notice that instead of ```src/main/java```, the source code is now under ```src/main/kotlin```.
 
Let's create a JPA entity class now.
 
## User entity
In this sample application we're going to create a simple User entity with id, name and salary fields. Here's how that looks in Kotlin:
```
package com.example.demo.entity  
  
import javax.persistence.Entity  
import javax.persistence.GeneratedValue  
import javax.persistence.GenerationType  
import javax.persistence.Id  
  
@Entity  
data class User(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,  
            val name: String = "",  
            val salary: Int = 2000)  
```

Kotlin has the concept of ```data class``` which is the equivalent of POJO in Java. We can declare the list of fields within the bracket with their type and optionally their default value.
 
The ```var``` or ```val``` denotes mutable or immutable fields and as you can see, Kotlin is still happy when we use the JPA annotations from Java.
 
## UserRepository
To be able to work with JPA entities, we'll be creating a Spring Data JPA repository to make our life easier. Let's take a look at the UserRepository interface:
```
package com.example.demo.repo  
  
import com.example.demo.entity.User  
import org.springframework.data.jpa.repository.JpaRepository  
  
interface UserRepository : JpaRepository<User, Long>  
```

As you can see, the Kotlin code is almost the same as its Java equivalent, except it uses ```:``` instead of ```extends``` on line 6.
 
## REST api
Now we have everything in place to create our very own Kotlin-based REST api:
```
package com.example.demo.web  
  
import com.example.demo.entity.User  
import com.example.demo.repo.UserRepository  
import org.springframework.web.bind.annotation.GetMapping  
import org.springframework.web.bind.annotation.RequestMapping  
import org.springframework.web.bind.annotation.RestController  
  
@RestController  
@RequestMapping("/api/users")  
class UserApi (val userRepository: UserRepository) {  
    @GetMapping("/all")  
    fun getAll() : List<User> = userRepository.findAll()  
}  
```

The ```UserApi``` class was created under the ```com.example.demo.web``` package and is annotated with the same Spring annotation that we'd use in a Java-based Spring MVC controller class.
 
The Spring dependency inject in Kotlin is very simple, similar to data classes we discussed earlier, we just need to declare the dependencies (userRepository) within the bracket in the class declaration and Spring will automatically take care of injecting them.
 
In Kotlin methods (functions) are prefixed with the ```fun``` keyword, because working with Kotlin is fun! :)
 
One of the interesting things in Kotlin is that if the function's body is a simple expression like with ```getAll()```, we can write the expression after the ```=``` sign.
 
## Bootstraping the application
Let's look at the Kotlin version of the Application class which is needed to bootstrap Spring Boot on application startup. Inside the Kotlin source folder we have the ```com.example.demo.DemoApplication.kt``` file created by the code generation tool.
 
We're going to modify it slightly to load some sample data into the database that we can query in our REST api:
```
package com.example.demo  
  
import com.example.demo.entity.User  
import com.example.demo.repo.UserRepository  
import org.springframework.boot.CommandLineRunner  
import org.springframework.boot.SpringApplication  
import org.springframework.boot.autoconfigure.SpringBootApplication  
import org.springframework.boot.builder.SpringApplicationBuilder  
import org.springframework.boot.web.support.SpringBootServletInitializer  
import org.springframework.context.annotation.Bean  
  
@SpringBootApplication  
class DemoApplication : SpringBootServletInitializer() {  
    @Bean  
    fun init(repository: UserRepository) = CommandLineRunner {  
        repository.save(User(name = "Jack", salary = 1000))  
        repository.save(User(name = "Chloe", salary = 2000))  
        repository.save(User(name = "Kim", salary = 3000))  
        repository.save(User(name = "David", salary = 4000))  
        repository.save(User(name = "Michelle", salary = 5000))  
    }  
  
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder
        = application.sources(Application::class.java)  
}  
  
fun main(args: Array<String>) {  
    SpringApplication.run(Application::class.java, *args)  
}  
```

The ```init()``` function will do the heavy-lifting to populate the database using Spring Boot's ```CommandLineRunner``` interface. Kotlin doesn't need the new keyword to create a new instance of an object and also has the concept of named parameters (ie: ```User(name = "Jack", salary = 1000)```). When using named parameters Kotlin has the option to only provide a subset of the parameters while the rest will be assigned with their default values. Actually this is the equivalent of method overloading in Java.
 
The ```configure()``` function needs the ```override``` keyword to override the same method in the parent class.
 
As for the ```main()``` function, it's declared outside the ```DemoApplication``` class as a stand-alone function. This is one way in Kotlin to create static functions (as required by the JVM for the ```main()``` method).
 
## Conclusion
In this post I covered how to get started with Kotlin and Spring Boot.
 
To test the code, run the ```main()``` method in ```DemoApplication``` class and open http://localhost:8080/api/users/all in your browser to get the list of users from the database.
 
If you're interested learning more about Kotlin, make sure to check out the https://kotlinlang.org website.
