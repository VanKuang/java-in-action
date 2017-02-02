# java-core
Try pure java functions  

## Design pattern

### Creational patterns
All about class instantiation, object creation, it abstract the object create process.  
Client just facing the interface, don't need care about how the object create.  
So that separate the object creation and usage.

**Including**
* Abstract Factory  
  Create a family of related objects
* Factory Method
* Builder  
  Step by step to create a complicated object
* Prototype  
  Creating object by copy from prototypes.
* Singleton  
  Only one instance can be created at the system.

### Structural patterns
About how to create a bigger, stronger system.  
Decouple between classes/objects, so that system will be easy to extend, easy to add logic functions.

**Including**
* Adapter  
  Wrap an interface to another interface for client to use the incompatible interface.
* Bridge  
  Decouple an abstraction from its implementation.
* Composite  
  Compose objects into tree structures to represent whole-part hierarchies.  
  Composite lets clients treat individual objects and compositions of objects uniformly.
* Decorator  
  Attach additional responsibility to an object dynamically.
* Facade  
  Provide a unified interface from a set of interfaces of a subsystem.
  Facade defines a high-level interface to make subsystem easy to use;
* Flyweight  
  Use sharing/caching to reuse large number of fine-grained objects
* Proxy  
  Provide a replacement for an object to control the usage of it.

### Behavioral patterns
Define the communication pattern between objects to increasing the flexibility.  
This patterns define the responsibility of classes and objects.  
It include two kinds of patterns, one is class  behavioral pattern, another is object behavioral pattern.

**Including**
* Chain of Responsibility  
  Decouple between sender and receiver, and make more than one object has chance to handle the request.
* Command  
  Encapsulate a request as an command object, and pass to the invoker to invoke it.
* Interpreter
* Iterator  
  Provide to access a collection of elements sequentially without need to know the underlying representation.
* Mediator  
  Use a object to encapsulate the interaction between a set of objects, in order to decouple those objects from referring each other explicitly.
* Memento
* Observer/Pub-Sub  
  Define a one-to-many dependency between objects, so that one object change, other object which depend on it got notified.
* State  
  The object's behavior will change when its state change.
* Strategy  
  Encapsulate each algorithm to classes, each classes are independent, and can be replace by each other.
* Template method  
  Define all steps by abstract methods, and its subclasses implement those methods base on their concrete logic.
* Visitor  
  Visitor lets you define a new operation without changing the classes of the elements on which it operates.