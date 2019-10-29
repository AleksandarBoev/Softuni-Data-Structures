package t04_linked_stack;

import stack_interface.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> linkedStack = new LinkedStack<>();
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);

        Object[] testing = linkedStack.toArray();
    }
}
