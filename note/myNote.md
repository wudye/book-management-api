config 
    computer using docker compose
database
    user class
    table in database to create through jpa
    table name with annotation entity  implements userdetails
    primary key GeneratedValue
    ManyToMany OneToMany ManyToOne (FetchType, mappedBy)
Spring Security:
    GrantedAuthority in User
    httpfilterchain config 
        only permit allowed sites
        others need authentication
        use authenticationProvider
        the config and excute order are different in spring security
    config the authentication with @Bean also with passwordEncode because DaoAuthenticationProvider need
    from header get token compare with password right 
        the token generate and valid check
 
12.11.2024
    springboot + angular + docker
    from youtube Bouali Ali
    the source code in https://github.com/ali-bouali/book-social-network
    book-social network
    use https://start.spring.io/ to initialise the project
        java version23
    implement jwt
    use docker
        install debian for windows
13.11.2024
    config postgresql in applicaton.yml
        like myql
    use debian virtual machine set docker 
        use the provided docker compose file
    table to create
        when create User class using UserDetails and principal from package org.springframework.security.core ;
            UserDetails is returned by the UserDetailsService. The DaoAuthenticationProvider validates the 
            UserDetails and then returns an Authentication that has a principal that is the UserDetails returned by the configured UserDetailsService.
            A Principal represents a user's identity.
            It can be a String object having username on a simple level or a complex UserDetails object.
        lombok @builder to create instances of our class.he use of the builder requires a default constructor. When you added the @AllArgsConstructor annotation
            @Entities in JPA are nothing but POJOs representing data that can be persisted in the database. An entity represents 
            a table stored in a database. Every instance of an entity represents a row in the table.
           @EntityListeners(AuditingEntityListener.class)  @EntityListeners should be used for repository method save,delete, update with @PreUpdate , @PreRemove and so on..
            https://www.baeldung.com/jpa-entity-lifecycle-events
            @Table(), the JPA annotation is used for adding the table name in the particular MySQL database
            @Id annotation is inherited from javax.persistence.Id, indicating the member field below is the primary key of the current entity. 
            @GeneratedValue automatically generate unique values for primary key columns within our database tables.
        LocalDateTime vs LocalDate
            LocalDate is an immutable date-time object that represents a date, often viewed as year-month-day. Other date fields, such as day-of-year, day-of-week and week-of-year, can also be accessed. 
            LocalDateTime class in Java is an immutable date-time object that represents a date in the yyyy-MM-dd-HH-mm-ss. zzz format.
             It implements the ChronoLocalDateTime interface and inherits the object class. Wherever we need to represent time without a timezone reference, we can use the LocalDateTime instances.
            @LastModifiedDate annotation is used in Spring Boot to automatically capture the date and time when an entity was last updated.
14.11.2024
            @ManyToMany Eager Fetching ( FetchType. EAGER ): Related entities are loaded immediately along with the parent entity. Lazy Fetching ( FetchType. LAZY ): Related entities are loaded only when accessed 
            @JsonIgnore is used to ignore the logical property used in serialization and deserialization.
         Spring Security
            @Overide
             public Collection<? extends GrantedAuthority> getAuthorities() {
                return this.roles
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());
            }
            the privilege getting from role name

            @JoinColumn
            If the join is for a OneToOne or ManyToOne mapping using a foreign key mapping strategy, the foreign key column is in the table of the source entity or embeddable.
            If the join is for a unidirectional OneToMany mapping using a foreign key mapping strategy, the foreign key is in the table of the target entity.
            If the join is for a ManyToMany mapping or for a OneToOne or bidirectional ManyToOne/OneToMany mapping using a join table, the foreign key is in a join table.
            If the join is for an element collection, the foreign key is in a collection table.
            @Configuration is a class-level annotation indicating that an object is a source of bean definitions. 
            @Configuration classes declare beans through @Bean-annotated methods. Calls to @Bean methods on @Configuration classes can also be used to define inter-bean dependencies. 
            See Basic Concepts: @Bean and @Configuration for a general introduction.
            .anyRequest().authenticated() means any other request (besides "/") requires authentication, restricting access to authenticated users only.

            Custom Authentication Provider:
                authenticationProvider(authenticationProvider) sets a custom AuthenticationProvider in the Spring Security configuration.
                An AuthenticationProvider is responsible for handling the actual authentication logic. By configuring a custom provider, you can control how credentials are validated (e.g., by verifying a JWT, querying a database, or integrating with an external authentication service).
                This is helpful if you have specific requirements for authentication that aren’t covered by Spring Security’s default providers.
            In Spring Security, an AuthenticationProvider is an interface responsible for handling authentication logic. It processes user credentials (such as username/password or tokens) and verifies them to authenticate the user. AuthenticationProvider implementations can be customized to support different authentication mechanisms beyond the default username/password.
            1. authenticate(Authentication authentication)
                This method performs the actual authentication logic.
                It takes an Authentication object as input, which holds the user’s credentials, and checks if they’re valid.
                If the credentials are correct, it returns an authenticated Authentication object, usually containing the user's authorities (roles or permissions).
                If authentication fails, it throws an AuthenticationException.
        excute order
            CORS → 2. CSRF (skipped, as it’s disabled) → 
            3. Authentication Filters (jwtAuthFilter first) → 4. Authorization Rules → 5. Session Management (stateless)
        config order
            CORS Configuration: .cors(Customizer.withDefaults())
            CSRF Configuration: .csrf(AbstractHttpConfigurer::disable)
            Authorization Rules: .authorizeHttpRequests(...)
            Session Management: .sessionManagement(...)
            Authentication Provider: .authenticationProvider(authenticationProvider)
            Custom Filter: .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            JWT Filter: The custom JWT filter validates the token first and sets the authentication context in SecurityContextHolder.
            Spring Security Filters: Once the context is set, Spring Security bypasses the UsernamePasswordAuthenticationFilter because the user is already authenticated.
            Authorization Filters: The authorization filters ensure the user has the appropriate permissions to access the resource.
19-11-2024
    controller
        Authentication controller
            @Valid to make a long story short, in your particular example code, ScriptFile class has some constraints defined for the class data members like
             @NotNull, @NotEmpty, etc. and @Valid instructs the framework (spring in our case) to check these constraints 
             against the parameter supplied when someone calls your method. In this case, if the validation fails, the server responds with an HTTP 400 Bad Request status code.



problem JwtFilter injection failed
2:58