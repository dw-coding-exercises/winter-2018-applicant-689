I didn't get quite as far as I'd like and there are several improvements that I
would like to make if I had more time. Here are my top 3:

1. Add better error handling throughout.
   I basically only implemented the happy path. The validation should be built
   out to be more robust. Adding specs for the user input and also for the
   pipeline functions would help catch a lot of bugs up front with generative
   testing. Also, depending on how this service is used, it might be useful to
   make the api queries asynchronous and handle failure more gracefully.

2. Make the query-building more robust.
   I think this may be a good place to use some multimethods for cleaner, more
   extensible code.

3. Make the service actually useful to end-users.
   There's a lot of useful data in the turbovote response that I didn't use in my
   interface. I didn't have time to make an actual useful service, but there's a lot
   of low-hanging fruit there.