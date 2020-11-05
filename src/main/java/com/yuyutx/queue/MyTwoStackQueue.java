package com.yuyutx.queue;

import java.util.Stack;

/**
 * @Author yudejun
 * @Date 2020/11/5 9:32
 * @Version 1.0
 * <p>
 * 使用两个栈来实现队列的add poll peek功能。其实就是入队 出对操作以及查看队首元素
 * <p>
 * 栈的特性是先进后出，而队列是先进先出。两个栈的话，可以一个栈pushStack用来存压入的数据，另一个栈popStack可以把刚才那个栈pushStack全部弹出并压入。
 * 这样，popStack实际上就具备了队列先进先出的特点
 * 这里有两个值得注意的问题：
 * 1. popStack必须为空栈才能压入数据
 * 2. pushStack向popStack压入数据必须全部压入，不能只压部分
 */
public class MyTwoStackQueue<T> {

    private Stack<T> pushStack = new Stack<>();
    private Stack<T> popStack = new Stack<>();

    /**
     * 这个方法其实就是用来满足两个注意事项的。
     * 从实现来看，当队列入队一个元素的时候，主要不出队，就不会执行这个方法。
     * 所以当队列数据较多，再出队的时候，复杂度会很高
     */
    private void pushToPop() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }

    public void add(T data) {
        pushStack.push(data);
        pushToPop();
    }

    public T poll() {
        if (pushStack.isEmpty() && popStack.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        pushToPop();
        return popStack.pop();
    }

    public T peek() {
        if (pushStack.isEmpty() && popStack.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        pushToPop();
        return popStack.peek();
    }

    public static void main(String[] args) {
        MyTwoStackQueue<Integer> queue = new MyTwoStackQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        queue.add(6);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
    }

}
