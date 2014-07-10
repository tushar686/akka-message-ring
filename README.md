akka-message-ring
=================
This is simple project to demonstrate an idea that through remote akka we can send message to root akka actor and it can 
float this message through the ring of other remote akka actors, finally message comes back to root akka actor indicating
ring completion i.e. message sent by akka-client flows through the ring of machines and the returns back to the root server


akka-client
-----------
This is starting point to inject a message into ring.


akka-server
-----------
This contains akka actors which can look up other remote actors.

The akka-server containt two types of actors

1> ServerWithRingInfo : This server contains the information about next akka-server's IP:port  in the ring. The information
is configured in resources/application.conf
sample message: "message"

2> ServerWithoutRingInfo: This server expects message to contain ring information.
sample message: "message root10.198.80.56:2551, 2-10.198.80.56:2552, 3-10.198.80.56:2553, 4-10.198.80.56:2554"
