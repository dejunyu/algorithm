/**
 * @author jun
 * @date 2020-11-01 14:10
 * @description
 *
 * 数组中两个数求和等于指定的一个数
 */
public class GetSum {

    public static void main(String[] args) {
        int[] array = {1,2,4,4};
        int number = 8;
        getTwoSum(array,number);
    }

    public  static void getTwoSum(int[] array,int number){
        if(array ==null){
            return;
        }
        int front = 0,end = array.length-1,sum = 0;
        while (front<end){
            sum = array[front] + array[end];
            if(sum == number){
                System.out.println(array[front] +","+ array[end]);
                break;
            }else if(sum>number){
                end --;
            }else {
                front++;
            }

        }
    }
}
