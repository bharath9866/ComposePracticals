# Q1. What is an Object ?
Object is a runtime instance of a class. It is a fundamental concept of object-oriented programming.
An object consists of:
**State** – represented by fields or variables (also known as attributes)
**Behavior** – represented by methods or functions
**Identity** – a unique reference in memory

```java
class Car {
    String color;
    void drive() {
        System.out.println("Driving...");
    }
}

public class Main {
    public static void main(String[] args) {
        Car myCar = new Car(); // 'myCar' is an object
        myCar.color = "Red";
        myCar.drive(); // Output: Driving...
    }
}
```
## Use of Objects in Java:
**Encapsulation** – Objects bundle data and related methods, promoting modularity and hiding implementation details.

**Reusability** – A class can be reused to create multiple objects with different states.

**Real-world modeling** – Objects map real-world entities (e.g., Employee, Student, Account) into software.

**Interaction** – Objects interact with each other to perform tasks (e.g., service objects calling DAO objects).

**Inheritance & Polymorphism** – Through objects, we achieve polymorphic behavior and method overriding.