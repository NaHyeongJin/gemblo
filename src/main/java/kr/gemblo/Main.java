package kr.gemblo;

public class Main {
    static public void main(String[] args) {
        int[] nums = {1, 2, 3, 2, 3};
        int[] result = solution(nums);
        System.out.println(result);
    }
        static int[] solution(int[] prices) {
            int[] answer = new int[prices.length];

            for(int i = 0; i < prices.length; i++) {
                int number = prices[i];
                for(int j = i+1; j < prices.length; j++) {
                    if(number > prices[j]) {
                        answer[i] = j-i;
                        break;
                    }
                    if(j == prices.length-1) answer[i] = j-i;
                }
            }

            return answer;
        }
}
