## Briefly summarize your client, Artemis Financial, and its software requirements. Who was the client? What issue did the company want you to address?

Artemis Financial is a consulting company that focuses on savings, retirement, investments, and insurance for individuals. The company was seeking to modernize its business to be more online and more secure. Global Rain sadly did not perform a requirement gathering phase to produce a document with the exact requirements for Artemis Financial, however, they required secure access to their server to protect their fiduciary duty to their clients.
 
## What did you do well when you found your client’s software security vulnerabilities? Why is it important to code securely? What value does software security add to a company’s overall well-being?

I was able to expose the underlying library vulnerabilities quite well with OWASP Dependency Checker, as well as my ability to keep code quality high. Secure coding means that the maintenance costs for the software are reduced, and since software maintenance is 60%-80% of a budget (Kaplan, 2002 as cited in Valacich et al., 2020, p. 475), this is an important metric to try to lower as much as possible. For Artemis Financial, secure software means lower insurance premiums and less downtime for maintenance, increasing their profit margins.

## Which part of the vulnerability assessment was challenging or helpful to you?

I found that trying to update the versions of each vulnerable component on a per-component basis was the most challenging endeavor. What was most helpful and made it far easier to counter this issue was to update only the main module that the project depended on to its highest version, and then work from there. Often, this would eliminate >90% of the identified vulnerabilities in the dependencies. Moving forward, I will focus on the main module as a priority rather than trying up update everything at once.

## How did you increase layers of security? In the future, what would you use to assess vulnerabilities and decide which mitigation techniques to use?

I increased the layers of security by using cryptographically secure functions with high bit levels in every area possible. Rather than use a simple RSA encryption for my keys, I chose one that is far quicker with ECDSA as RSA needs 3072-bit keys whereas ECDSA needs 256-bit keys for a 128-bit security level (Grigutytė, 2024). By making sure to handle errors, my code is less exploitable by bad actors, and by logging these errors we can more readily perform defensive measures if a bad actor is trying to use these errors to prod for vulnerabilities. Code quality was a major focus of mine, including asymptotic analysis of the algorithms of my code, showing how fast the code will run. This is incredibly important in multithreaded environments, where race conditions can be statically debugged by seeing that one function might be Ω(n) while the other is Ω(log n), letting us know that the second function may have raced ahead if they run normally as θ(n) and θ(n log n) respectively, where the latter function is typically slower but on edge cases may be faster.

In the future, I will more accurately follow the model that SNHU provided us using the Vulnerability Assessment Process Flow. It provides a framework that can accurately assess the problems that your code faces, as well as what steps should be taken for each type of problem.

## How did you make certain the code and software application were functional and secure? After refactoring the code, how did you check to see whether you introduced new vulnerabilities?

I made sure that the code was functional and secure by continually testing the code as I made updates or refactors. This was to alert me to any new issue with the program where something may have gone overlooked or was implemented incorrectly. Furthermore, after every successful build, I ran the OWASP Dependency Checker again to make sure that I was still getting the report I was expecting. I also made sure to follow the logical flow of the program to see if there were any unhandled exceptions or any inputs or events that may trigger undefined behavior.

What I would like to highlight is that I forced the usage of Transport Layer Security (TLS) version 1.3 using TLS_AES_256_GCM_SHA384. The explanation of why this is significant is far too long to fit here but suffice it to say that it is a significantly more secure way of connecting machines together over the internet. More information on the protocol can be found in my [7-1 Project Two writeup under the Mod7 folder](https://github.com/Kubia-Beta/Software-Security-CS305/blob/main/Mod7/7-1%20Project%20Two%20-%20Connor%20Sculthorpe.docx).

I also wanted to delve into certificate pinning and the double-cookie solution to Cross-Site Request Forgery (CSRF) attacks, Certificate Authority (CA) compromises, and Man-in-the-middle (MITM) attacks. However, these are out of the scope of the project, and so were put on hold. I have opened them up as features I want to complete in my personal time to better learn how these defenses work. I did begin the Certificate Pinning scaffolding, but it is merely a scaffold.

## What resources, tools, or coding practices did you use that might be helpful in future assignments or tasks?

OWASP is chock full of tools and assessment features that are invaluable, not just the Dependency Checker. NIST also offers a section for Cybersecurity that also includes frameworks for cybersecurity standards, privacy, and risk management that are immensely helpful to understanding the field (Information Technology Laboratory, 2021). Sticking to using unit testing frameworks will also be immensely helpful, as knowing you have 100% code coverage provides peace of mind that is hard to match.

## Employers sometimes ask for examples of work that you have successfully completed to show your skills, knowledge, and experience. What might you show future employers from this assignment?

I want to show that my knowledge of cryptography is rather strong and that I have a mindset that is fit for thinking ahead of the problems for a business by considering how to integrate security into the workflow of how I code even if it is not a position where security is a high priority. Many businesses consider security to be a dead last priority, so bringing in security knowledge and explaining how that security knowledge can increase their revenue for a trivial increase in workload by following positive patterns can show how you can be a vital asset for their company. It is not just that I would be bringing my knowledge of coding to them, but rather my knowledge of how to think about what makes the business more successful while I am designing my code.

### References

Grigutytė, M. (2024, April 18). _RSA vs. ECDSA: What are the differences?._ NordVPN. https://nordvpn.com/blog/ecdsa-vs-rsa/ 

Information Technology Laboratory (2021, November). _NIST CYBERSECURITY & PRIVACY PROGRAM_. U.S. Department of Commerce, National Institute of Standards and Technology. https://www.nist.gov/system/files/documents/2021/11/09/Cybersecurity%20Vitals%20Fact%20Sheet.pdf 

Valacich, J. S., George, J. F., & Hoffer, J. A. (2020). _Modern Systems Analysis and Design_. Pearson.

