package com.ereson.toca;


public class leetcodeTest {
    public int majorityElement(int[] nums) {
        int length = nums.length;
        int flag = length / 2;
        int max = 0;
        for (int i = 0; i < length - 1; i++) {
            if (nums[i] > max) max = nums[i];
        }
        int[] num = new int[max];

        for (int i = 0; i < length - 1; i++) {
            num[nums[i]]++;
            if (num[nums[i]] > flag) return nums[i];
        }
        return 0;
    }
}
