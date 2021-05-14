### Sound Synthesis

### 1. HW Purpose

Create **synthesizer package** to simulate the sound of instruments.

Learn how to write and use **packages**, as well as get some hands-on practice with **interfaces** and **abstract classes.** 

Implement a **simple data structure** as well as an **algorithm** that’s easy to implement using that data structure. 

Add support for **iteration** and **exceptions** to our data structure.



### 2. Package Structure

**synthesizer package** contains following parts:

- `BoundedQueue`, an interface which declares all the methods that must be implemented by any class that implements `BoundedQueue`.
- `AbstractBoundedQueue`, an abstract class which implements `BoundedQueue`, capturing the redundancies between methods in `BoundedQueue`.
- `ArrayRingBuffer`, a class which extends `AbstractBoundedQueue` and uses an array as the actual implementation of the `BoundedQueue`.
- `GuitarString`, which uses an `ArrayRingBuffer<Double>` to implement the Karplus-Strong algorithm to synthesize a guitar string sound.



### 3. Task 1

Define an interface for `BoundedQueue`, it contains following method to be implemented:

```java
int capacity();     // return size of the buffer
int fillCount();    // return number of items currently in the buffer
void enqueue(T x);  // add item x to the end
T dequeue();        // delete and return item from the front
T peek();           // return (but do not delete) item from the front
// is the buffer empty (fillCount equals zero)?
default boolean isEmpty() { return fillCount() == 0;}
// is the buffer full (fillCount is same as capacity)?
default boolean isFull() { return fillCount() == capacity();}
```



### 4. Task 2

Create a new abstract class `AbstractBoundedQueue` that implements `BoundedQueue`, which has the following methods:

```java
protected int fillCount;
protected int capacity;
public int capacity() { return capacity; }
public int fillCount() { return fillCount; }
public boolean isEmpty() { return fillCount() == 0; }
public boolean isFull() { return fillCount() == capacity; }
public abstract T peek();
public abstract T dequeue();
public abstract void enqueue(T x);
```



### 5. Task 3

Achieve a queue implemented by array ring buffer.

In this work, a variable `first` stores the index of the least recently inserted item, which point to the item that will be removed or peeked;  `last` stores the index one beyond the most recently inserted item, which point to the position that a new item will be placed in.

`peek()`, `dequeue()`, `enqueue()` are implemented in this task. 



### 6. Task 4

Using the Karplus-Strong algorithm to replicate the sound of a plucked string. 

Write `GuitarString` class, which uses an `ArrayRingBuffer` .



### 7. Task 5

Realize iterator.



### What did I learn?

- Why interface is necessary?

  An interface is a formal contract between a class and the outside world. This is a way of enforcing promised behavior. 



- What's the difference between Interface and Abstract Class?
  - Class uses <u>implements</u> to inherit Interface, uses <u>extends</u> to inherit Abstract Class
  - Normally, Interface only declares <u>what methods should to be implemented</u> in the class but not give the code to implement, whereas the Abstract Class  write <u>abstract method with implementation</u>. Both of them <u>can not be instantiated</u>.



- Why Abstract Class is necessary?

  ?



- When to use an Interface Versus an Abstract Class?

  In CS61B,  Josh Hug give us a metaphor. 

  An interface as defining a “can-do” or an “is-a” relationship, whereas an abstract class should be a stricter “is-a” relationship.

  In fact, if the project is not big enough, the difference is subtle.

