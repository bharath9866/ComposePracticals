package com.example.adaptivestreamingplayer.dsa.blind75.arraysAndHashing.containsDuplicate

import com.example.adaptivestreamingplayer.dsa.benchmark

fun main() {
    val nums = intArrayOf(1, 2, 3, 1, 2, 3)
    val k = 2
    benchmark("containsNearbyDuplicate", nums) {
        ContainsDuplicateTwo().containsNearbyDuplicate(nums, k)
    }
}
class ContainsDuplicateTwo {

    /**
     * https://leetcode.com/problems/contains-duplicate-ii/description/
     * 2. Contains Duplicate II
     * Given an integer array nums and an integer k,
     * return true if there are two distinct indices i and j in the array
     * such that nums[i] == nums[j] and abs(i - j) <= k.
     */
    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        val hashSet = mutableSetOf<Int>()
        var L = 0
        for(i in nums.indices) {
            if(hashSet.size > k) {
                hashSet.remove(nums[L])
                L++
            }
            if(nums[i] in hashSet)
                return true
            hashSet.add(nums[i])
        }
        return false
    }
}