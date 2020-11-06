package com.yuyutx.stack;

import java.util.Stack;

/**
 * @author jun
 * @date 2020-11-06 22:25
 * @description
 *
 * 用递归函数和栈操作逆序一个栈
 */
public class MyRecursiveReverseStack<T> {

    //将栈底元素返回并移除
    public T getAndRemoveElement(Stack<T> stack){
        T result = stack.pop();
        if(stack.isEmpty()){
            return result;
        }else {
            T last = getAndRemoveElement(stack);
            stack.push(result);
            return last;
        }

    }

    //逆序一个栈
    public void reverse(Stack<T> stack){
        if(stack.isEmpty()){
            return;
        }
        T result = getAndRemoveElement(stack);
        reverse(stack);
        stack.push(result);
    }

    public static void main(String[] args) {
        MyRecursiveReverseStack<Integer> s =new MyRecursiveReverseStack<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        for (Integer i :stack){
            System.out.println(i);
        }
        System.out.println("-----");
        s.reverse(stack);
        for (Integer i :stack){
            System.out.println(i);
        }
    }

}
