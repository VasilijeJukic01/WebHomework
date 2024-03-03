# Concurrent programming in Java
N students are attending the defense of a homework assignment. <br>
The defense is conducted by a professor and an assistant. The assistant can only review the work of one student at a time, while the professor is capable of overseeing only two defenses simultaneously (they do not want to conduct a defense for just one student). <br>

We will simulate the defense with separate threads - one for the assistant and two for the professor. Defenses can begin the moment both the assistant's and the professor's threads are ready to work. The professor and assistant are available for the next 5 seconds from the start, after which defenses are no longer possible. <br>

Each student arrives for defense at a random time - a random value within the range of 0 < x <= 1 second from the start of the defense. Each student defends the assignment at their own pace - a random value within the range of 0.5 <= X <= 1 second, after which they receive a grade and finish. Since defenses last 5 seconds, a defense that has started must be interrupted the moment 5 seconds expire. <br>

The professor refuses to review the work of just one student and will wait for two students ready to defend before commencing the review of both students simultaneously, not finishing until both are done.
The assistant begins reviewing the work as soon as a student is ready for defense. <br>

The probability that a student will defend their work with the professor or the assistant should be user-defined (e.g., 50% chance to defend with the professor and 50% chance to defend with the assistant). <br>

After completing their defense, each student receives a grade between 5 and 10. The received grades should be added to the sum of all students' grades. After the program finishes (after 5 seconds allocated for defense have elapsed), this sum will be divided by the number of students, and the resulting average should be printed to the console. This average must be accurate regardless of the number of students.
It should not happen that the same student defends the homework twice. It should not happen that a student defends the homework after the time allocated for defenses has expired (5 seconds), even if they started before the end. It should not happen that the professor and assistant review the work of the same student. <br>

The input parameter of the system is N, i.e., the number of students. <br>

The system's output should be printed in the following format for all students who have arrived or have been interrupted during the defense: <br>
```Thread: <Student's thread name> Arrival: <Time of student's arrival from the start of defense> Prof: <Name of assistant's or professor's thread> TTC: <Time taken to review the homework>:<time of defense initiation> Score: <Grade received, ranging from 5 to 10>``` <br>
Use any type of thread pool for thread creation (except singleThreadPool). Implement a cyclicBarrier for the professor when waiting for two students to begin defense with him.