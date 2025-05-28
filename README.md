# Framework-less REST API

## Companion Videos

[![Part 1](https://img.youtube.com/vi/-UVrMS43uEQ/0.jpg)](https://youtu.be/-UVrMS43uEQ?si=3A9zNcuWjEXTlXmQ)
<br />
[![Part 2](https://img.youtube.com/vi/b8E3WhSWLhs/0.jpg)](https://youtu.be/b8E3WhSWLhs?si=CWsnSYs0E99pRjob)
<br />
[![Part 3](https://img.youtube.com/vi/j7zD7it61S0/0.jpg)](https://youtu.be/j7zD7it61S0?si=X55s3m1bqxO3spyR)
<br />


## Components / Libraries

- Servlets
- JDBC + HikariCP
- Jackson

```
 ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ 
  Servlet Container                                                                   │
 │-----------------             ┌───────────────┐                                      
  Jetty                         │ Serialization │                                     │
 │                              ├───────────────┤                                      
                                │     JSON      │                                     │
 │        ┌───────────────┐     │    -------    │◀─────┐                               
          │     REST      │     │    Jackson    │      │                              │
 │        ├───────────────┤     └───────────────┘      │                               
          │   API Layer   │                            │                              │
 │        │   --------    │◀────────────────┐          │                               
          │   Servlets    │                 │          │                              │
 │        └───────────────┘                 │          │                               
                                            │          │                              │
 │                                          │          │                               
                                            │          │                              │
 │        ┌───────────────┐                 │          │       ┌───────────────┐       
          │   Core DTOs   │                 │          │       │     Main      │      │
 │        ├───────────────┤                 │          │       ├───────────────┤       
          │ Service Layer │                 │          │       │   App Layer   │      │
 │        │ ------------- │◀────────────────┼──────────┴───────│ ------------- │       
          │  Plain Java   │                 │                  │  Plain Java   │      │
 │        └───────────────┘                 │                  └───────────────┘       
                                            │                                         │
 │                                          │                                          
                                            │                                         │
 │        ┌───────────────┐                 │                                          
          │    DB DTOs    │                 │                                         │
 │        ├───────────────┤                 │                                          
          │  Data Layer   │                 │                                         │
 │        │---------------│◀────────────────┘                                          
          │JDBC / Postgres│                                                           │
 │        └───────────────┘                                                            
                  │                                                                   │
 │                │                                                                    
                  │                                                                   │
 │                │                                                                    
  ─ ─ ─ ─ ─ ─ ─ ─ ┼ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
                  │                                                                    
                  └────────────────────────┐                                           
                                           │                                           
                                           │                                           
                                           │                                           
                                           │                                           
                                           │                                           
                                           │                                           
                                           ▼                                           
┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─  
 DATABASE                                                                            │ 
│----------                                                                            
 PostgreSQL                                                                          │ 
│                                                                                      
                                                                                     │ 
│                                                                                      
                                                                                     │ 
│                                                                                      
                                                                                     │ 
│                                                                                      
                                                                                     │ 
└ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─  
```