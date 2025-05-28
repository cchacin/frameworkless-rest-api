# Framework-less REST API

## Companion Videos

<iframe width="560" height="315" src="https://www.youtube.com/embed/-UVrMS43uEQ?si=WDZQBKW-0CowYUFn" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

<iframe width="560" height="315" src="https://www.youtube.com/embed/b8E3WhSWLhs?si=z_17pSS2tkDJS8M5" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

<iframe width="560" height="315" src="https://www.youtube.com/embed/j7zD7it61S0?si=_OELAw9zlWGUNrbG" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>


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