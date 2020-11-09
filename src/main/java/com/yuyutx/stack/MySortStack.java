package com.yuyutx.stack;

import java.util.Stack;

/**
 * @author jun
 * @date 2020-11-10 06:21
 * @description
 *
 * 使用一个栈实现另一个栈的排序,从顶到底从大到小
 *
 * 思路
 * 1.只要新建的栈里的元素是从从大到小的入栈，然后再弹出到原来的空栈，那原来的栈从顶到底就是从大到小排列
 * 2.当原来的待排序的栈不为空时，弹出栈顶元素，当新建的辅助栈不为空，而且栈顶元素小于弹出值时，将辅助栈元素弹出压入待排序战。
 * 3.待排序栈不为空时，弹出的元素一直压入辅助栈
 */
public class MySortStack {

    public void sortStackByStack(Stack<Integer> stack){
        Stack<Integer> popStack = new Stack<>();
        while(!stack.isEmpty()){
            int cur = stack.pop();
            while(!popStack.isEmpty()&&popStack.peek()<cur){
                stack.push(popStack.pop());
            }
            popStack.push(cur);
        }
        while (!popStack.isEmpty()){
            stack.push(popStack.pop());
        }
    }

    public static void main(String[] args) {
        MySortStack mySortStack = new MySortStack();
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(1);
        stack.push(2);
        stack.push(4);
        System.out.println(stack);
        mySortStack.sortStackByStack(stack);
        System.out.println(stack);
    }
}
