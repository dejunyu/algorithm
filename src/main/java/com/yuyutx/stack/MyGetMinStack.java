package com.yuyutx.stack;

import java.util.Stack;

/**
 * @Author yudejun
 * @Date 2020/11/4 16:20
 * @Version 1.0
 * <p>
 * 设计一个有getMin功能的栈
 * <p>
 * * 1.使用stack来保存栈中数据，minStack保存栈中最小值
 * * 2.push时，判断minStack是否为空，空的话放入值，不为空的话，和minStack栈顶元素比较，如果大于栈顶元素，不存入，否则存入。把元素放入stack
 * * 3.pop时，如果stack为空，抛出异常，不为空时，需要和最小值比较，因为入栈时，栈顶元素只可能小于或者等于弹出元素，所以只有等于栈顶元素的时候才会出栈
 * * 4.getMin时，minStack为空时返回异常，不为空时 直接获取minStack的栈顶元素
 */
public class MyGetMinStack {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public void push(int item) {
        if (minStack.isEmpty()) {
            minStack.push(item);
        } else if (item <= getMin()) {
            minStack.push(item);
        }
        stack.push(item);
    }

    public int pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        int value = stack.pop();
        if (value == getMin()) {
            minStack.pop();
        }
        return value;
    }

    public int getMin() {
        if (minStack.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        return minStack.peek();
    }

    public static void main(String[] a) {
        MyGetMinStack stack = new MyGetMinStack();
        stack.push(3);
        stack.push(5);
        stack.push(2);
        stack.push(4);
        stack.push(1);
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
    }
}
